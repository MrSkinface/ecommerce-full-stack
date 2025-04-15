package ua.mike.ecommerce.web.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.config.resolver.AuthenticatedCustomer;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.web.dto.CustomerDto;
import ua.mike.ecommerce.web.mapper.CustomerMapper;

import static ua.mike.ecommerce.Constants.Header.X_CUSTOMER_TOKEN;
import static ua.mike.ecommerce.Constants.Version.V1;

@RestController
@RequestMapping(V1 + "customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomerMapper customerMapper;

    @PostMapping("authorize")
    @Parameter(name =X_CUSTOMER_TOKEN, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class), required = true)
    public CustomerDto authorizeCustomer(@Parameter(hidden = true) @AuthenticatedCustomer Customer customer) {
        return customerMapper.convert(customer);
    }
}