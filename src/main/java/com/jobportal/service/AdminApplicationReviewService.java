package com.jobportal.service;


import com.jobportal.dto.AdminApplicationReviewDTO;

import java.util.List;

public interface AdminApplicationReviewService {
    List<AdminApplicationReviewDTO> getAllApplicationsForAdmin();
}
