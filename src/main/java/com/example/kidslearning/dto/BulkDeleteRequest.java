package com.example.kidslearning.dto;

import lombok.Data;

import java.util.List;

@Data
public class BulkDeleteRequest {
    private List<Long> ids;
}
