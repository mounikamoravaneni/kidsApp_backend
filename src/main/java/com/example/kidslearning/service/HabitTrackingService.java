package com.example.kidslearning.service;


import com.example.kidslearning.dto.MonthlyHabitResponse;
import com.example.kidslearning.entity.HabitTracking;
import com.example.kidslearning.entity.Habits;
import com.example.kidslearning.entity.Kid;
import com.example.kidslearning.repository.HabitTrackingRepository;
import com.example.kidslearning.repository.HabitsRepository;
import com.example.kidslearning.repository.KidRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitTrackingService {

    private final HabitTrackingRepository repository;
    private final HabitsRepository habitsRepository;
    private final KidRepository kidRepository;
    public HabitTrackingService(HabitTrackingRepository repository, HabitsRepository habitsRepository, KidRepository kidRepository) {
        this.repository = repository;
        this.habitsRepository = habitsRepository;

        this.kidRepository = kidRepository;
    }
    public String saveHabits(Long kidId, List<Long> habitIds, LocalDate trackingDate) {

        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("Kid not found"));

        int duplicateCount = 0;
        int savedCount = 0;

        for (Long habitId : habitIds) {

            Habits habit = habitsRepository.findById(habitId)
                    .orElseThrow(() -> new RuntimeException("Habit not found"));

            boolean exists = repository
                    .existsByHabitIdAndKidIdAndTrackingDate(
                            habitId, kidId, trackingDate
                    );

            if (exists) {
                duplicateCount++;
            } else {
                HabitTracking tracking = HabitTracking.builder()
                        .habit(habit)
                        .kid(kid)
                        .trackingDate(trackingDate)
                        .status(true)
                        .build();

                repository.save(tracking);
                savedCount++;
            }
        }

        if (savedCount == 0) {
            return "Habits already exist for date: " + trackingDate;
        }

        return "Habits saved successfully";
    }



    public List<MonthlyHabitResponse> getMonthlyHabits(Long kidId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return repository.getMonthlyHabitDetails(kidId, startDate, endDate);
    }

}
