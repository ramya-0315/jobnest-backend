package com.jobportal.repository;

import com.jobportal.model.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Long> {
    // No extra methods needed, just use findAll()
}
