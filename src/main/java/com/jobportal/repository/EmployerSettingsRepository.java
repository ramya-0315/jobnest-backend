package com.jobportal.repository;

import com.jobportal.model.EmployerSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployerSettingsRepository extends JpaRepository<EmployerSettings, Long> {
    Optional<EmployerSettings> findById(Long id);
}
