package ua.mike.ecommerce.controllers;

import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mike.ecommerce.dto.PaymentInfo;
import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;
import ua.mike.ecommerce.services.PurchaseService;

@RestController
@RequestMapping("/api/checkout")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody Purchase purchase) {
        return this.service.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> paymentIntent(@RequestBody PaymentInfo info) throws StripeException {
        return new ResponseEntity<>(service.createStripePayment(info).toJson(), HttpStatus.OK);
    }
}
