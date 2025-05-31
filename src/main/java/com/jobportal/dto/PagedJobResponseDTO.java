package com.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder // ✅ Add this line
@NoArgsConstructor
@AllArgsConstructor
public class PagedJobResponseDTO {
    private List<JobResponseDTO> jobs;
    private int totalPages;
    private long totalElements;
    private int currentPage;
}
