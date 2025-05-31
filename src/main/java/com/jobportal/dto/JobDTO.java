package com.jobportal.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String type;
    private String experience;
    private String salaryRange;
    private String description;
    private String skillsRequired;
    private LocalDateTime postedDate;
}
