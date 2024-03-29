package org.example.tasklist.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.tasklist.domain.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @SneakyThrows
    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) {
        String bearerToken = ((HttpServletRequest) servletRequest)
                .getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null
                && jwtTokenProvider.validateToken(bearerToken)) {
            try {
                Authentication authentication
                        = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null) {
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            } catch (ResourceNotFoundException ignore) {
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
