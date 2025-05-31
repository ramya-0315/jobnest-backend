package com.jobportal.repository;

import com.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'JOB_SEEKER'")
    long countJobSeekers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'EMPLOYER'")
    long countEmployers();
}
