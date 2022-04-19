package ua.mike.ecommerce.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.mike.ecommerce.dto.PaymentInfo;
import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;
import ua.mike.ecommerce.repos.AddressRepo;
import ua.mike.ecommerce.repos.CustomerRepo;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static ua.mike.ecommerce.services.PurchaseServiceImpl.StripeParams.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final CustomerRepo customerRepo;
    private final AddressRepo addressRepo;

    public PurchaseServiceImpl(CustomerRepo customerRepo,
                               AddressRepo addressRepo,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        final var order = purchase.getOrder();
        order.setTrackingNumber(UUID.randomUUID().toString());
        final var customer = customerRepo.byEmail(purchase.getCustomer().getEmail()).orElse(purchase.getCustomer());
        customer.getOrders().add(order);
        order.setCustomer(customer);
        order.setShippingAddress(
                addressRepo.search(purchase.getShippingAddress().getCountry().getId(),
                        purchase.getShippingAddress().getState().getId(),
                        purchase.getShippingAddress().getCity(),
                        purchase.getShippingAddress().getStreet(),
                        purchase.getShippingAddress().getZip())
                .orElse(purchase.getShippingAddress()));
        order.setBillingAddress(addressRepo.search(purchase.getBillingAddress().getCountry().getId(),
                        purchase.getBillingAddress().getState().getId(),
                        purchase.getBillingAddress().getCity(),
                        purchase.getBillingAddress().getStreet(),
                        purchase.getBillingAddress().getZip())
                .orElse(purchase.getBillingAddress().equals(purchase.getShippingAddress()) ?
                        purchase.getShippingAddress() : purchase.getBillingAddress()));
        purchase.getItems().forEach(item -> {
            item.setOrder(order);
            order.getItems().add(item);
        });
        this.customerRepo.save(customer);
        return new PurchaseResponse(order.getTrackingNumber());
    }

    @Override
    public PaymentIntent createStripePayment(PaymentInfo info) throws StripeException {
        return PaymentIntent.create(Map.of(
                amount.name(), info.getAmount(),
                currency.name(), info.getCurrency(),
                receipt_email.name(), info.getReceiptEmail(),
                payment_method_types.name(), Collections.singletonList("card")
        ));
    }

    enum StripeParams {
        amount, currency, payment_method_types, receipt_email
    }
}
