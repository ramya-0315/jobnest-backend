package com.jobportal.controller;

import com.jobportal.dto.JobDTO;
import com.jobportal.service.RecommendedJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobseeker/recommended-jobs")
@RequiredArgsConstructor
public class RecommendedJobsController {

    private final RecommendedJobsService recommendedJobsService;

    @GetMapping
    public Page<JobDTO> getRecommendedJobs(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return recommendedJobsService.getRecommendedJobs(email, page, size);
    }
}
