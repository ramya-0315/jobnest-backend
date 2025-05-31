package com.jobportal.service;

import com.jobportal.dto.JobDTO;
import com.jobportal.model.AccountSettings;
import com.jobportal.model.Job;
import com.jobportal.repository.AccountSettingsRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.RecommendedJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RecommendedJobsServiceImpl implements RecommendedJobsService {

    private final JobRepository jobRepository;
    private final AccountSettingsRepository accountSettingsRepository;

    @Override
    public Page<JobDTO> getRecommendedJobs(String email, int page, int size) {
        AccountSettings settings = accountSettingsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account settings not found for email: " + email));

        List<JobDTO> recommendedJobs = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by("postedDate").descending());

        String[] preferredRoles = settings.getPreferredRoles().split(",");
        String[] preferredLocations = settings.getPreferredLocations().split(",");

        for (String role : preferredRoles) {
            for (String location : preferredLocations) {
                // Trim and convert to lowercase for case-insensitive matching
                String trimmedRole = role.trim().toLowerCase();
                String trimmedLocation = location.trim().toLowerCase();

                // Search jobs using trimmed and lowercase roles/locations
                Page<Job> jobsPage = jobRepository.findRecommendedJobs(trimmedRole, trimmedLocation, pageable);

                jobsPage.forEach(job -> recommendedJobs.add(mapToDTO(job)));

                // If enough jobs are found, break the loop
                if (recommendedJobs.size() >= size) {
                    break;
                }
            }
            if (recommendedJobs.size() >= size) {
                break;
            }
        }

        return new PageImpl<>(recommendedJobs, pageable, recommendedJobs.size());
    }

    private JobDTO mapToDTO(Job job) {
        return JobDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .description(job.getDescription())
                .type(job.getType())
                .experience(job.getExperience())
                .salaryRange(job.getSalaryRange())
                .skillsRequired(job.getSkillsRequired())
                .postedDate(job.getPostedDate())
                .build();
    }
}
