package com.jobportal.repository;

import com.jobportal.model.AccountSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountSettingsRepository extends JpaRepository<AccountSettings, Long> {
    Optional<AccountSettings> findByEmail(String email);
}
