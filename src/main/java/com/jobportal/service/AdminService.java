package com.jobportal.service;

import com.jobportal.dto.AdminUpdateRequest;
import com.jobportal.model.Admin;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.AdminRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public AdminService(UserRepository userRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    // ✅ Get all users with pagination
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // ✅ Fallback to non-paginated user list (used internally if needed)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Get user by ID for controller check
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // ✅ Get job by ID for controller check
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // ✅ Delete user with pre-check
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // ✅ Delete job with pre-check
    public void deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        } else {
            throw new RuntimeException("Job not found");
        }
    }
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin updateAdmin(Long adminId, AdminUpdateRequest request) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }

        Admin admin = optionalAdmin.get();

        if (request.getName() != null) admin.setName(request.getName());
        if (request.getEmail() != null) admin.setEmail(request.getEmail());
        if (request.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return adminRepository.save(admin);
    }
}
