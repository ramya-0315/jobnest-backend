package com.jobportal.controller;

import com.jobportal.service.AdminAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminAnalyticsController {

    @Autowired
    private AdminAnalyticsService analyticsService;

    @GetMapping("/analytics")
    public ResponseEntity<com.jobportal.dto.AnalyticsResponseDTO> getAnalytics() {
        com.jobportal.dto.AnalyticsResponseDTO stats = analyticsService.getPlatformStats();
        return ResponseEntity.ok(stats);
    }
}