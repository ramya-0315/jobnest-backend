package com.jobportal.service;

import com.jobportal.dto.JobDTO;
import org.springframework.data.domain.Page;

public interface RecommendedJobsService {
    Page<JobDTO> getRecommendedJobs(String email, int page, int size);
}
