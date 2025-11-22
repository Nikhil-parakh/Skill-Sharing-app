package com.example.Skills_Share_Scheduler.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "slots")
public class Slots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private int slotId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private Users mentor;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(name = "is_booked")
    private boolean isBooked;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
