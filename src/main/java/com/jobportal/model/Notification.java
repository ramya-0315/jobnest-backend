package com.jobportal.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employerId; // Who should receive this

    private String message;
    @Column(name = "`read`")
    private boolean read;

    private LocalDateTime createdAt;
}
