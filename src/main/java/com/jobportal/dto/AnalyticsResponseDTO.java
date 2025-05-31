package com.jobportal.dto;

public class AnalyticsResponseDTO {
    private long totalJobSeekers;
    private long totalEmployers;
    private long totalJobs;
    private long totalApplications;

    // Constructors
    public AnalyticsResponseDTO() {}

    public AnalyticsResponseDTO(long totalJobSeekers, long totalEmployers, long totalJobs, long totalApplications) {
        this.totalJobSeekers = totalJobSeekers;
        this.totalEmployers = totalEmployers;
        this.totalJobs = totalJobs;
        this.totalApplications = totalApplications;
    }

    // Getters and Setters
    public long getTotalJobSeekers() { return totalJobSeekers; }
    public void setTotalJobSeekers(long totalJobSeekers) { this.totalJobSeekers = totalJobSeekers; }

    public long getTotalEmployers() { return totalEmployers; }
    public void setTotalEmployers(long totalEmployers) { this.totalEmployers = totalEmployers; }

    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }
}