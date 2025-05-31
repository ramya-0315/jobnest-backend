package com.jobportal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.model.Job;
import com.jobportal.model.JobApplication;
import com.jobportal.model.User;
import com.jobportal.dto.ApplicationStatus;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.JobSeekerProfileRepository;
import com.jobportal.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
public class JobApplicationService {

    private static final String UPLOAD_DIR = "uploads/resumes/"; // fallback if needed

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private Cloudinary cloudinary; // ✅ Now properly autowired

    public void saveApplication(String name, String email, String coverLetter, MultipartFile resume, Long jobId, String skills) {
        try {
            JobApplication application = new JobApplication();
            application.setName(name);
            application.setEmail(email);
            application.setCoverLetter(coverLetter);
            application.setSkills(skills);
            application.setStatus(ApplicationStatus.PENDING);
            application.setAppliedDate(LocalDate.now());

            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));
            application.setJob(job);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            application.setUser(user);

            // ✅ Upload resume to Cloudinary
            if (!resume.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(resume.getBytes(), ObjectUtils.emptyMap());
                String resumeUrl = uploadResult.get("secure_url").toString();
                application.setResumeUrl(resumeUrl);
            }

            // ✅ Fallback to profile resume if resume not uploaded
            if (application.getResumeUrl() == null) {
                jobSeekerProfileRepository.findByEmail(user.getEmail()).ifPresent(profile -> {
                    application.setResumeUrl(profile.getResumeUrl());
                });
            }

            jobApplicationRepository.save(application);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume.", e);
        }
    }
}
