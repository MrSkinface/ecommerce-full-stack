package ua.mike.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import ua.mike.ecommerce.models.Address;
import ua.mike.ecommerce.models.Customer;
import ua.mike.ecommerce.models.Order;
import ua.mike.ecommerce.models.OrderItem;

import java.util.Set;

@Data
@Builder
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> items;

}
