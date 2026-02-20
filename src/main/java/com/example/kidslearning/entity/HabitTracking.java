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
                @UniqueConstraint(
                        name = "uk_habit_kid_date",
                        columnNames = {"habit_id", "kid_id", "tracking_date"})
        })
public class HabitTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Relationship to Habit
    @ManyToOne(fetch = FetchType.LAZY)  // LAZY is recommended
    @JoinColumn(name = "habit_id", nullable = false)
    private Habits habit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id", nullable = false)
    private Kid kid;

    @Column(name = "tracking_date", nullable = false)
    private LocalDate trackingDate;

    private Boolean status = true;

    private LocalDateTime createdAt = LocalDateTime.now();


}
