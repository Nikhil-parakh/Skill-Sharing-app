package com.example.Skills_Share_Scheduler.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserUpdateDto {
    private String fullName;
    private String email;
    private String username;
    private String role;  // only admin can update
}
