package com.example.kidslearning.dto;

import com.example.kidslearning.entity.HabitTracking;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HabitTrackingResponse {

    private Long id;
    private Long habitId;
    private Long kidId;
    private LocalDate trackingDate;
    private Boolean status;
    private LocalDateTime createdAt;

    public HabitTrackingResponse(HabitTracking tracking) {
        this.id = tracking.getId();
        this.habitId = tracking.getHabit().getId();

        this.trackingDate = tracking.getTrackingDate();
        this.status = tracking.getStatus();
        this.createdAt = tracking.getCreatedAt();
    }
}
