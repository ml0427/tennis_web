package com.tennis.platform.service.impl;

import com.tennis.platform.dto.CoachDTO;
import com.tennis.platform.model.Coach;
import com.tennis.platform.repository.CoachRepository;
import com.tennis.platform.service.CoachService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;

    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public List<CoachDTO> getAllCoaches() {
        return coachRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CoachDTO getCoachById(Long id) {
        Coach coach = coachRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("教練不存在"));
        return convertToDTO(coach);
    }

    @Override
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
        dto.setUsername(coach.getUser().getUsername());
        dto.setEmail(coach.getUser().getEmail());
        return dto;
    }
} 