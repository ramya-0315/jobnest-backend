package com.jobportal.service;

import com.jobportal.model.JobSeekerProfile;
import com.jobportal.repository.JobSeekerProfileRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository profileRepository;
    private final Cloudinary cloudinary;

    public JobSeekerProfileService(JobSeekerProfileRepository profileRepository, Cloudinary cloudinary) {
        this.profileRepository = profileRepository;
        this.cloudinary = cloudinary;
    }

    public String uploadToCloudinary(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url"); // returns https:// URL
    }

    public JobSeekerProfile saveOrUpdateProfile(JobSeekerProfile profile) {
        Optional<JobSeekerProfile> existingProfile = profileRepository.findByEmail(profile.getEmail());
        if (existingProfile.isPresent()) {
            JobSeekerProfile p = existingProfile.get();
            p.setName(profile.getName());
            p.setRole(profile.getRole());
            p.setLocation(profile.getLocation());
            p.setPhone(profile.getPhone());
            p.setSummary(profile.getSummary());
            p.setSkills(profile.getSkills());
            p.setResumeUrl(profile.getResumeUrl());
            p.setProfilePicUrl(profile.getProfilePicUrl());
            return profileRepository.save(p);
        }
        return profileRepository.save(profile);
    }
    public Optional<JobSeekerProfile> getProfileByEmail(String email) {
        return profileRepository.findByEmail(email);  // Use profileRepository, not the static call
    }


    public void deleteProfileByEmail(String email) {
        Optional<JobSeekerProfile> profile = profileRepository.findByEmail(email);
        profile.ifPresent(profileRepository::delete);
    }
}
