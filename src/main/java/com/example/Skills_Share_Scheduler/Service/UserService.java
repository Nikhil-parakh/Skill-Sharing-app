package com.example.Skills_Share_Scheduler.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Skills_Share_Scheduler.Config.JwtUtil;
import com.example.Skills_Share_Scheduler.DTO.RegisterRequest;
import com.example.Skills_Share_Scheduler.Entity.UserUpdateDto;
import com.example.Skills_Share_Scheduler.Entity.Users;
import com.example.Skills_Share_Scheduler.Exceptions.UserAlreadyExistsException;
import com.example.Skills_Share_Scheduler.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Transactional
    public Users registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already registered, try another username");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));

        String role = request.getRole().toUpperCase();

        if (role.equals("ADMIN")) {
            throw new IllegalArgumentException("Cannot register as ADMIN");
        }

        if (!role.equals("MENTEE") && !role.equals("MENTOR")) {
            throw new IllegalArgumentException("Invalid role! Only MENTEE or MENTOR allowed.");
        }

        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        return userRepo.save(user);
    }

    public Map<String, Object> verify(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid username or password");
        }

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(username);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("user", Map.of(
                "userId", user.getUserId(),
                "username", user.getUsername(),
                "role", user.getRole()));

        return response;
    }

    public List<Users> getUsers() {
        return userRepo.findAll();
    }

    public Optional<Users> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<Users> getUserByUserId(int id) {
        return userRepo.findById(id);
    }

    @Transactional
    public void deleteById(int targetUserId, String actingUsrname) {
        Users actingUser = userRepo.findByUsername(actingUsrname).orElse(null);
        if (actingUser == null) {
            throw new RuntimeException("Acting user not found");
        }

        Users targetUser = userRepo.findById(targetUserId).orElse(null);
        if (targetUser == null) {
            throw new RuntimeException("Target user not found");
        }

        String role = actingUser.getRole().toUpperCase();

        if (!role.equals("ADMIN")) {

            if (actingUser.getUserId() != targetUserId) {
                throw new RuntimeException("you are not allowed to delete another user");
            }
        }
        userRepo.delete(targetUser);
    }

    @Transactional
    public Users updateUserProfile(int targetUserId, UserUpdateDto newUser, String actingUsername) {
        Users actingUser = userRepo.findByUsername(actingUsername).orElse(null);
        if (actingUser == null) {
            throw new RuntimeException("Acting user not found");
        }

        Users targetUsers = userRepo.findById(targetUserId).orElse(null);
        // .orElseThrow(() -> new RuntimeException("user to delete not Found"));
        if (targetUsers == null) {
            throw new RuntimeException("Target user not found");
        }

        String role = actingUser.getRole().toUpperCase();

        if (!role.equals("ADMIN")) {
            if (actingUser.getUserId() != targetUserId) {
                throw new RuntimeException("you are not allowed to modify any others profile.");
            }
        }

        if (newUser.getFullName() != null)
            targetUsers.setFullName(newUser.getFullName());
        if (newUser.getUsername() != null)
            targetUsers.setUsername(newUser.getUsername());
        if (newUser.getEmail() != null)
            targetUsers.setEmail(newUser.getEmail());

        if (newUser.getRole() != null) {
            if (!actingUser.getRole().equals("ADMIN")) {
                throw new RuntimeException("Only Admin can change the role of an User sorry!");
            }
            String newRole = newUser.getRole().toUpperCase();
            if (!newRole.equals("MENTEE") && !newRole.equals("MENTOR") && !newRole.equals("ADMIN")) {
                throw new RuntimeException("Invalid role");
            }
            targetUsers.setRole(newRole);
        }
        return userRepo.save(targetUsers);
    }

}
