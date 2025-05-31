package com.jobportal.controller;

import com.jobportal.dto.EmployerProfileDTO;
import com.jobportal.service.EmployerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer/profile")
@CrossOrigin(origins = "*") // Adjust as needed for CORS
public class EmployerProfileController {

    @Autowired
    private EmployerProfileService profileService;

    @PutMapping
    public EmployerProfileDTO updateProfile(@RequestBody EmployerProfileDTO dto) {
        return profileService.updateProfile(dto);
    }

    @GetMapping
    public EmployerProfileDTO getProfile() {
        return profileService.getProfile();
    }
}
