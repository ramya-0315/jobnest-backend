package com.jobportal.service;

import com.jobportal.dto.EmployerProfileDTO;
import com.jobportal.model.EmployerProfile;
import com.jobportal.repository.EmployerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployerProfileService {

    @Autowired
    private EmployerProfileRepository repository;

    public EmployerProfileDTO updateProfile(EmployerProfileDTO dto) {
        EmployerProfile profile = repository.findAll()
                .stream()
                .findFirst()
                .orElse(new EmployerProfile());

        profile.setName(dto.getName());
        profile.setIndustry(dto.getIndustry());
        profile.setLocation(dto.getLocation());
        profile.setAbout(dto.getAbout());

        repository.save(profile);
        return dto;
    }

    public EmployerProfileDTO getProfile() {
        Optional<EmployerProfile> optionalProfile = repository.findAll().stream().findFirst();

        if (optionalProfile.isPresent()) {
            EmployerProfile p = optionalProfile.get();
            EmployerProfileDTO dto = new EmployerProfileDTO();
            dto.setName(p.getName());
            dto.setIndustry(p.getIndustry());
            dto.setLocation(p.getLocation());
            dto.setAbout(p.getAbout());
            return dto;
        }

        return new EmployerProfileDTO(); // empty for new users
    }


    public List<EmployerProfileDTO> getAllProfiles() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public EmployerProfileDTO convertToDTO(EmployerProfile entity) {
        EmployerProfileDTO dto = new EmployerProfileDTO();
        dto.setId(entity.getId()); // ✅ Ensure this is included
        dto.setName(entity.getName());
        dto.setIndustry(entity.getIndustry());
        dto.setLocation(entity.getLocation());
        dto.setAbout(entity.getAbout());
        return dto;
    }

    @Autowired
    private EmployerProfileRepository employerProfileRepository;
    public void deleteProfileById(Long id) {
        if (employerProfileRepository.existsById(id)) {
            employerProfileRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employer profile with ID " + id + " not found.");
        }
    }
}

