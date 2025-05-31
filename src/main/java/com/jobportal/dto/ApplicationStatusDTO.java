package com.jobportal.dto;

public class ApplicationStatusDTO {
    private String statusName;

    public ApplicationStatusDTO(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
