package ua.mike.ecommerce.services;

import org.springframework.stereotype.Service;
import ua.mike.ecommerce.dto.Purchase;
import ua.mike.ecommerce.dto.PurchaseResponse;
import ua.mike.ecommerce.repos.AddressRepo;
import ua.mike.ecommerce.repos.CustomerRepo;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final CustomerRepo customerRepo;
    private final AddressRepo addressRepo;

    public PurchaseServiceImpl(CustomerRepo customerRepo, AddressRepo addressRepo) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
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
}
