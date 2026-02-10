package com.example.kidslearning.controller;

import com.example.kidslearning.dto.KidDto;
import com.example.kidslearning.dto.KidSubjectResponseDto;
import com.example.kidslearning.dto.HabitDto;
import com.example.kidslearning.dto.HabitRequestDto;
import com.example.kidslearning.service.KidService;
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
@RequestMapping("/api/kids")
@RequiredArgsConstructor
public class KidController {

    private final KidService kidService;
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<KidDto>> createKid(@RequestBody KidDto kidDto) {
        KidDto created = kidService.createKid(kidDto);
        return ResponseEntity.ok(ApiResponse.ok("Kid created successfully", created));
    }

    @PostMapping("/{kidId}/subjects")
    public ResponseEntity<?> addSubjectToKid(
            @PathVariable Long kidId,
            @RequestBody HabitRequestDto dto) {

        // 1️⃣ Add subject to kid using SubjectService
        subjectService.addSubjectToKid(kidId, dto);

        // 2️⃣ Fetch kid with updated subjects via KidService
        KidDto kidDto = kidService.getKidById(kidId);

        // 3️⃣ Map subjects to SubjectDto (if not already done in KidDto)
        List<HabitDto> subjectList = kidDto.getSubjects(); // assuming KidDto has List<SubjectDto>

        // 4️⃣ Create response DTO
        KidSubjectResponseDto data = new KidSubjectResponseDto(
                kidDto.getId(),
                kidDto.getName(),
                subjectList
        );

        return ResponseEntity.ok(ApiResponse.ok("Subject added to kid", data));
    }

    @GetMapping("/{kidId}/subjects")
    public ResponseEntity<?> getKidSubjects( @PathVariable Long kidId){
        // 2️⃣ Fetch kid with updated subjects via KidService
        KidDto kidDto = kidService.getKidById(kidId);

        // 3️⃣ Map subjects to SubjectDto (if not already done in KidDto)
        List<HabitDto> subjectList = kidDto.getSubjects(); // assuming KidDto has List<SubjectDto>

        KidSubjectResponseDto data = new KidSubjectResponseDto(
                kidDto.getId(),
                kidDto.getName(),
                subjectList
        );
        return ResponseEntity.ok(ApiResponse.ok("Subjects fetched successfully", data));
    }
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ApiResponse<List<KidDto>>> getKidsByParent(@PathVariable Long parentId) {
        List<KidDto> kids = kidService.getKidsByParent(parentId);
        return ResponseEntity.ok(ApiResponse.ok("Kids fetched successfully", kids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KidDto>> getKidById(@PathVariable Long id) {
        KidDto kid = kidService.getKidById(id);
        return ResponseEntity.ok(ApiResponse.ok("Kid fetched successfully", kid));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteKid(@PathVariable Long id) {
        kidService.deleteKid(id);
        return ResponseEntity.ok(ApiResponse.ok("Kid deleted successfully", null));
    }


    @DeleteMapping("/{kidId}/subjects/{subjectId}")
    public ResponseEntity<ApiResponse<Void>> removeSubjectFromKid(
            @PathVariable Long kidId,
            @PathVariable Long subjectId
    ) {
        kidService.removeSubjectFromKid(kidId, subjectId);
        return ResponseEntity.ok(ApiResponse.ok("Subject removed from kid", null));
    }

}


