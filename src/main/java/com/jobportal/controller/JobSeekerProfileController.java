package com.jobportal.controller;

import com.jobportal.model.JobSeekerProfile;
import com.jobportal.service.JobSeekerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class JobSeekerProfileController {

    private final JobSeekerProfileService profileService;

    public JobSeekerProfileController(JobSeekerProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/save")
    public ResponseEntity<JobSeekerProfile> saveProfile(
            @RequestParam("name") String name,
            @RequestParam("role") String role,
            @RequestParam("location") String location,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("summary") String summary,
            @RequestParam("skills") List<String> skills,
            @RequestParam(value = "resume", required = false) MultipartFile resume,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic,
            @RequestParam(value = "githubUrl", required = false) String githubUrl
    ) throws IOException {

        JobSeekerProfile profile = new JobSeekerProfile();
        profile.setName(name);
        profile.setRole(role);
        profile.setLocation(location);
        profile.setEmail(email);
        profile.setPhone(phone);
        profile.setSummary(summary);
        profile.setSkills(skills);
        profile.setGithubUrl(githubUrl);

        if (resume != null && !resume.isEmpty()) {
            String resumeUrl = profileService.uploadToCloudinary(resume);
            profile.setResumeUrl(resumeUrl);
        }

        if (profilePic != null && !profilePic.isEmpty()) {
            String profilePicUrl = profileService.uploadToCloudinary(profilePic);
            profile.setProfilePicUrl(profilePicUrl);
        }

        JobSeekerProfile savedProfile = profileService.saveOrUpdateProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/{email}")
    public ResponseEntity<JobSeekerProfile> getProfile(@PathVariable String email) {
        return profileService.getProfileByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteProfile(@PathVariable String email) {
        profileService.deleteProfileByEmail(email);
        return ResponseEntity.ok("Profile deleted successfully.");
    }
}
