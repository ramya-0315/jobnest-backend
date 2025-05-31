package com.jobportal.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminApplicationReviewDTO {
    private String jobSeekerName;
    private String jobTitle;
    private String companyName;
    private LocalDate dateApplied;
    private String status;
}
