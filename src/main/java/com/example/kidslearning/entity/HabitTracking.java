package com.example.kidslearning.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "habit_tracking",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"habit_id", "kid_id", "tracking_date"})
        })
public class HabitTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "habit_id", nullable = false)
    private Long habitId;

    @Column(name = "kid_id", nullable = false)
    private Long kidId;

    @Column(name = "tracking_date", nullable = false)
    private LocalDate trackingDate;

    private Boolean status = true;

    private LocalDateTime createdAt = LocalDateTime.now();

   
}
