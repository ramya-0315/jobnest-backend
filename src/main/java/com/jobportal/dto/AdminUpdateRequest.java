package com.jobportal.dto;
// dto/AdminUpdateRequest.java
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateRequest {
    private String name;
    private String email;
    private String password;
}
