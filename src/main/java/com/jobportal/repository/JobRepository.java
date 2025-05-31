package com.jobportal.repository;

import com.jobportal.model.Job;
import com.jobportal.model.Employer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // For Employer side (already existing)
    List<Job> findByEmployer(Employer employer);

    Page<Job> findByEmployer(Employer employer, Pageable pageable);

    @Query("SELECT COUNT(j) FROM Job j")
    long countAllJobs();

    // ✅ For Jobseeker Recommended Jobs (new one)
    @Query("SELECT j FROM Job j WHERE " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :role, '%')) " +
            "OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    Page<Job> findRecommendedJobs(@Param("role") String role, @Param("location") String location, Pageable pageable);

}
