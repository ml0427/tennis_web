package com.tennis.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Spring Security 配置類
 * 用於設定系統的安全性規則、認證方式、授權規則等
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置 Spring Security 的主要過濾鏈
     * 定義了系統的安全規則、登入設定、session管理等
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 關閉 CSRF 防護，因為我們使用 API 方式呼叫
                .csrf().disable()
                // 啟用 CORS 跨域支援
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:8080"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                })
                .and()
                // 配置請求授權規則
                .authorizeRequests()
                // 允許以下路徑無需認證即可訪問
                .antMatchers(
                        "/api/auth/**",     // 認證相關的 API
                        "/h2-console/**",    // H2 資料庫控制台
                        "/error",            // 錯誤頁面
                        "/login",            // 登入頁面
                        "/register",         // 註冊頁面
                        "/",                 // 首頁
                        "/assets/**",        // 靜態資源
                        "/index.html"        // 首頁 HTML
                ).permitAll()
                // 其他所有請求都需要認證
                .anyRequest().authenticated()
                .and()
                // 配置表單登入
                .formLogin()
                // 設定處理登入請求的 URL
                .loginProcessingUrl("/api/auth/login")
                // 登入成功處理器
                .successHandler((request, response, authentication) ->
                        response.setStatus(HttpServletResponse.SC_OK))
                // 登入失敗處理器
                .failureHandler((request, response, exception) ->
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Session 管理
                .sessionManagement()
                // 設定 Session 創建策略為需要時才創建
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 允許 H2 控制台的 frame 顯示
        http.headers().frameOptions().disable();

        return http.build();
    }

    /**
     * 配置密碼編碼器
     * 使用 BCrypt 強雜湊算法進行密碼加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置認證管理器
     * 用於處理認證請求
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
