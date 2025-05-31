package com.jobportal.controller;


import com.jobportal.dto.AdminApplicationReviewDTO;
import com.jobportal.service.AdminApplicationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/admin/applications-review")
@CrossOrigin(origins = "*") // Allow frontend access (update as needed)
public class AdminApplicationReviewController {

    @Autowired
    private AdminApplicationReviewService applicationReviewService;

    @GetMapping
    public List<AdminApplicationReviewDTO> getAllApplications() {
        return applicationReviewService.getAllApplicationsForAdmin();
    }
}
