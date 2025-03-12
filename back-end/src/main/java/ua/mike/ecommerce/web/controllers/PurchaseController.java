package ua.mike.ecommerce.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.web.dto.Purchase;
import ua.mike.ecommerce.web.dto.PurchaseResponse;
import ua.mike.ecommerce.services.PurchaseService;

import static ua.mike.ecommerce.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/checkout")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService service;

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@Validated @RequestBody Purchase purchase) {
        return this.service.placeOrder(purchase);
    }
}
