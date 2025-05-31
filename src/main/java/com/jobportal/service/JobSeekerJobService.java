package com.jobportal.service;

import com.jobportal.dto.ApplicationStatusDTO;
import com.jobportal.dto.JobAppResponseByEmployer;
import com.jobportal.dto.JobResponseDTO;
import com.jobportal.model.Job;
import com.jobportal.model.JobApplication;
import com.jobportal.model.User;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSeekerJobService {

    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository; // ✅ Declare it

    @Autowired
    public JobSeekerJobService(JobRepository jobRepository,
                               JobApplicationRepository jobApplicationRepository,
                               UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository; // ✅ Assign it
    }

    public List<JobResponseDTO> getAllAvailableJobs() {
        return jobRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public JobResponseDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return job != null ? convertToDTO(job) : null;
    }

    public boolean applyForJob(Long jobId, JobApplication jobApplication, Principal principal) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) return false;

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        jobApplication.setJob(job);
        jobApplication.setUser(user); // 👈 VERY IMPORTANT
        jobApplicationRepository.save(jobApplication);
        return true;
    }

    private JobResponseDTO convertToDTO(Job job) {
        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .salaryRange(job.getSalaryRange())
                .description(job.getDescription())
                .type(job.getType())
                .experience(job.getExperience())
                .postedDate(job.getPostedDate())
                .employerName(job.getEmployer() != null ? job.getEmployer().getCompanyName() : null)
                .build();
    }
    public List<JobAppResponseByEmployer> getApplicationsForJobSeeker(Long jobSeekerId) {
        List<JobApplication> applications = jobApplicationRepository.findByUser_Id(jobSeekerId);

        return applications.stream().map(app -> {
            Job job = app.getJob();

            // Map JobApplication to JobAppResponseByEmployer
            return new JobAppResponseByEmployer(
                    job.getTitle(),
                    job.getCompany(),
                    app.getStatus() != null ? app.getStatus().name() : "Unknown",  // handle null status
                    job.getId(),
                    app.getAppliedDate() // assuming this exists in JobApplication
            );
        }).collect(Collectors.toList());
    }

}
