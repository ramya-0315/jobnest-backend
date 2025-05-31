package com.jobportal.repository;

import com.jobportal.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EmployerApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a WHERE a.employer.email = :email")
    List<Application> findByEmployerEmail(String email);
}
