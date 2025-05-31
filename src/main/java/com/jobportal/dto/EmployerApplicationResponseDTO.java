package com.jobportal.dto;


import lombok.Data;

import java.util.List;

@Data
public class EmployerApplicationResponseDTO {
    private Long applicationId;
    private Long jobId;
    private String jobTitle;
    private ApplicationStatus status;
    private ApplicantInfo applicant;

    @Data
    public static class ApplicantInfo {
        private Long jobSeekerId;
        private String name;
        private String email;
        private String phoneNumber;
        private String resumeUrl;
        private List<String> skills;

        private String summary;
    }
}
