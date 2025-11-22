package com.example.Skills_Share_Scheduler.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Skills_Share_Scheduler.Entity.Booking;
import com.example.Skills_Share_Scheduler.Service.BookingService;

@RestController
@RequestMapping("/Booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create/{slotId}")
    public ResponseEntity<?> bookSlot(@PathVariable int slotId) {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Booking book = bookingService.createBooking(slotId, actingUsername);
            return ResponseEntity.ok(book);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/Cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId) {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            bookingService.cancelBooking(bookingId, actingUsername);
            return ResponseEntity.ok("Booking Cancel");
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/MenteeBookings")
    public ResponseEntity<?> getMyBookings() {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            List<Booking> bookings = bookingService.getMyBookings(actingUsername);
            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/MentorBookings")
    public ResponseEntity<?> getMentorsBooking() {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            List<Booking> bookings = bookingService.getMentorBookings(actingUsername);
            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/AllBokings")
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
