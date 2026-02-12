package com.example.kidslearning.repository;

import com.example.kidslearning.entity.Chapter;
import com.example.kidslearning.entity.Habits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByHabits(Habits habits);
}


