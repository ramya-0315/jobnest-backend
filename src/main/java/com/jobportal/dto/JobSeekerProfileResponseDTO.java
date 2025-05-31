package com.jobportal.dto;


import lombok.Data;

@Data
public class JobSeekerProfileResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String resumeUrl;
    private String skills;
    private String summary;
}
