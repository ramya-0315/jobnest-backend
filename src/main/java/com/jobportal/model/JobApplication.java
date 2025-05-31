package com.jobportal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jobportal.dto.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String resumeUrl; // ✅ Store file path as string

    private String skills; // ✅ New field for comma-separated skills

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    private LocalDate appliedDate;

    @CreationTimestamp
    private LocalDateTime appliedAt;
}
