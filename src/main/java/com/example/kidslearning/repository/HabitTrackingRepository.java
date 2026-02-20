package com.example.kidslearning.repository;




import com.example.kidslearning.dto.MonthlyHabitResponse;
import com.example.kidslearning.entity.HabitTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitTrackingRepository
        extends JpaRepository<HabitTracking, Long> {

    List<HabitTracking> findByKidIdAndTrackingDate(Long kidId, LocalDate date);

    boolean existsByHabitIdAndKidIdAndTrackingDate(
            Long habitId,
            Long kidId,
            LocalDate trackingDate
    );

    @Query("""
    SELECT new com.example.kidslearning.dto.MonthlyHabitResponse(
        ht.habit.id,
        ht.habit.name,
          ht.kid.id,
        ht.trackingDate,
        ht.status
    )
    FROM HabitTracking ht
    WHERE ht.kid.id = :kidId
    AND ht.trackingDate BETWEEN :startDate AND :endDate
""")
    List<MonthlyHabitResponse> getMonthlyHabitDetails(
            Long kidId,
            LocalDate startDate,
            LocalDate endDate
    );
}

