package com.example.Skills_Share_Scheduler.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Skills_Share_Scheduler.DTO.RegisterRequest;
import com.example.Skills_Share_Scheduler.DTO.LoginRequest;
import com.example.Skills_Share_Scheduler.Entity.UserUpdateDto;
import com.example.Skills_Share_Scheduler.Entity.Users;
import com.example.Skills_Share_Scheduler.Service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/User")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/All_Users")
    public ResponseEntity<List<Users>> getAll() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/ById/{Id}")
    public ResponseEntity<Users> getUserById(@PathVariable int Id) {
        Users user = userService.getUserByUserId(Id).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ByUsername/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        Users user = userService.getUserByUsername(username).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/Register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        try {
            userService.registerUser(request);
            return ResponseEntity.ok(
                    Map.of("message", "User registered successfully"));

        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        try {
            Map<String, Object> response = userService.verify(
                    credentials.getUsername(),
                    credentials.getPassword());

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/Update/{targetID}")
    public ResponseEntity<?> updateUser(@PathVariable int targetID, @RequestBody UserUpdateDto newUser) {

        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            Users user = userService.updateUserProfile(targetID, newUser, actingUsername);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/Delete/{targetId}")
    public ResponseEntity<?> deleteById(@PathVariable int targetId) {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            userService.deleteById(targetId, actingUsername);
            return ResponseEntity.ok("User Deleted SuccessFuly");
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
