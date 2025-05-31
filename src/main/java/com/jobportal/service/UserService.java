package com.jobportal.service;

import com.jobportal.dto.*;
import com.jobportal.model.User;

import java.util.List;

public interface UserService {
    LoginResponse authenticate(LoginRequest request);

    void registerUser(SignupRequest request);


    User updateUser(User updatedUser);

    User getUserById(Long userId);

    boolean sendPasswordResetLink(String email);

    boolean resetPassword(ResetPasswordRequest request);

    User getUserByEmail(String email);

    // 🔥 NEW
    List<UserDTO> getAllUsers();


    void deleteUserById(Long id);
    List<JobResponseDTO> getAllJobsForAdmin();
    JobResponseDTO updateJob(Long id, JobRequestDTO jobRequestDTO);
    void deleteJobById(Long id);


}
