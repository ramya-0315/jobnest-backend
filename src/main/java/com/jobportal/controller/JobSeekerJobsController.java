package com.jobportal.controller;

import com.jobportal.dto.JobAppResponseByEmployer;
import com.jobportal.dto.JobResponseDTO;
import com.jobportal.model.JobApplication;
import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.JobSeekerJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/jobseeker/jobs")
public class JobSeekerJobsController {

    private final JobSeekerJobService jobSeekerJobService;

    @Autowired
    public JobSeekerJobsController(JobSeekerJobService jobSeekerJobService) {
        this.jobSeekerJobService = jobSeekerJobService;
    }
    @Autowired
    private UserRepository userRepository;

    // GET /api/jobseeker/jobs
    @GetMapping
    public ResponseEntity<List<JobResponseDTO>> getAllAvailableJobs() {
        List<JobResponseDTO> jobs = jobSeekerJobService.getAllAvailableJobs();
        return ResponseEntity.ok(jobs);
    }

    // GET /api/jobseeker/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long id) {
        JobResponseDTO job = jobSeekerJobService.getJobById(id);
        return job != null ? ResponseEntity.ok(job) : ResponseEntity.notFound().build();
    }

    // POST /api/jobseeker/jobs/apply/{jobId}
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyForJob(@PathVariable Long jobId,
                                              @RequestBody JobApplication jobApplication,
                                              Principal principal) {
        boolean isApplied = jobSeekerJobService.applyForJob(jobId, jobApplication, principal);
        if (isApplied) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully applied for the job!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to apply for the job. Please try again.");
        }
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<JobAppResponseByEmployer>> getMyApplications(Principal principal) {
        String email = principal.getName();
        User jobSeeker = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<JobAppResponseByEmployer> myApps = jobSeekerJobService
                .getApplicationsForJobSeeker(jobSeeker.getId());


        return ResponseEntity.ok(myApps);
    }

}
