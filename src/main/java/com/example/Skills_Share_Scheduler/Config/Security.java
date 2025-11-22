package com.example.Skills_Share_Scheduler.Config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Security {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Endpoints which are open for everyone
                        .requestMatchers("/User/Register", "/User/Login").permitAll()

                        // Only admin can access this 
                        .requestMatchers("/User/All_Users/**").hasRole("ADMIN")
                        .requestMatchers("/User/Delete/**").hasRole("ADMIN")

                        // SELF or ADMIN (handled in service layer)
                        .requestMatchers("/User/Update/**").authenticated()

                        // ANY LOGGED IN USER (MENTOR / MENTEE / ADMIN)
                        .requestMatchers("/User/User_Id/**", "/User/Username/**").authenticated()

                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

// package com.example.Skills_Share_Scheduler.Config;

// import static org.springframework.security.config.Customizer.withDefaults;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import
// org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import
// org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

// @Autowired
// private UserDetailsService userDetailsService;

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {

// http
// .csrf(csrf -> csrf.disable())
// .authorizeHttpRequests(auth -> auth

// // PUBLIC ENDPOINTS
// .requestMatchers("/User/Register", "/User/Login").permitAll()

// // ADMIN ONLY
// .requestMatchers("/User/All_Users").hasRole("ADMIN")

// // SELF or ADMIN (handled in service layer)
// .requestMatchers("/User/Update/**").authenticated()
// .requestMatchers("/User/Delete/**").authenticated()

// // ANY LOGGED IN USER (MENTOR / MENTEE / ADMIN)
// .requestMatchers("/User/User_Id/**", "/User/Username/**").authenticated()

// .anyRequest().authenticated()
// )
// .httpBasic(withDefaults());

// return http.build();
// }

// @Bean
// public AuthenticationProvider authenticationProvider() {
// DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
// provider.setPasswordEncoder(passwordEncoder());
// provider.setUserDetailsService(userDetailsService);
// return provider;
// }

// @Bean
// public BCryptPasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder(10);
// }

// @Bean
// public AuthenticationManager
// authenticationManager(AuthenticationConfiguration config) throws Exception {
// return config.getAuthenticationManager();
// }
// }
