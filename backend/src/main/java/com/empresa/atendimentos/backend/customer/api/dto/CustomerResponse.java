package com.empresa.atendimento.customer.api.dto;

import com.empresa.atendimento.customer.domain.Customer;

import java.util.UUID;

public record CustomerResponse(
    UUID id,
    String email,
    String name,
    String phone
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getEmail(),
            customer.getName(),
            customer.getPhone()
        );
    }
}
