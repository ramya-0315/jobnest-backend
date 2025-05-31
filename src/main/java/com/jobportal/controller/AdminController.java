package com.jobportal.controller;

import com.jobportal.dto.AdminUpdateRequest;
import com.jobportal.dto.EmployerProfileDTO;
import com.jobportal.model.Admin;
import com.jobportal.service.AdminService;
import com.jobportal.service.EmployerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private EmployerProfileService profileService;

    @GetMapping("/employers")
    public List<EmployerProfileDTO> getAllEmployerProfiles() {
        return profileService.getAllProfiles();
    }
    @DeleteMapping("/employers/{id}")
    public void deleteEmployerProfile(@PathVariable Long id) {
        profileService.deleteProfileById(id);
    }
    @Autowired
    private AdminService adminService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody AdminUpdateRequest request) {
        Admin updatedAdmin = adminService.updateAdmin(id, request);
        return ResponseEntity.ok(updatedAdmin);
    }


}
