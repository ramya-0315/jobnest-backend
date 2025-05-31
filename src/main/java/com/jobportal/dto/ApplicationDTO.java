package com.jobportal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private Long applicationId;
    private String jobTitle;
    private String jobSeekerName;
    private String role;
    private String skills;
    private ApplicationStatus status;

    // getters and setters
}
