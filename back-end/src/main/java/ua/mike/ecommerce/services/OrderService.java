package ua.mike.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.mike.ecommerce.persistence.entity.Address;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.persistence.entity.Order;
import ua.mike.ecommerce.persistence.entity.OrderItem;
import ua.mike.ecommerce.persistence.repository.CountryRepository;
import ua.mike.ecommerce.persistence.repository.OrderRepository;
import ua.mike.ecommerce.persistence.repository.ProductRepository;
import ua.mike.ecommerce.persistence.repository.StateRepository;
import ua.mike.ecommerce.web.dto.OrderDetailsDto;
import ua.mike.ecommerce.web.dto.OrderDto;
import ua.mike.ecommerce.web.dto.PurchaseAddressDto;
import ua.mike.ecommerce.web.dto.PurchaseOrderItemDto;
import ua.mike.ecommerce.web.dto.PurchaseRequest;
import ua.mike.ecommerce.web.dto.PurchaseResponse;
import ua.mike.ecommerce.web.exception.NotFoundException;
import ua.mike.ecommerce.web.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;

    private final OrderMapper orderMapper;

    public Page<OrderDto> getOrders(long customerId, int page, int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        return orderRepository.findAllByCustomerId(customerId, pageable)
                .map(orderMapper::convert);
    }

    @Transactional
    public OrderDetailsDto getOrderDetails(Long customerId, Long orderId) {
        return orderRepository.findByCustomerIdAndId(customerId, orderId)
                .map(orderMapper::toDetails)
                .orElseThrow(() -> new NotFoundException(orderId));

    }

    @Transactional
    public PurchaseResponse placeOrder(Customer customer, PurchaseRequest purchase) {

        var items = getOrderItems(purchase.items());
        var order = orderRepository.save(Order.builder()
                .customer(customer)
                .shippingAddress(getShippingAddress(purchase.shippingAddress()))
                .items(items)
                .totalQuantity(getTotalQuantity(purchase.items()))
                .totalPrice(getTotalPrice(items))
                .build());

        return new PurchaseResponse(order.getTrackingNumber());
    }

    private Address getShippingAddress(PurchaseAddressDto address) {
        var country = countryRepository.findById(address.country().id())
                .orElseThrow(() -> new NotFoundException(address.country().id()));
        var state = stateRepository.findById(address.state().id())
                .orElseThrow(() -> new NotFoundException(address.country().id()));
        return Address.builder()
                .country(country)
                .state(state)
                .city(address.city())
                .street(address.street())
                .zip(address.zip())
                .build();
    }

    private List<OrderItem> getOrderItems(List<PurchaseOrderItemDto> items) {
        return items.stream().map(item -> {
                    var product = productRepository.findById(item.productId()).orElseThrow(() -> new NotFoundException(item.productId()));
                    return OrderItem.builder()
                            .product(product)
                            .quantity(item.quantity())
                            .build();
                })
                .map(OrderItem.class::cast)
                .toList();
    }

    private int getTotalQuantity(List<PurchaseOrderItemDto> items) {
        return items.stream()
                .map(PurchaseOrderItemDto::quantity)
                .reduce(0, Integer::sum);
    }

    private BigDecimal getTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}