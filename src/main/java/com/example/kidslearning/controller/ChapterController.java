package com.example.kidslearning.controller;

import com.example.kidslearning.dto.ChapterDto;
import com.example.kidslearning.service.ChapterService;
import com.example.kidslearning.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChapterDto>> createChapter(@RequestBody ChapterDto chapterDto) {
        ChapterDto created = chapterService.createChapter(chapterDto);
        return ResponseEntity.ok(ApiResponse.ok("Chapter created successfully", created));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<ChapterDto>>> getChaptersBySubject(@PathVariable Long habitId) {
        List<ChapterDto> chapters = chapterService.getChaptersBySubject(habitId);
        return ResponseEntity.ok(ApiResponse.ok("Chapters fetched successfully", chapters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChapterDto>> getChapterById(@PathVariable Long id) {
        ChapterDto chapter = chapterService.getChapterById(id);
        return ResponseEntity.ok(ApiResponse.ok("Chapter fetched successfully", chapter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.ok(ApiResponse.ok("Chapter deleted successfully", null));
    }
}


