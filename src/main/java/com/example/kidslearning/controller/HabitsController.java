package com.example.kidslearning.controller;

import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.service.SubjectService;
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
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitsController {

    private final SubjectService subjectService;



    @PostMapping
    public ResponseEntity<ApiResponse<HabitDto>> createSubject(@RequestBody HabitDto subjectDto) {
        HabitDto created = subjectService.createSubject(subjectDto);
        return ResponseEntity.ok(ApiResponse.ok("Subject created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HabitDto>>> getAllSubjects() {
        List<HabitDto> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(ApiResponse.ok("Subjects fetched successfully", subjects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitDto>> getSubjectById(@PathVariable Long id) {
        HabitDto subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(ApiResponse.ok("Subject fetched successfully", subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(ApiResponse.ok("Subject deleted successfully", null));
    }
}


