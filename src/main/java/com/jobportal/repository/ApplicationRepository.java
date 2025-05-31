package com.jobportal.repository;

import com.jobportal.model.Application;
import com.jobportal.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJob_Employer(Employer employer);
    @Query("SELECT COUNT(a) FROM JobApplication a")
    long countAllApplications();
}
