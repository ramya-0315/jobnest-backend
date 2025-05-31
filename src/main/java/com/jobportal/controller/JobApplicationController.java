package com.jobportal.controller;

import com.jobportal.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend to send requests

@RestController
@RequestMapping("/api")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("coverLetter") String coverLetter,
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("jobId") Long jobId,
            @RequestParam("skills") String skills) {

        if (resume.isEmpty()) {
            return ResponseEntity.badRequest().body("Resume file is missing");
        }

        try {
            jobApplicationService.saveApplication(name, email, coverLetter, resume, jobId, skills);
            return ResponseEntity.ok("Application submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to process application: " + e.getMessage());
        }
    }


}

