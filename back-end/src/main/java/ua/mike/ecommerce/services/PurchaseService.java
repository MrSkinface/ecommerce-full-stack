package ua.mike.ecommerce.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import ua.mike.ecommerce.dto.PaymentInfo;
import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;

public interface PurchaseService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createStripePayment(PaymentInfo info) throws StripeException;
}
