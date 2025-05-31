package com.jobportal.dto;

import lombok.Data;

@Data
public class EmployerProfileDTO {
    private Long id; // ✅ Add this line
    private String name;
    private String industry;
    private String location;
    private String about;
}
