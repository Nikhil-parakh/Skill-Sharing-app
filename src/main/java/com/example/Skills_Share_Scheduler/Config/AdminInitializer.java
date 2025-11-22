package com.example.Skills_Share_Scheduler.Config;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Skills_Share_Scheduler.Entity.Users;
import com.example.Skills_Share_Scheduler.Repository.UserRepo;

@Configuration
public class AdminInitializer implements CommandLineRunner {

    @Autowired UserRepo userRepo;
    @Autowired BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (userRepo.findByRole("ADMIN").isEmpty()) {

            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setFullName("System Admin");
            admin.setEmail("admin@system.com");
            admin.setCreatedAt(LocalDateTime.now());

            userRepo.save(admin);
            System.out.println("ADMIN CREATED SUCCESSFULLY!");
        }
    }
}
