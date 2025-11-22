package com.example.Skills_Share_Scheduler.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Skills_Share_Scheduler.Entity.Booking;
import com.example.Skills_Share_Scheduler.Entity.Slots;
import com.example.Skills_Share_Scheduler.Entity.Users;
import com.example.Skills_Share_Scheduler.Repository.BookingRepo;
import com.example.Skills_Share_Scheduler.Repository.SlotsRepo;
import com.example.Skills_Share_Scheduler.Repository.UserRepo;

import jakarta.transaction.Transactional;


@Service
public class BookingService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private SlotsRepo slotsRepo;

    @Transactional
    public Booking createBooking(int slotId, String actingUsername) {
        Users mentee = userRepo.findByUsername(actingUsername)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if (!mentee.getRole().equalsIgnoreCase("MENTEE")) {
            throw new RuntimeException("Only MENTEES can book slots.");
        }

        Slots slot = slotsRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot does not exist"));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }

        if (slot.getStartTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot book past slots");
        }

        Booking booking = new Booking();
        booking.setSlot(slot);
        booking.setMentee(mentee);
        booking.setStatus("BOOKED");
        booking.setBookedAt(LocalDateTime.now());

        slot.setBooked(true);
        slotsRepo.save(slot);

        return bookingRepo.save(booking);
    }

    @Transactional
    public void cancelBooking(int bookingId, String actingUsername) {
        Users user = userRepo.findByUsername(actingUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isOwner = booking.getMentee().getUserId() == user.getUserId();
        boolean isAdmin = user.getRole().equalsIgnoreCase("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to cancel this booking");
        }

        Slots slot = booking.getSlot();
        slot.setBooked(false);
        slotsRepo.save(slot);

        // Delete booking
        bookingRepo.delete(booking);
    }

    public List<Booking> getMyBookings(String actingUsername) {
        Users mentee = userRepo.findByUsername(actingUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!mentee.getRole().equalsIgnoreCase("MENTEE")) {
            throw new RuntimeException("Only mentees can view their bookings");
        }

        return bookingRepo.findByMenteeUserId(mentee.getUserId());
    }

    public List<Booking> getMentorBookings(String actingUsername) {
            Users mentor = userRepo.findByUsername(actingUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!mentor.getRole().equalsIgnoreCase("MENTOR")) {
            throw new RuntimeException("Only mentors can view their bookings");
        }

        return bookingRepo.findBySlotMentorUserId(mentor.getUserId());
    }

    public List<Booking> getAllBookings() {
        String adminName = SecurityContextHolder.getContext().getAuthentication().getName();

        Users admin = userRepo.findByUsername(adminName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!admin.getRole().equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Only Admin can view All bookings");
        }
        return bookingRepo.findAll();
    }
    
}
