package com.example.kidslearning.controller;


import com.example.kidslearning.entity.HabitTracking;
import com.example.kidslearning.service.HabitTrackingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit-tracking")
public class HabitTrackingController {

    private final HabitTrackingService service;

    public HabitTrackingController(HabitTrackingService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public HabitTracking saveHabit(
            @RequestParam Long habitId,
            @RequestParam Long kidId) {

        return service.saveHabit(habitId, kidId);
    }

    @GetMapping("/today/{kidId}")
    public List<HabitTracking> getTodayHabits(
            @PathVariable Long kidId) {

        return service.getTodayHabits(kidId);
    }
}

