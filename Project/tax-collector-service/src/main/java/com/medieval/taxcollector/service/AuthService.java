package com.medieval.taxcollector.service;

import com.medieval.taxcollector.dto.LoginRequest;
import com.medieval.taxcollector.dto.LoginResponse;
import com.medieval.taxcollector.security.JwtProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtProvider.generateToken(authentication);
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.startsWith("ROLE_") ? a.substring("ROLE_".length()) : a)
                .toList();

        return LoginResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .expiresInMs(jwtProvider.getExpirationMs())
                .username(authentication.getName())
                .roles(roles)
                .build();
    }
}
