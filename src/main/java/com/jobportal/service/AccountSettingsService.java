package com.jobportal.service;

import com.jobportal.dto.AccountSettingsDTO;
import com.jobportal.model.AccountSettings;

public interface AccountSettingsService {

    AccountSettings saveOrUpdateAccountSettings(AccountSettingsDTO accountSettingsDTO);

    AccountSettings getAccountSettingsByEmail(String email);
}