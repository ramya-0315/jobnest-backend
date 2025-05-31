package com.jobportal.controller;

import com.jobportal.dto.EmployerApplicationResponseDTO;
import com.jobportal.dto.UpdateApplicationStatusRequestDTO;
import com.jobportal.dto.JobSeekerProfileResponseDTO;
import com.jobportal.service.EmployerApplicationService;
import com.jobportal.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer/applications")
@RequiredArgsConstructor
public class EmployerApplicationController {

    private final EmployerApplicationService applicationService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<EmployerApplicationResponseDTO> getApplicationsForEmployer(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        return applicationService.getApplicationsForEmployerByEmail(email);
    }

    @GetMapping("/profile/{email}")
    public JobSeekerProfileResponseDTO getJobSeekerProfile(@PathVariable String email) {
        return applicationService.getJobSeekerProfile(email);
    }

    @PutMapping("/{applicationId}/status")
    public void updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody UpdateApplicationStatusRequestDTO requestDTO) {
        applicationService.updateApplicationStatus(applicationId, requestDTO);
    }
}
