package com.jobportal.repository;


import com.jobportal.model.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeekerProfile, Long> {
}
