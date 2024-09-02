package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dtos.signupDto;
import com.example.api.utils.JwtUtils;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    public String userSignupToken(signupDto signupDto) {
        return jwtUtils.generateToken(signupDto);
    }

    public String isValidToken(String token) {
        return jwtUtils.isValidToken(token);
    }
}
