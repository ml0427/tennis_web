package com.tennis.platform.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    
    // 教練相關資料
    private String experience;
    private String certificates;
    private String specialties;
    private String locations;
    private Integer hourlyRate;
    private String availableTime;
    private Double rating;
    private Integer totalRatings;
} 