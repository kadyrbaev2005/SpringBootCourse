package com.medieval.taxcollector.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String tokenType;
    private String accessToken;
    private long expiresInMs;
    private String username;
    private List<String> roles;
}
