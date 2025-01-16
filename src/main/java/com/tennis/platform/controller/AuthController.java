package com.tennis.platform.controller;

import com.tennis.platform.dto.LoginDTO;
import com.tennis.platform.dto.UserDTO;
import com.tennis.platform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
//
//        System.out.println("login >>> " + loginDTO);
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginDTO.getEmail(),
//                            loginDTO.getPassword()
//                    )
//            );
//
//            System.out.println(authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "登入成功");
//            System.out.println("登入成功");
//            return ResponseEntity.ok(response);
//        } catch (BadCredentialsException e) {
//            System.out.println("帳號或密碼錯誤");
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "帳號或密碼錯誤");
//            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(response);
//        } catch (Exception e) {
//            Map<String, String> response = new HashMap<>();
//            response.put("message", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "註冊成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        Map<String, String> response = new HashMap<>();
        response.put("message", "登出成功");
        return ResponseEntity.ok(response);
    }
}
