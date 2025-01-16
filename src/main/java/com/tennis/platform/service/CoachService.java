package com.tennis.platform.service;

import com.tennis.platform.dto.CoachDTO;
import com.tennis.platform.model.Coach;
import com.tennis.platform.repository.CoachRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public List<CoachDTO> getAllCoaches() {
        return coachRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public CoachDTO getCoachById(Long id) {
        Coach coach = coachRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("教練不存在"));
        return convertToDTO(coach);
    }

    public CoachDTO updateCoach(Long id, CoachDTO coachDTO) {
        Coach coach = coachRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("教練不存在"));
        
        coach.setExperience(coachDTO.getExperience());
        coach.setHourlyRate(coachDTO.getHourlyRate());
        coach.setCertificates(coachDTO.getCertificates());
        coach.setSpecialties(coachDTO.getSpecialties());
        coach.setLocations(coachDTO.getLocations());
        coach.setAvailableTime(coachDTO.getAvailableTime());
        
        return convertToDTO(coachRepository.save(coach));
    }

    private CoachDTO convertToDTO(Coach coach) {
        CoachDTO dto = new CoachDTO();
        BeanUtils.copyProperties(coach, dto);
        dto.setEmail(coach.getUser().getEmail());
        return dto;
    }
} 