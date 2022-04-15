package ua.mike.ecommerce.services;

import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;

public interface PurchaseService {

    PurchaseResponse placeOrder(Purchase purchase);
}
