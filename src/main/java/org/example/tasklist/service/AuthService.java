package org.example.tasklist.service;

import org.example.tasklist.web.dto.auth.JwtRequest;
import org.example.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
