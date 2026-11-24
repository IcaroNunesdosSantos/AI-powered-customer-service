package com.empresa.atendimento.customer.api.dto;

public record RegisterCustomerRequest(
    String email,
    String name
) {}
