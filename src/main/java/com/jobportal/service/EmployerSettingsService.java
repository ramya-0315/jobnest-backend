package com.jobportal.service;

import com.jobportal.dto.SettingsDTO;
import com.jobportal.model.Employer;
import com.jobportal.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerSettingsService {

    @Autowired
    private EmployerRepository employerRepository;

    // Fetch the settings for a specific employer
    public SettingsDTO getSettings(Long employerId) {
        Employer employer = employerRepository.findById(employerId).orElseThrow(() -> new RuntimeException("Employer not found"));
        return new SettingsDTO(employer.getEmail(), employer.getPassword());
    }

    // Update the settings for a specific employer
    public SettingsDTO updateSettings(Long employerId, SettingsDTO dto) {
        Employer employer = employerRepository.findById(employerId).orElseThrow(() -> new RuntimeException("Employer not found"));
        employer.setEmail(dto.getEmail());
        employer.setPassword(dto.getPassword());  // You may want to hash the password before saving

        employerRepository.save(employer);
        return new SettingsDTO(employer.getEmail(), employer.getPassword());
    }
}
