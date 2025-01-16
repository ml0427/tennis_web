package com.tennis.platform.service;

import com.tennis.platform.dto.UserDTO;
import com.tennis.platform.model.Coach;
import com.tennis.platform.model.User;
import com.tennis.platform.repository.CoachRepository;
import com.tennis.platform.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;  // 新增
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final PasswordEncoder passwordEncoder;  // 新增

    public UserService(
            UserRepository userRepository,
            CoachRepository coachRepository,
            PasswordEncoder passwordEncoder  // 新增
    ) {
        this.userRepository = userRepository;
        this.coachRepository = coachRepository;
        this.passwordEncoder = passwordEncoder;  // 新增
    }
    
    public void registerUser(UserDTO userDTO) {
        if (userDTO.getEmail() == null){
            throw new RuntimeException("Email必填");
        }
        if (userDTO.getPassword() == null){
            throw new RuntimeException("Password必填");
        }
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            System.out.println(optionalUser.get());
            throw new RuntimeException("此郵箱已被使用");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // 修改：加密密碼
        user.setRole(userDTO.getRole());
        user.setCreatedAt(LocalDateTime.now());
        // 保存用戶
        user = userRepository.save(user);
        // 如果是教練，創建教練資料
        if ("ROLE_COACH".equals(user.getRole())) {
            Coach coach = new Coach();
            coach.setUser(user);
            coach.setRating(0.0);
            coach.setTotalRatings(0);
            coachRepository.save(coach);
        }
    }

    public UserDTO getCurrentUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserInfo(email);
    }

    public UserDTO updateProfile(UserDTO userDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
        
        // 更新基本資料
        if (userDTO.getEmail() != null) {
            if (!user.getEmail().equals(userDTO.getEmail()) && 
                userRepository.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException("此郵箱已被使用");
            }
            user.setEmail(userDTO.getEmail());
        }
        
        // 如果是教練，更新教練資料
        if ("ROLE_COACH".equals(user.getRole())) {
            User finalUser = user;
            Coach coach = coachRepository.findByUser(user)
                .orElseGet(() -> {
                    Coach newCoach = new Coach();
                    newCoach.setUser(finalUser);
                    newCoach.setRating(0.0);
                    newCoach.setTotalRatings(0);
                    return newCoach;
                });
                
            coach.setExperience(userDTO.getExperience());
            coach.setCertificates(userDTO.getCertificates());
            coach.setSpecialties(userDTO.getSpecialties());
            coach.setLocations(userDTO.getLocations());
            coach.setHourlyRate(userDTO.getHourlyRate());
            coach.setAvailableTime(userDTO.getAvailableTime());
            
            coachRepository.save(coach);
        }
        
        user = userRepository.save(user);
        return getUserInfo(email);
    }

    public UserDTO getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
            
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        
        // 如果是教練，添加教練資料
        if ("ROLE_COACH".equals(user.getRole())) {
            Coach coach = coachRepository.findByUser(user)
                .orElseGet(() -> {
                    Coach newCoach = new Coach();
                    newCoach.setUser(user);
                    newCoach.setRating(0.0);
                    newCoach.setTotalRatings(0);
                    return coachRepository.save(newCoach);
                });
                
            userDTO.setExperience(coach.getExperience());
            userDTO.setCertificates(coach.getCertificates());
            userDTO.setSpecialties(coach.getSpecialties());
            userDTO.setLocations(coach.getLocations());
            userDTO.setHourlyRate(coach.getHourlyRate());
            userDTO.setAvailableTime(coach.getAvailableTime());
            userDTO.setRating(coach.getRating());
            userDTO.setTotalRatings(coach.getTotalRatings());
        }
        
        return userDTO;
    }
}