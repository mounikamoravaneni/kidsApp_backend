package com.example.kidslearning.service.impl;

import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.dto.HabitRequestDto;
import com.example.kidslearning.entity.Kid;
import com.example.kidslearning.entity.Habits;
import com.example.kidslearning.entity.User;
import com.example.kidslearning.exception.ResourceNotFoundException;
import com.example.kidslearning.repository.KidRepository;
import com.example.kidslearning.repository.HabitsRepository;
import com.example.kidslearning.repository.UserRepository;
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
    private final UserRepository userRepository;



    @Override
    public HabitDto createSubject(HabitDto subjectDto) {
        //fetch parent user from db
        // 🔥 Fetch parent user from DB
        User parent = userRepository.findById(subjectDto.getParentId())
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Habits subject = new Habits();
        subject.setName(subjectDto.getName());
        subject.setParent(parent);
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
    public  List<HabitDto> getHabitsByParentId(Long parentId) {
        User user = userRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + parentId));


        List<Habits> habits = habitsRepository.findByParentId(parentId);

        return habits.stream()
                .map(this::mapToDto)
                .toList();
    }


    @Transactional
    @Override
    public void deleteHabit(List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("IDs cannot be empty");
        }

        List<Habits> habits = habitsRepository.findAllById(ids);

        if (habits.size() != ids.size()) {
            throw new EmptyResultDataAccessException("Some habits not found", ids.size());
        }

        habitsRepository.deleteAll(habits);

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


