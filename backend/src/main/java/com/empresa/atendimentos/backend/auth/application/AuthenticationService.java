package com.empresa.atendimento.auth.application;

import com.empresa.atendimento.auth.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String username, String password) {
        // TODO: validate credentials against user repository
        return jwtTokenProvider.generateToken(username);
    }
}
