package com.example.kidslearning.controller;

import com.example.kidslearning.dto.BulkDeleteRequest;
import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.service.SubjectService;
import com.example.kidslearning.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    private final SubjectService habitService;



    @PostMapping
    public ResponseEntity<ApiResponse<HabitDto>> createSubject(@RequestBody HabitDto subjectDto) {

        System.out.println("=== POST API HIT ===");
        System.out.println("Name received: " + subjectDto.getParentId());
        HabitDto created = habitService.createSubject(subjectDto);
        return ResponseEntity.ok(ApiResponse.ok("Habit created successfully", created));
    }



    @GetMapping("parent/{parentId}")
    public ResponseEntity<ApiResponse<List<HabitDto>>> getSubjectById(@PathVariable Long parentId) {
        List<HabitDto> habits = habitService.getHabitsByParentId(parentId);
        return ResponseEntity.ok(ApiResponse.ok("Habit fetched successfully", habits));
    }



    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> deleteHabits(@RequestBody BulkDeleteRequest request) {
        try {
            if (request == null || request.getIds() == null || request.getIds().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("No habit IDs provided to delete."));
            }app-release.aab

            habitService.deleteHabit(request.getIds());
            return ResponseEntity.ok(ApiResponse.ok("Habits deleted successfully", null));

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Some habits were not found: " + e.getMessage()));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("Cannot delete habits due to existing dependencies."));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete habits: " + e.getMessage()));
        }
    }
}


