package ua.mike.ecommerce.web.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.config.resolver.AuthenticatedCustomer;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.services.OrderService;
import ua.mike.ecommerce.web.dto.OrderDetailsDto;
import ua.mike.ecommerce.web.dto.OrderDto;
import ua.mike.ecommerce.web.dto.PurchaseRequest;
import ua.mike.ecommerce.web.dto.PurchaseResponse;

import static ua.mike.ecommerce.Constants.Version.V1;

@RestController
@RequestMapping(V1 + "orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Page<OrderDto> getOrdersHistory(@Parameter(hidden = true) @AuthenticatedCustomer Customer customer,
                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        return orderService.getOrders(customer.getId(), page, size);
    }

    @GetMapping("{orderId}")
    public OrderDetailsDto getOrderDetails(@Parameter(hidden = true) @AuthenticatedCustomer Customer customer,
                                           @PathVariable Long orderId) {
        return orderService.getOrderDetails(customer.getId(), orderId);
    }

    @PostMapping
    public PurchaseResponse purchase(@AuthenticatedCustomer Customer customer, @Validated @RequestBody PurchaseRequest purchase) {
        return orderService.placeOrder(customer, purchase);
    }
}