package com.jobportal.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ProfileResponseDTO {
    private String name;
    private String role;
    private String location;
    private String email;
    private String phone;
    private String summary;
    private List<String> skills;
    private String resumeUrl;
    private String profilePicUrl;
    private String githubUrl;
}
