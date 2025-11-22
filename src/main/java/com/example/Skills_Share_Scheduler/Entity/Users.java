package com.example.Skills_Share_Scheduler.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")     // DB - name
    private int userId;           

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;
    
    @NotNull
    private String role;

    private String fullName;

    @Email
    private String email;

    private LocalDateTime createdAt;
}
