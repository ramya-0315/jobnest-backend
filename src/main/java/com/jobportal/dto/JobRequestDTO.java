package com.jobportal.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDTO {
    private String title;
    private String company;
    private String location;
    private String salaryRange;
    private String description;
    private String type;
    private String experience;
    private String SkillsRequired;
}
