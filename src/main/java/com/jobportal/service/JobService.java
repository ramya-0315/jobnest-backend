package com.jobportal.service;

import com.jobportal.dto.JobRequestDTO;
import com.jobportal.dto.JobResponseDTO;
import com.jobportal.dto.PagedJobResponseDTO;
import com.jobportal.model.Employer;
import com.jobportal.model.Job;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    // Post a job
    public JobResponseDTO postJob(String employerEmail, JobRequestDTO jobDTO) {
        Employer employer = employerRepository.findByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Job job = Job.builder()
                .title(jobDTO.getTitle())
                .company(jobDTO.getCompany())
                .location(jobDTO.getLocation())
                .salaryRange(jobDTO.getSalaryRange())
                .description(jobDTO.getDescription())
                .type(jobDTO.getType())
                .experience(jobDTO.getExperience())
                .skillsRequired(jobDTO.getSkillsRequired())
                .postedDate(LocalDateTime.now())
                .employer(employer)
                .build();

        job = jobRepository.save(job);
        return mapToDTO(job);
    }

    // Get paged jobs by employer email
    public PagedJobResponseDTO getPagedJobsByEmployer(String email, int page) {
        Employer employer = employerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        int pageIndex = Math.max(page - 1, 0); // ✅ Fix: ensure it's 0-based and not negative
        PageRequest pageable = PageRequest.of(pageIndex, 5);

        Page<Job> jobPage = jobRepository.findByEmployer(employer, pageable);

        List<JobResponseDTO> jobs = jobPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return PagedJobResponseDTO.builder()
                .jobs(jobs)
                .totalPages(jobPage.getTotalPages())
                .totalElements(jobPage.getTotalElements())
                .currentPage(page) // return 1-based page to client
                .build();
    }

    // Update a job
    // Update a job
    public JobResponseDTO updateJob(Long id, JobRequestDTO jobDTO, String email) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getEmployer().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not the owner of this job.");
        }

        job.setTitle(jobDTO.getTitle());
        job.setCompany(jobDTO.getCompany());
        job.setLocation(jobDTO.getLocation());
        job.setSalaryRange(jobDTO.getSalaryRange());
        job.setDescription(jobDTO.getDescription());
        job.setType(jobDTO.getType());
        job.setExperience(jobDTO.getExperience());
        job.setSkillsRequired(jobDTO.getSkillsRequired()); // ✅ Added this line

        jobRepository.save(job);
        return mapToDTO(job);
    }


    // Delete a job
    public void deleteJob(Long id, String email) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getEmployer().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not the owner of this job.");
        }

        jobRepository.delete(job);
    }

    // Get all jobs by employer (non-paginated)
    public List<JobResponseDTO> getJobsByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        return jobRepository.findByEmployer(employer).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert entity to DTO
    private JobResponseDTO mapToDTO(Job job) {
        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .salaryRange(job.getSalaryRange())
                .description(job.getDescription())
                .type(job.getType())
                .experience(job.getExperience())
                .skillsRequired(job.getSkillsRequired()) // ✅ FIXED LINE
                .postedDate(job.getPostedDate())
                .employerName(job.getEmployer().getCompanyName())
                .build();
    }



}
