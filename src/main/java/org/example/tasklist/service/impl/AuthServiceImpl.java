package org.example.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.service.AuthService;
import org.example.tasklist.service.UserService;
import org.example.tasklist.web.dto.auth.JwtRequest;
import org.example.tasklist.web.dto.auth.JwtResponse;
import org.example.tasklist.web.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        User user
                = userService.getByUsername(loginRequest.getUsername());
        return JwtResponse.builder()
                .id(user.getId())
                .username(loginRequest.getUsername())
                .accessToken(jwtTokenProvider.createAccessToken(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles()))
                .refreshToken(jwtTokenProvider.createRefreshToken(
                        user.getId(),
                        user.getUsername()))
                .build();
    }

    @Override
    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
