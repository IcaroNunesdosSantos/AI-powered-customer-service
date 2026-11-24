package com.empresa.atendimento.customer.api;

import com.empresa.atendimento.customer.api.dto.CustomerResponse;
import com.empresa.atendimento.customer.api.dto.RegisterCustomerRequest;
import com.empresa.atendimento.customer.application.RegisterCustomer;
import com.empresa.atendimento.customer.domain.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final RegisterCustomer registerCustomer;

    public CustomerController(RegisterCustomer registerCustomer) {
        this.registerCustomer = registerCustomer;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> register(@RequestBody RegisterCustomerRequest request) {
        Customer customer = registerCustomer.execute(request.email(), request.name());
        return ResponseEntity.ok(CustomerResponse.from(customer));
    }
}
