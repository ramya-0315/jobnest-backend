package com.jobportal.service;

import com.jobportal.dto.NotificationDTO;
import com.jobportal.model.Notification;
import com.jobportal.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationDTO> getNotificationsForEmployer(Long employerId) {
        return notificationRepository.findByEmployerIdOrderByCreatedAtDesc(employerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null && !notification.isRead()) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    public void createNotification(Long employerId, String message) {
        Notification notification = new Notification();
        notification.setEmployerId(employerId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getEmployerId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}