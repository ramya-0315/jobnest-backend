package com.jobportal.controller;


import com.jobportal.dto.AccountSettingsDTO;
import com.jobportal.model.AccountSettings;
import com.jobportal.service.AccountSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account-settings")
@RequiredArgsConstructor
public class AccountSettingsController {

    private final AccountSettingsService accountSettingsService;

    @PostMapping("/save")
    public ResponseEntity<AccountSettings> saveAccountSettings(@RequestBody AccountSettingsDTO accountSettingsDTO) {
        AccountSettings savedSettings = accountSettingsService.saveOrUpdateAccountSettings(accountSettingsDTO);
        return ResponseEntity.ok(savedSettings);
    }

    @GetMapping("/{email}")
    public ResponseEntity<AccountSettings> getAccountSettings(@PathVariable String email) {
        AccountSettings settings = accountSettingsService.getAccountSettingsByEmail(email);
        return ResponseEntity.ok(settings);
    }
}
