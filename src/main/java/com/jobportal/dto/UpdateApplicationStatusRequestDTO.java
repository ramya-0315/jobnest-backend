package com.jobportal.dto;

import com.jobportal.dto.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequestDTO {
    private ApplicationStatus status;
}