package com.tennis.platform.security;

import com.tennis.platform.model.User;
import com.tennis.platform.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("in loadUserByUsername >>>" + username);
        try {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("用戶不存在"));
            System.out.println(user);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
            );
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw  e;
        }
    }
}