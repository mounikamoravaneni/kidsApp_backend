package com.example.kidslearning.service;


import com.example.kidslearning.entity.HabitTracking;
import com.example.kidslearning.repository.HabitTrackingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitTrackingService {

    private final HabitTrackingRepository repository;

    public HabitTrackingService(HabitTrackingRepository repository) {
        this.repository = repository;
    }

    public HabitTracking saveHabit(Long habitId, Long kidId) {

        LocalDate today = LocalDate.now();

        if (repository.existsByHabitIdAndKidIdAndTrackingDate(
                habitId, kidId, today)) {
            throw new RuntimeException("Habit already saved today");
        }

        HabitTracking tracking = new HabitTracking();
        tracking.setHabitId(habitId);
        tracking.setKidId(kidId);
        tracking.setTrackingDate(today);

        return repository.save(tracking);
    }

    public List<HabitTracking> getTodayHabits(Long kidId) {
        return repository.findByKidIdAndTrackingDate(
                kidId,
                LocalDate.now()
        );
    }
}
