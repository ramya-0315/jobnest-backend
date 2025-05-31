package com.jobportal.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSettingsDTO {

    private String email;
    private String preferredRoles;
    private String preferredLocations;
    private boolean preferRecentJobs;
}
