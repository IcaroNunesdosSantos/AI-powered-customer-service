package com.empresa.atendimento.customer.application;

import com.empresa.atendimento.customer.domain.Customer;
import com.empresa.atendimento.customer.infra.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterCustomer {

    private final CustomerRepository customerRepository;

    public RegisterCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(String email, String name) {
        Customer customer = new Customer(email, name);
        return customerRepository.save(customer);
    }
}
