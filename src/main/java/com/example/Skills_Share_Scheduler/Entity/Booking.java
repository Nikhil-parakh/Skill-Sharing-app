package com.example.Skills_Share_Scheduler.Entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "booking",
    uniqueConstraints = @UniqueConstraint(columnNames = "slot_id")   // Prevent double booking
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")   // <-- DB column
    private int bookingId;         // <-- JAVA field (correct camelCase)

    @OneToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slots slot;

    @ManyToOne
    @JoinColumn(name = "mentee_id", nullable = false)
    private Users mentee;

    private String status;

    private LocalDateTime bookedAt;
}

