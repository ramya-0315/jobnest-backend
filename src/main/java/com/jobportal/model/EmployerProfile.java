
package com.jobportal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String industry;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String about;
    

    // If needed, you can link this to an Employer using a OneToOne mapping
}