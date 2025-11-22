package com.example.Skills_Share_Scheduler.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Skills_Share_Scheduler.Entity.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer>{

    List<Booking> findByMenteeUserId(int userId);

    List<Booking> findBySlotMentorUserId(int userId);

}
