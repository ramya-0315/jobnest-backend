package com.jobportal.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Data
public class SignupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "JOBSEEKER|EMPLOYER|ADMIN", message = "Role must be either JOBSEEKER, EMPLOYER, or ADMIN")
    private String role;
    private String location;
    private String phone;
    private String companyName;
}
