package com.jobportal.service;

import com.jobportal.dto.EmployerApplicationResponseDTO;
import com.jobportal.dto.UpdateApplicationStatusRequestDTO;
import com.jobportal.dto.JobSeekerProfileResponseDTO;
import com.jobportal.model.JobApplication;
import com.jobportal.model.JobSeekerProfile;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobSeekerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerApplicationServiceImpl implements EmployerApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployerApplicationResponseDTO> getApplicationsForEmployerByEmail(String email) {
        List<JobApplication> applications = applicationRepository.findByJob_Employer_Email(email);

        return applications.stream().map(app -> {
            EmployerApplicationResponseDTO dto = new EmployerApplicationResponseDTO();
            dto.setApplicationId(app.getId());
            dto.setJobId(app.getJob().getId());
            dto.setJobTitle(app.getJob().getTitle());
            dto.setStatus(app.getStatus());

            EmployerApplicationResponseDTO.ApplicantInfo applicant = new EmployerApplicationResponseDTO.ApplicantInfo();
            applicant.setJobSeekerId(app.getUser().getId());
            applicant.setName(app.getUser().getFullName());
            applicant.setEmail(app.getUser().getEmail());
            applicant.setPhoneNumber(app.getUser().getPhone());

            // 🔥 Fetch JobSeekerProfile by Email
            jobSeekerProfileRepository.findByEmail(app.getUser().getEmail()).ifPresent(profile -> {
                if (profile.getResumeUrl() != null && !profile.getResumeUrl().isEmpty()) {
                    applicant.setResumeUrl(profile.getResumeUrl());
                } else {
                    applicant.setResumeUrl(null);
                }
                applicant.setSkills(profile.getSkills());
                applicant.setSummary(profile.getSummary());
            });

            dto.setApplicant(applicant);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequestDTO requestDTO) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(requestDTO.getStatus());
        applicationRepository.save(application);
    }

    @Override
    public JobSeekerProfileResponseDTO getJobSeekerProfile(String email) {
        JobSeekerProfile jobSeeker = jobSeekerProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));
        return modelMapper.map(jobSeeker, JobSeekerProfileResponseDTO.class);
    }
}
