package com.example.Skills_Share_Scheduler.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Skills_Share_Scheduler.Entity.Slots;

public interface SlotsRepo extends JpaRepository<Slots, Integer>{
    List<Slots> findAllByIsBookedFalseAndStartTimeAfter(LocalDateTime now);

    List<Slots> findByMentorUserId(int userId);

    List<Slots> findByMentorUserIdAndIsBookedFalse(int id);

}
