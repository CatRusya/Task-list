package org.example.tasklist.service.impl;

import org.example.tasklist.service.AuthService;
import org.example.tasklist.web.dto.auth.JwtRequest;
import org.example.tasklist.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
