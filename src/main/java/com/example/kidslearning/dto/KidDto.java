package com.example.kidslearning.dto;

import com.example.kidslearning.entity.Kid;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class KidDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Long parentId;
    private List<HabitDto> subjects; // ← add this

    public static KidDto fromEntity(Kid kid) {
        KidDto dto = new KidDto();
        dto.setId(kid.getId());
        dto.setName(kid.getName());
        dto.setParentId(kid.getParent().getId());
        dto.setDateOfBirth(kid.getDateOfBirth());

        return dto;
    }


}


