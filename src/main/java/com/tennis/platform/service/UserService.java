package com.tennis.platform.service;

import com.tennis.platform.model.User;
import com.tennis.platform.dto.UserDTO;
import com.tennis.platform.dto.LoginDTO;

public interface UserService {
    User registerUser(UserDTO userDTO);
    String login(LoginDTO loginDTO);
    UserDTO getCurrentUserProfile();
    UserDTO updateProfile(UserDTO userDTO);
    UserDTO getUserInfo(String username);
} 