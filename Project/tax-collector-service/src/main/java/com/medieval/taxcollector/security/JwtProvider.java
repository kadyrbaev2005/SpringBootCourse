package com.medieval.taxcollector.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {
        byte[] keyBytes = resolveKeyBytes(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }

    private static byte[] resolveKeyBytes(String secret) {
        String trimmed = secret.trim();
        if (trimmed.startsWith("base64:")) {
            return Decoders.BASE64.decode(trimmed.substring("base64:".length()));
        }
        return trimmed.getBytes(StandardCharsets.UTF_8);
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant exp = now.plusMillis(expirationMs);
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.startsWith("ROLE_") ? a.substring("ROLE_".length()) : a)
                .toList();

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public Authentication toAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        String username = claims.getSubject();
        Collection<GrantedAuthority> authorities = extractAuthorities(claims);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    @SuppressWarnings("unchecked")
    private static Collection<GrantedAuthority> extractAuthorities(Claims claims) {
        Object raw = claims.get("roles");
        List<GrantedAuthority> out = new ArrayList<>();
        if (raw instanceof List<?> list) {
            for (Object o : list) {
                if (o != null) {
                    String r = o.toString();
                    if (!r.startsWith("ROLE_")) {
                        r = "ROLE_" + r;
                    }
                    out.add(new SimpleGrantedAuthority(r));
                }
            }
        }
        return out;
    }
}
