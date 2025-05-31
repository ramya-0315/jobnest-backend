package com.jobportal.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class JobApplicationRequest {

    private Long jobId;
    private String coverLetter;
}


