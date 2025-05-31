package com.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAppResponseByEmployer{
    private String Title;
    private String companyName;
    private String Status;
    private Long jobId;
    private LocalDate appliedDate;// ACCEPTED / REJECTED / PENDING
}
