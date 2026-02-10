package com.example.kidslearning.service.impl;

import com.example.kidslearning.dto.KidDto;
import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.entity.Kid;
import com.example.kidslearning.entity.Habits;
import com.example.kidslearning.entity.User;
import com.example.kidslearning.exception.ResourceNotFoundException;
import com.example.kidslearning.repository.KidRepository;
import com.example.kidslearning.repository.HabitsRepository;
import com.example.kidslearning.repository.UserRepository;
import com.example.kidslearning.service.KidService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KidServiceImpl implements KidService {

    private final KidRepository kidRepository;
    private final UserRepository userRepository;
    private final HabitsRepository subjectRepository;

    @Override
    public KidDto createKid(KidDto kidDto) {
        User parent = userRepository.findById(kidDto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + kidDto.getParentId()));

        Kid kid = new Kid();
        kid.setName(kidDto.getName());
        kid.setDateOfBirth(kidDto.getDateOfBirth());
        kid.setParent(parent);

        Kid saved = kidRepository.save(kid);
        return mapToDto(saved);
    }

    @Override
    public List<KidDto> getKidsByParent(Long parentId) {
        User parent = userRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id " + parentId));

        return kidRepository.findByParent(parent).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public KidDto getKidById(Long id) {
        Kid kid = kidRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kid not found with id " + id));
        return mapToDto(kid);
    }

    @Override
    public void deleteKid(Long id) {
        if (!kidRepository.existsById(id)) {
            throw new ResourceNotFoundException("Kid not found with id " + id);
        }
        kidRepository.deleteById(id);
    }

    @Transactional
    public void removeSubjectFromKid(Long kidId, Long subjectId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new ResourceNotFoundException("Kid not found"));

        Habits subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        // Remove relationship
        kid.getHabits().remove(subject);
        subject.getKids().remove(kid);

        // Save the kid (subject side will be updated automatically due to mappedBy)
        kidRepository.save(kid);
    }

    private KidDto mapToDto(Kid kid) {
        KidDto dto = new KidDto();
        dto.setId(kid.getId());
        dto.setName(kid.getName());
        dto.setDateOfBirth(kid.getDateOfBirth());
        if (kid.getParent() != null) {
            dto.setParentId(kid.getParent().getId());
        }
        // ✅ Map subjects
        if (kid.getHabits() != null && !kid.getHabits().isEmpty()) {
            List<HabitDto> subjectList = kid.getHabits().stream()
                    .map(s -> new HabitDto(s.getId(), s.getName()))
                    .collect(Collectors.toList());
            dto.setSubjects(subjectList);
        } else {
            dto.setSubjects(new ArrayList<>()); // prevent null
        }

        return dto;
    }
}


