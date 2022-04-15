package ua.mike.ecommerce.controllers;

import org.springframework.web.bind.annotation.*;
import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;
import ua.mike.ecommerce.services.PurchaseService;

@CrossOrigin
@RestController
@RequestMapping("/api/checkout")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody Purchase purchase) {
        return this.purchaseService.placeOrder(purchase);
    }
}
