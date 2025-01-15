package com.tennis.platform.service;

import com.tennis.platform.dto.CoachDTO;
import com.tennis.platform.model.Coach;

import java.util.List;

public interface CoachService {
    List<CoachDTO> getAllCoaches();
    CoachDTO getCoachById(Long id);
    CoachDTO updateCoach(Long id, CoachDTO coachDTO);
} 