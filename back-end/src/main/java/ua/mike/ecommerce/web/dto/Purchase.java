package ua.mike.ecommerce.web.dto;

import lombok.Builder;
import lombok.Data;
import ua.mike.ecommerce.persistence.entity.Address;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.persistence.entity.Order;
import ua.mike.ecommerce.persistence.entity.OrderItem;

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
