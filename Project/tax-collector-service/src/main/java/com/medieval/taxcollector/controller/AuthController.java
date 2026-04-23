package com.medieval.taxcollector.controller;

import com.medieval.taxcollector.dto.LoginRequest;
import com.medieval.taxcollector.dto.LoginResponse;
import com.medieval.taxcollector.domain.AppUser;
import com.medieval.taxcollector.security.JwtProvider;
import com.medieval.taxcollector.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        AppUser user;
        try {
            user = userService.findUserByUsername(request.getUsername());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect login or password");
        }

        boolean passwordCorrect = userService.checkPassword(user, request.getPassword());
        if (!passwordCorrect) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect login or password");
        }

        List<String> roles = userService.getUserRoles(user);
        String token = jwtProvider.generateToken(user.getUsername(), roles);
        return LoginResponse.builder()
                .tokenType("Bearer")
                .accessToken(token)
                .expiresInMs(jwtProvider.getExpirationMs())
                .username(user.getUsername())
                .roles(userService.getUserRoles(user))
                .build();
    }
}
