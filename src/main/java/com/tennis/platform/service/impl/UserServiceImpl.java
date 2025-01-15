package com.tennis.platform.service.impl;

import com.tennis.platform.dto.LoginDTO;
import com.tennis.platform.dto.UserDTO;
import com.tennis.platform.model.Coach;
import com.tennis.platform.model.User;
import com.tennis.platform.repository.CoachRepository;
import com.tennis.platform.repository.UserRepository;
import com.tennis.platform.security.JwtUtils;
import com.tennis.platform.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(
            UserRepository userRepository,
            CoachRepository coachRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.userRepository = userRepository;
        this.coachRepository = coachRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    
    @Override
    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("用戶名已存在");
        }
        
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("郵箱已被使用");
        }
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
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
        
        return user;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
            
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密碼錯誤");
        }
        
        return jwtUtils.generateToken(new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        ));
    }

    @Override
    public UserDTO getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
            
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
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
        }
        
        return userDTO;
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
        
        // 更新基本資料
        if (userDTO.getEmail() != null) {
            if (!user.getEmail().equals(userDTO.getEmail()) && 
                userRepository.existsByEmail(userDTO.getEmail())) {
                throw new RuntimeException("郵箱已被使用");
            }
            user.setEmail(userDTO.getEmail());
        }
        
        // 如果是教練，更新教練資料
        if ("ROLE_COACH".equals(user.getRole())) {
            User finalUser = user;
            Coach coach = coachRepository.findById(user.getId())
                .orElseGet(() -> {
                    Coach newCoach = new Coach();
                    newCoach.setId(finalUser.getId());
                    newCoach.setUser(finalUser);
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
        return getCurrentUserProfile(); // 返回更新後的完整資料
    }

    @Override
    public UserDTO getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("用戶不存在"));
            
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        
        // 如果是教練，添加教練資料
        if ("ROLE_COACH".equals(user.getRole())) {
            Coach coach = coachRepository.findById(user.getId())
                .orElseGet(() -> {
                    Coach newCoach = new Coach();
                    newCoach.setId(user.getId());
                    newCoach.setUser(user);
                    return coachRepository.save(newCoach);
                });
                
            userDTO.setExperience(coach.getExperience());
            userDTO.setCertificates(coach.getCertificates());
            userDTO.setSpecialties(coach.getSpecialties());
            userDTO.setLocations(coach.getLocations());
            userDTO.setHourlyRate(coach.getHourlyRate());
            userDTO.setAvailableTime(coach.getAvailableTime());
        }
        
        return userDTO;
    }
} 