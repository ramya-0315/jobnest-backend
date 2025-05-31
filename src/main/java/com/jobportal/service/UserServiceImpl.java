package com.jobportal.service;

import com.jobportal.dto.*;
import com.jobportal.exception.InvalidPasswordException;
import com.jobportal.exception.UserNotFoundException;
import com.jobportal.model.Employer;
import com.jobportal.model.Job;
import com.jobportal.model.JobApplication;
import com.jobportal.model.User;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.util.JwtUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmployerRepository employerRepository,
                           JobRepository jobRepository,
                           JobApplicationRepository jobApplicationRepository,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    @Transactional
    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + updatedUser.getId()));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail().trim().toLowerCase());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User getUserByEmail(String email) {
        String cleanedEmail = email.trim().toLowerCase();
        return userRepository.findByEmail(cleanedEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        String rawPassword = request.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse("Bearer " + token, user);
    }

    @Override
    public void registerUser(SignupRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword();

        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Enforce Gmail domain
        if (!email.endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Only Gmail addresses are allowed");
        }

        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        // Validate password strength
        if (!isStrongPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain uppercase, lowercase, number, and special character");
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(password);

        // Create user
        User user = new User();
        user.setFullName(request.getName());
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(request.getRole().toUpperCase());
        user.setLocation(request.getLocation());
        user.setPhone(request.getPhone());
        userRepository.save(user);

        // If employer role, save employer too
        if ("EMPLOYER".equalsIgnoreCase(request.getRole())) {
            Employer employer = Employer.builder()
                    .companyName(request.getCompanyName())
                    .email(email)
                    .password(encodedPassword)
                    .build();
            employerRepository.save(employer);
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isStrongPassword(String password) {
        // At least 8 characters, 1 upper, 1 lower, 1 digit, 1 special char
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }


    @Override
    public boolean sendPasswordResetLink(String email) {
        String cleanedEmail = email.trim().toLowerCase();
        Optional<User> optionalUser = userRepository.findByEmail(cleanedEmail);

        if (optionalUser.isEmpty()) {
            System.out.println("No user found for email: " + email);
            return false;
        }

        System.out.println("Password reset link sent to: " + email);
        return true;
    }

    @Override
    @Transactional
    public boolean resetPassword(ResetPasswordRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            System.out.println("No user found for password reset: " + email);
            return false;
        }

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        System.out.println("Password successfully reset for: " + email);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<JobResponseDTO> getAllJobsForAdmin() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private JobResponseDTO mapToDTO(Job job) {
        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .salaryRange(job.getSalaryRange())
                .description(job.getDescription())
                .type(job.getType())
                .experience(job.getExperience())
                .postedDate(job.getPostedDate())
                .employerName(job.getEmployer().getCompanyName())
                .build();
    }

    @Override
    public JobResponseDTO updateJob(Long id, JobRequestDTO jobRequestDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(jobRequestDTO.getTitle());
        job.setDescription(jobRequestDTO.getDescription());
        job.setLocation(jobRequestDTO.getLocation());
        job.setSalaryRange(jobRequestDTO.getSalaryRange());
        job.setCompany(jobRequestDTO.getCompany());
        job.setExperience(jobRequestDTO.getExperience());

        jobRepository.save(job);

        return modelMapper.map(job, JobResponseDTO.class);
    }

    @Override
    public void deleteJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getApplications() != null && !job.getApplications().isEmpty()) {
            throw new RuntimeException("Cannot delete job with existing applications. Please archive or remove applications first.");
        }

        jobRepository.delete(job);
    }

}
