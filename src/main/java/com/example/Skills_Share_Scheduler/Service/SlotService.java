package com.example.Skills_Share_Scheduler.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Skills_Share_Scheduler.Entity.Slots;
import com.example.Skills_Share_Scheduler.Entity.Users;
import com.example.Skills_Share_Scheduler.Repository.SlotsRepo;
import com.example.Skills_Share_Scheduler.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class SlotService {

    @Autowired UserRepo userRepo;
    @Autowired SlotsRepo slotRepo;

    public Slots createSlot(LocalDateTime startTime, LocalDateTime endTime, String actingUsername) {
        Users mentor = userRepo.findByUsername(actingUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if(!mentor.getRole().equalsIgnoreCase("MENTOR")){
            throw new RuntimeException("Only mentors can Create Slots Sorry.");
        }

        if(startTime.isAfter(endTime)){
            throw new RuntimeException("Invalid timeslots!!  Start time cannot be after End Time");
        }

        if(startTime.isBefore(LocalDateTime.now())){
            throw new RuntimeException("Invalid timeslots!! start time must be in the future");
        }
        List<Slots> mentorSlots = slotRepo.findByMentorUserId(mentor.getUserId());
        for(Slots s : mentorSlots){
            boolean isOverlap = 
                startTime.isBefore(s.getEndTime()) && 
                endTime.isAfter(s.getStartTime());  

            if(isOverlap){
                throw new  RuntimeException("Slot is overlaping with an existing one");
            }
        }
        Slots slot= new Slots();
        slot.setMentor(mentor);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setBooked(false);
        slot.setCreatedAt(LocalDateTime.now());
        slot.setUpdatedAt(LocalDateTime.now());

        return slotRepo.save(slot);

    }

    public List<Slots> getAllSlots(String actingUsername) {
        Users mentor = userRepo.findByUsername(actingUsername)
            .orElseThrow(() -> new RuntimeException("Mentor Not Found"));

        if(!mentor.getRole().equalsIgnoreCase("MENTOR")){
            throw new RuntimeException("Only mentors can view their slots");
        }

        return slotRepo.findByMentorUserId(mentor.getUserId());
    }

    public List<Slots> getSlotsOfMentor(int id) {
        return slotRepo.findByMentorUserIdAndIsBookedFalse(id);
    }

    @Transactional
    public void deleteSlot(int slotId, String actingUsername) {
        Users user = userRepo.findByUsername(actingUsername)
            .orElseThrow(()-> new RuntimeException("User Not Found"));

        Slots slot = slotRepo.findById(slotId)
            .orElseThrow(()->new RuntimeException("Slot Not Found"));

        boolean isOwner = user.getUserId() == slot.getMentor().getUserId(); 
        boolean isAdmin = user.getRole().equalsIgnoreCase("ADMIN");

        if(!isOwner && !isAdmin){
            throw new RuntimeException("Not Allowed to delete the slot");
        }

        if(slot.isBooked()){
            throw new RuntimeException("can't delete a slot which is already booked");
        }

        slotRepo.delete(slot);
    }

    public List<Slots> getAllAvailableSlots() {
    return slotRepo.findAllByIsBookedFalseAndStartTimeAfter(LocalDateTime.now());
}
}
