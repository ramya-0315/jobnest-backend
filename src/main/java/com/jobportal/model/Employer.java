package com.jobportal.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employer")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String email;
    private String password;
    private String location;   // ✅ Add this
    private String phone;      // ✅ Add this

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<Job> jobs;
}
