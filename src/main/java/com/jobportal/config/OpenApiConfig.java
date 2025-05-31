package com.jobportal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI jobPortalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Portal - (JobNest) API")
                        .description("Backend REST API documentation for the Job Portal - (JobNest) Application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Ramya")
                                .email("ramyacse2024@gmail.com")
                                .url("https://yourportfolio.com"))
                );
    }
}
