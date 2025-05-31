package com.jobportal.service;

import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAnalyticsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public com.jobportal.dto.AnalyticsResponseDTO getPlatformStats() {
        long seekers = userRepository.countJobSeekers();
        long employers = userRepository.countEmployers();
        long jobs = jobRepository.countAllJobs();
        long applications = applicationRepository.countAllApplications();

        return new com.jobportal.dto.AnalyticsResponseDTO(seekers, employers, jobs, applications);
    }
}
