package com.example.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import com.example.project.dto.ApiResponse;
import com.example.project.exception.TokenInvalidException;
import com.example.project.util.JwtUtil;  // 新增這行
import com.tennis.platform.dto.UserDTO;
import com.tennis.platform.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(false, "請提供認證令牌", null));
        }

        if (!token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(false, "無效的令牌格式", null));
        }

        try {
            String jwtToken = token.substring(7);
            String userId = jwtUtil.getUserIdFromToken(jwtToken);

            if (userId == null || userId.isBlank()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "無效的用戶令牌", null));
            }

            UserDTO userInfo = userService.getUserInfo(userId);
            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "找不到用戶資料", null));
            }

            return ResponseEntity.ok(new ApiResponse(true, "成功獲取用戶資料", userInfo));

        } catch (TokenInvalidException e) {
            logger.error("Token驗證失敗", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(false, "令牌驗證失敗: " + e.getMessage(), null));
        } catch (Exception e) {
            logger.error("獲取用戶資料時發生錯誤", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "系統處理請求時發生錯誤", null));
        }
    }
}
