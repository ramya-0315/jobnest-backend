package com.jobportal.controller;

import com.jobportal.dto.SettingsDTO;
import com.jobportal.service.EmployerSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer/settings")
@CrossOrigin(origins = "*") // Adjust for frontend
public class EmployerSettingsController {

    @Autowired
    private EmployerSettingsService service;

    // Fetch settings for a specific employer by ID
    @GetMapping("/{employerId}")
    public SettingsDTO getSettings(@PathVariable Long employerId) {
        return service.getSettings(employerId);
    }

    // Update settings for a specific employer by ID
    @PutMapping("/{employerId}")
    public SettingsDTO updateSettings(@PathVariable Long employerId, @RequestBody SettingsDTO dto) {
        return service.updateSettings(employerId, dto);
    }
}
