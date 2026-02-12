package com.example.kidslearning.service.impl;

import com.example.kidslearning.dto.ChapterDto;
import com.example.kidslearning.entity.Chapter;
import com.example.kidslearning.entity.Habits;
import com.example.kidslearning.exception.ResourceNotFoundException;
import com.example.kidslearning.repository.ChapterRepository;
import com.example.kidslearning.repository.HabitsRepository;
import com.example.kidslearning.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final HabitsRepository habitRepository;

    @Override
    public ChapterDto createChapter(ChapterDto chapterDto) {
        Habits subject = habitRepository.findById(chapterDto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + chapterDto.getSubjectId()));

        Chapter chapter = new Chapter();
        chapter.setTitle(chapterDto.getTitle());
        chapter.setHabits(subject);

        Chapter saved = chapterRepository.save(chapter);
        return mapToDto(saved);
    }

    @Override
    public List<ChapterDto> getChaptersBySubject(Long habitId) {
        Habits habits = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id " + habitId));

        return chapterRepository.findByHabits(habits).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChapterDto getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id " + id));
        return mapToDto(chapter);
    }

    @Override
    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chapter not found with id " + id);
        }
        chapterRepository.deleteById(id);
    }

    private ChapterDto mapToDto(Chapter chapter) {
        ChapterDto dto = new ChapterDto();
        dto.setId(chapter.getId());
        dto.setTitle(chapter.getTitle());
        if (chapter.getHabits() != null) {
            dto.setSubjectId(chapter.getHabits().getId());
        }
        return dto;
    }
}


