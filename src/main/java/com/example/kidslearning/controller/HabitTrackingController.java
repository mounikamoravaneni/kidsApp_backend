package com.example.kidslearning.controller;


import com.example.kidslearning.dto.HabitTrackingRequest;
import com.example.kidslearning.dto.MonthlyHabitResponse;
import com.example.kidslearning.entity.HabitTracking;
import com.example.kidslearning.service.HabitTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequiredArgsConstructor
@RequestMapping("/habit-tracking")
public class HabitTrackingController {

    private final HabitTrackingService service;

    @PostMapping("/save-multiple")
    public ResponseEntity<?> saveHabits(@RequestBody HabitTrackingRequest request) {

        String message = service.saveHabits(
                request.getKidId(),
                request.getHabitIds(),
                request.getTrackingDate()
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", message
        ));
    }



    @GetMapping("/monthly")
    public List<MonthlyHabitResponse> getMonthlyHabits(
            @RequestParam Long kidId,
            @RequestParam int year,
            @RequestParam int month) {
        return service.getMonthlyHabits(kidId, year, month);
    }
}

