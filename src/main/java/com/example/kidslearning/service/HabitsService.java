package com.example.kidslearning.service;

import com.example.kidslearning.dto.HabitsDto;
import com.example.kidslearning.dto.HabitRequestDto;

import java.util.List;

public interface HabitsService {

    HabitsDto createSubject(HabitsDto subjectDto);

    List<HabitsDto> getAllSubjects();

    HabitsDto getSubjectById(Long id);

    void deleteSubject(Long id);

    // ✅ ADD THIS
    void addSubjectToKid(Long kidId, HabitRequestDto dto);

}


