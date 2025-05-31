package com.jobportal.controller;

import com.jobportal.dto.NotificationDTO;
import com.jobportal.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{employerId}")
    public List<NotificationDTO> getNotifications(@PathVariable Long employerId) {
        return notificationService.getNotificationsForEmployer(employerId);
    }

    @PostMapping("/read/{id}")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestParam Long employerId, @RequestParam String message) {
        notificationService.createNotification(employerId, message);
    }
}
