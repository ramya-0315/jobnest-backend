package com.jobportal.repository;

import com.jobportal.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findByUser_Id(Long id);

    // ✨ Add this extra query for employer
    List<JobApplication> findByJob_Employer_Email(String email);
}
