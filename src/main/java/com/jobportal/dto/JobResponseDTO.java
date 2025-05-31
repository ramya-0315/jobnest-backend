package com.jobportal.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JobResponseDTO {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String salaryRange;
    private String description;
    private String type;
    private String experience;
    private String skillsRequired; // ✅ Make sure this exists
    private LocalDateTime postedDate;
    private String employerName;
}
