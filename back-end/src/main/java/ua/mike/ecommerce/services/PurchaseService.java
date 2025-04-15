package ua.mike.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mike.ecommerce.web.dto.Purchase;
import ua.mike.ecommerce.web.dto.PurchaseResponse;
import ua.mike.ecommerce.persistence.repository.AddressRepository;
import ua.mike.ecommerce.persistence.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final CustomerRepository customerRepo;
    private final AddressRepository addressRepo;

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        final var order = purchase.getOrder();
        order.setTrackingNumber(UUID.randomUUID().toString());
        final var customer = customerRepo.findByEmail(purchase.getCustomer().getEmail()).orElse(purchase.getCustomer());
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
}
