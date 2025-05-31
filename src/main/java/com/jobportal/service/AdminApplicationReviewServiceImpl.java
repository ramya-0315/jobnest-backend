package com.jobportal.service;


import com.jobportal.dto.AdminApplicationReviewDTO;
import com.jobportal.model.JobApplication;
import com.jobportal.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminApplicationReviewServiceImpl implements AdminApplicationReviewService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public List<AdminApplicationReviewDTO> getAllApplicationsForAdmin() {
        List<JobApplication> applications = jobApplicationRepository.findAll();

        return applications.stream()
                .map(app -> {
                    String fullName = "Unknown User";
                    String jobTitle = "Unknown Job";
                    String companyName = "Unknown Company";

                    if (app.getUser() != null) {
                        fullName = app.getUser().getFullName();
                    } else {
                        System.out.println("⚠️ Warning: JobApplication ID " + app.getId() + " has no associated User.");
                    }

                    if (app.getJob() != null) {
                        jobTitle = app.getJob().getTitle();
                        companyName = app.getJob().getCompany(); // Make sure getCompany() returns a String
                    } else {
                        System.out.println("⚠️ Warning: JobApplication ID " + app.getId() + " has no associated Job.");
                    }

                    return new AdminApplicationReviewDTO(
                            fullName,
                            jobTitle,
                            companyName,
                            app.getAppliedDate(),
                            app.getStatus() != null ? app.getStatus().name() : "UNKNOWN"
                    );
                })
                .collect(Collectors.toList());
    }


}
