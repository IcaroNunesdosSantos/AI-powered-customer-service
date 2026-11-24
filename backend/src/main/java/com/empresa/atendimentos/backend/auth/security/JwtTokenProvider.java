package com.empresa.atendimento.auth.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "DefaultSecretKeyForJwtTokenGeneration1234567890";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    public String generateToken(String username) {
        try {
            String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
            String payload = Base64.getEncoder().encodeToString(
                String.format("{\"sub\":\"%s\",\"exp\":%d}", username, System.currentTimeMillis() + EXPIRATION)
                    .getBytes(StandardCharsets.UTF_8)
            );
            String signatureInput = header + "." + payload;
            String signature = sign(signatureInput);
            return signatureInput + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;
            String signatureInput = parts[0] + "." + parts[1];
            return sign(signatureInput).equals(parts[2]);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            String payload = new String(Base64.getDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
            return payload.replace("{\"sub\":\"", "").replace("\"}", "");
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract username from token", e);
        }
    }

    public void setAuthentication(String username) {
        User user = new User(username, "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Authentication getAuthentication() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
    }

    private String sign(String input) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(keySpec);
        byte[] macData = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(macData);
    }
}
