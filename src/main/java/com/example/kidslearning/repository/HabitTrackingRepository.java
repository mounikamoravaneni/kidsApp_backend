package com.example.kidslearning.repository;




import com.example.kidslearning.entity.HabitTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitTrackingRepository
        extends JpaRepository<HabitTracking, Long> {

    List<HabitTracking> findByKidIdAndTrackingDate(Long kidId, LocalDate date);

    boolean existsByHabitIdAndKidIdAndTrackingDate(
            Long habitId,
            Long kidId,
            LocalDate trackingDate
    );
}
