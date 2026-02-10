package com.example.kidslearning.service;

import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.dto.HabitRequestDto;

import java.util.List;

public interface SubjectService {

    HabitDto createSubject(HabitDto subjectDto);

    List<HabitDto> getAllSubjects();

    HabitDto getSubjectById(Long id);

    void deleteSubject(Long id);

    // ✅ ADD THIS
    void addSubjectToKid(Long kidId, HabitRequestDto dto);

}


