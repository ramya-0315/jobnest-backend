package com.jobportal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account_settings")
public class AccountSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String preferredRoles; // Example: "Backend Developer, Full Stack Developer"

    private String preferredLocations; // Example: "Bangalore, Hyderabad"

    private boolean preferRecentJobs; // true = show latest jobs first
}
