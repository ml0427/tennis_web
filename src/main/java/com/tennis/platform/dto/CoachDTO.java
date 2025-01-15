package com.tennis.platform.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CoachDTO {
    private Long id;
    private String username;
    private String email;
    private String experience;
    private String certificates;
    private String specialties;
    private String locations;
    private BigDecimal hourlyRate;
    private String availableTime;
    private Double rating;
    private Integer totalRatings;
} 