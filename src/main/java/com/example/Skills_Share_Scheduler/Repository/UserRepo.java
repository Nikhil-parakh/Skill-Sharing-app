package com.example.Skills_Share_Scheduler.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Skills_Share_Scheduler.Entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer>{

    Optional<Users> findByUsername(String username);

    Optional<Users> findByRole(String role);
}
