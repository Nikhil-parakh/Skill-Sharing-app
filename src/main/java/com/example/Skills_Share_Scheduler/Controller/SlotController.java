package com.example.Skills_Share_Scheduler.Controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Skills_Share_Scheduler.Entity.Slots;
import com.example.Skills_Share_Scheduler.Service.SlotService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping("/Create")
    @Transactional
    public ResponseEntity<?> addSlot(@RequestBody Map<String, String> newslot) {
        Slots slot;
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            LocalDateTime startTime = LocalDateTime.parse(newslot.get("startTime"));
            LocalDateTime endTime = LocalDateTime.parse(newslot.get("endTime"));
            slot = slotService.createSlot(startTime, endTime, actingUsername);
            return new ResponseEntity<>(slot, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/AllAvailable")
    public ResponseEntity<?> getAllAvailableSlots() {
        return ResponseEntity.ok(slotService.getAllAvailableSlots());
    }

    @GetMapping("/MySlots")
    public ResponseEntity<?> getMySlots() {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<Slots> slots = slotService.getAllSlots(actingUsername);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/OfMentor/{id}")
    public ResponseEntity<?> getSlotsOfMentor(@PathVariable int id) {
        return ResponseEntity.ok(slotService.getSlotsOfMentor(id));
    }

    @DeleteMapping("/Delete/{slotId}")
    public ResponseEntity<?> deleteSlot(@PathVariable int slotId) {
        String actingUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            slotService.deleteSlot(slotId, actingUsername);
            return ResponseEntity.ok("Successfully Deleted the slot: " + slotId);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
