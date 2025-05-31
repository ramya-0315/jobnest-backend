package com.jobportal.service;

import com.jobportal.dto.JobRecommendationRequest;
import com.jobportal.model.Job;
import com.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIJobRecomendationService {

    private final JobRepository jobRepository;

    public AIJobRecomendationService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> recommendJobs(JobRecommendationRequest request) {
        List<String> userSkills = (request.getSkills() != null) ? request.getSkills() : List.of(); // ✅ Fix: Use List<String>

        return jobRepository.findAll().stream()
                .filter(job -> matchesSkills(job.getSkillsRequired(), userSkills))
                .collect(Collectors.toList());
    }

    private boolean matchesSkills(String jobSkills, List<String> userSkills) {
        if (jobSkills == null) return false; // ✅ Fix: Prevent NullPointerException

        for (String skill : userSkills) {
            if (jobSkills.toLowerCase().contains(skill.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

