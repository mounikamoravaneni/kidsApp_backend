package com.example.kidslearning.service.impl;

import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.dto.HabitRequestDto;
import com.example.kidslearning.entity.Kid;
import com.example.kidslearning.entity.Habits;
import com.example.kidslearning.exception.ResourceNotFoundException;
import com.example.kidslearning.repository.KidRepository;
import com.example.kidslearning.repository.HabitsRepository;
import com.example.kidslearning.service.SubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final HabitsRepository habitsRepository;
    private final KidRepository kidRepository;

    @Override
    public HabitDto createSubject(HabitDto subjectDto) {
        Habits subject = new Habits();
        subject.setName(subjectDto.getName());
        Habits saved = habitsRepository.save(subject);
        return mapToDto(saved);
    }

    @Override
    public List<HabitDto> getAllSubjects() {
        return habitsRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HabitDto getSubjectById(Long id) {
        Habits subject = habitsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
        return mapToDto(subject);
    }


    @Transactional
    @Override
    public void deleteHabit(List<Long> ids) {
        List<Habits> habitsToDelete = habitsRepository.findAllById(ids);

        if (habitsToDelete.isEmpty()) {
            throw new EmptyResultDataAccessException("No habits found for given IDs", ids.size());
        }

        // Force load trackings to trigger cascade delete
        habitsToDelete.forEach(habit -> habit.getTrackings().size());

        habitsRepository.deleteAll(habitsToDelete);
    }


    private HabitDto mapToDto(Habits subject) {
        HabitDto dto = new HabitDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        return dto;
    }
    @Override
    @Transactional
    public void addSubjectToKid(Long kidId, HabitRequestDto dto) {

        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("Kid not found"));

        Habits subject = habitsRepository.findByName(dto.getName())
                .orElseGet(() -> {
                    Habits newSubject = new Habits();
                    newSubject.setName(dto.getName());
                    return habitsRepository.save(newSubject);
                });

        kidRepository.save(kid);
    }

}


