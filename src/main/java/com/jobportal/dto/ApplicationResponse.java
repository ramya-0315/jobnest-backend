package com.jobportal.dto;


import lombok.Data;

@Data
public class ApplicationResponse {
    private Long applicationId;
    private String applicantEmail;
    private String jobTitle;
    private String coverLetter;
    private String appliedDate;
}