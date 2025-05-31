package com.jobportal.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class JobRecommendationRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    private List<String> skills;
}
