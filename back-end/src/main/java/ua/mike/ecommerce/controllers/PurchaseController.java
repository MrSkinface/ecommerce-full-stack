package ua.mike.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;
import ua.mike.ecommerce.services.PurchaseService;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService service;

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@Validated @RequestBody Purchase purchase) {
        return this.service.placeOrder(purchase);
    }
}
