package com.example.kidslearning.repository;

import com.example.kidslearning.entity.Habits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitsRepository extends JpaRepository<Habits, Long> {

    // 🔍 Find subject by name (used while adding subject to kid)
    Optional<Habits> findByName(String name);

    // 🔍 Check if subject exists by name (optional but useful)
    boolean existsByName(String name);
}



