package com.jobportal.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long employerId;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
}

