package com.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDTO {
    private Long id;
    private String applicantName;
    private String email;
    private String jobTitle;
    private String message;
    private String resumePath;
    private String status;
}
