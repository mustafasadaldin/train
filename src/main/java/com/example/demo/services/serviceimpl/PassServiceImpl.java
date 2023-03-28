package com.example.demo.services.serviceimpl;

import com.example.demo.services.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PassServiceImpl implements PasswordService {

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder().encode(password);
    }

    @Override
    public boolean isTruePass(String password, String hashedPass) {
        return passwordEncoder().matches(password, hashedPass);
    }

    @Override
    public String genRandomPassword() {
        return UUID.randomUUID().toString();
    }

}
