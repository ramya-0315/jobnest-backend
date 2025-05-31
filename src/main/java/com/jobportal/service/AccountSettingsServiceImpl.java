package com.jobportal.service;

import com.jobportal.dto.AccountSettingsDTO;
import com.jobportal.model.AccountSettings;
import com.jobportal.repository.AccountSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountSettingsServiceImpl implements AccountSettingsService {

    private final AccountSettingsRepository accountSettingsRepository;

    @Override
    public AccountSettings saveOrUpdateAccountSettings(AccountSettingsDTO dto) {
        // Check if settings already exist for this email
        AccountSettings settings = accountSettingsRepository.findByEmail(dto.getEmail())
                .orElse(AccountSettings.builder().email(dto.getEmail()).build());

        settings.setPreferredRoles(dto.getPreferredRoles());
        settings.setPreferredLocations(dto.getPreferredLocations());
        settings.setPreferRecentJobs(dto.isPreferRecentJobs());

        return accountSettingsRepository.save(settings);
    }

    @Override
    public AccountSettings getAccountSettingsByEmail(String email) {
        return accountSettingsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account settings not found for email: " + email));
    }
}