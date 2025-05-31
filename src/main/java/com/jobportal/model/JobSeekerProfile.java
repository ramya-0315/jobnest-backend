package com.jobportal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private String location;
    private String email;
    private String phone;

    @Column(length = 2000)
    private String summary;

    @ElementCollection
    private List<String> skills;

    private String resumeUrl;  // just store URL
    private String profilePicUrl;
    // ADD these new fields 👇
    private String githubUrl;// store URL
}
