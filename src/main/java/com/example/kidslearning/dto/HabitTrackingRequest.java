package com.example.kidslearning.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HabitTrackingRequest {
    private Long kidId;
    private List<Long> habitIds;
    private LocalDate trackingDate;
}
