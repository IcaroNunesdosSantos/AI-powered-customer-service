package com.empresa.atendimento.auth.api.dto;

public record LoginRequest(
    String username,
    String password
) {}
