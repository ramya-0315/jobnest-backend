package com.jobportal.controller;

import com.jobportal.dto.*;
import com.jobportal.model.Job;
import com.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/post")
    public JobResponseDTO postJob(@RequestBody JobRequestDTO jobDTO, Authentication auth) {
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_EMPLOYER"))) {
            throw new AccessDeniedException("Only employers can post jobs.");
        }
        String email = auth.getName();
        return jobService.postJob(email, jobDTO);
    }

    @GetMapping("/my-jobs")
    public PagedJobResponseDTO getMyJobs(@RequestParam(defaultValue = "1") int page, Authentication auth) {
        String email = auth.getName();
        return jobService.getPagedJobsByEmployer(email, page);
    }

    @PutMapping("/update/{id}")
    public JobResponseDTO updateJob(@PathVariable Long id, @RequestBody JobRequestDTO jobDTO, Authentication auth) {
        String email = auth.getName();
        return jobService.updateJob(id, jobDTO, email);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteJob(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        jobService.deleteJob(id, email);
    }


}