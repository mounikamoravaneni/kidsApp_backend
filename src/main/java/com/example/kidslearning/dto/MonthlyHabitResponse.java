package com.example.kidslearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MonthlyHabitResponse {

    private Long habitId;
    private String habitName;
    private Long kidId;
    private LocalDate trackingDate;
    private Boolean status;
}
