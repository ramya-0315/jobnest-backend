package com.jobportal.service;

import com.jobportal.dto.EmployerApplicationResponseDTO;
import com.jobportal.dto.UpdateApplicationStatusRequestDTO;
import com.jobportal.dto.JobSeekerProfileResponseDTO;

import java.util.List;

public interface EmployerApplicationService {
    List<EmployerApplicationResponseDTO> getApplicationsForEmployerByEmail(String email); // 🆕
    void updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequestDTO requestDTO);
    JobSeekerProfileResponseDTO getJobSeekerProfile(String email); // 🆕
}
