package org.example.tasklist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;
}
