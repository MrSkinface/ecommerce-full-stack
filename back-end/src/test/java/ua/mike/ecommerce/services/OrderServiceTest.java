package ua.mike.ecommerce.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.mike.ecommerce.persistence.entity.Country;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.persistence.entity.Order;
import ua.mike.ecommerce.persistence.entity.Product;
import ua.mike.ecommerce.persistence.entity.State;
import ua.mike.ecommerce.persistence.repository.CountryRepository;
import ua.mike.ecommerce.persistence.repository.OrderRepository;
import ua.mike.ecommerce.persistence.repository.ProductRepository;
import ua.mike.ecommerce.persistence.repository.StateRepository;
import ua.mike.ecommerce.web.dto.CountryDto;
import ua.mike.ecommerce.web.dto.PurchaseAddressDto;
import ua.mike.ecommerce.web.dto.PurchaseOrderItemDto;
import ua.mike.ecommerce.web.dto.PurchaseRequest;
import ua.mike.ecommerce.web.dto.StateDto;
import ua.mike.ecommerce.web.exception.NotFoundException;
import ua.mike.ecommerce.web.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks OrderService orderService;

    @Mock OrderRepository orderRepository;
    @Mock ProductRepository productRepository;
    @Mock CountryRepository countryRepository;
    @Mock StateRepository stateRepository;

    @Spy OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    private static final long CUSTOMER_ID = 3L;
    private static final long ORDER_ID = 9L;
    private static final String ORDER_TRACKING_NUMBER = "order_tracking_number";
    private static final long PRODUCT_ID = 6L;
    private static final long COUNTRY_ID = 16L;
    private static final long STATE_ID = 26L;

    @Test
    void getOrders() {
        int page = 1;
        int size = 3;
        when(orderRepository.findAllByCustomerId(eq(CUSTOMER_ID), any(Pageable.class))).thenReturn(orders(page, size));

        var result = orderService.getOrders(CUSTOMER_ID, page, size);

        assertThat(result).hasSize(1);
        assertThat(result.getContent().getFirst().id()).isEqualTo(ORDER_ID);
        assertThat(result.getContent().getFirst().trackingNumber()).isEqualTo(ORDER_TRACKING_NUMBER);

        verify(orderMapper).convert(any(Order.class));
    }

    @Test
    void getOrderDetails() {
        when(orderRepository.findByCustomerIdAndId(CUSTOMER_ID, ORDER_ID)).thenReturn(Optional.of(order()));

        var result = orderService.getOrderDetails(CUSTOMER_ID, ORDER_ID);

        assertThat(result.id()).isEqualTo(ORDER_ID);
        assertThat(result.trackingNumber()).isEqualTo(ORDER_TRACKING_NUMBER);

        verify(orderMapper).toDetails(any(Order.class));
    }

    @Test
    void getOrderDetails_NotFound() {
        when(orderRepository.findByCustomerIdAndId(CUSTOMER_ID, ORDER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderDetails(CUSTOMER_ID, ORDER_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Resource with id [")
                .hasMessageContaining("] not found");
    }

    @Test
    void placeOrder() {

        var request = purchaseRequest();

        var customer = new Customer();
        var product = Product.builder().unitPrice(BigDecimal.TEN).build();
        var country = new Country();
        var state = new State();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(country));
        when(stateRepository.findById(STATE_ID)).thenReturn(Optional.of(state));
        doAnswer(e -> {
            Order entity = e.getArgument(0);
            entity.setTrackingNumber(ORDER_TRACKING_NUMBER);
            return entity;
        }).when(orderRepository).save(any(Order.class));

        var result = orderService.placeOrder(customer, request);

        assertThat(result.trackingUUID()).isEqualTo(ORDER_TRACKING_NUMBER);

        verify(orderRepository).save(argThat(order -> {
            assertThat(order.getCustomer()).isEqualTo(customer);
            assertThat(order.getShippingAddress().getCountry()).isEqualTo(country);
            assertThat(order.getShippingAddress().getState()).isEqualTo(state);
            assertThat(order.getItems()).hasSize(1);
            return true;
        }));
    }

    @Test
    void placeOrder_ProductNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.placeOrder(new Customer(), purchaseRequest()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Resource with id [")
                .hasMessageContaining(String.valueOf(PRODUCT_ID))
                .hasMessageContaining("] not found");
    }

    @Test
    void placeOrder_CountryNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(new Product()));
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.placeOrder(new Customer(), purchaseRequest()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Resource with id [")
                .hasMessageContaining(String.valueOf(COUNTRY_ID))
                .hasMessageContaining("] not found");
    }

    @Test
    void placeOrder_StateNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(new Product()));
        when(countryRepository.findById(COUNTRY_ID)).thenReturn(Optional.of(new Country()));
        when(stateRepository.findById(STATE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.placeOrder(new Customer(), purchaseRequest()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Resource with id [")
                .hasMessageContaining(String.valueOf(STATE_ID))
                .hasMessageContaining("] not found");
    }

    private Page<Order> orders(int page, int size) {
        var list = singletonList(order());
        return new PageImpl<>(list, PageRequest.of(page, size), list.size());
    }

    private Order order() {
        return Order.builder()
                .id(ORDER_ID)
                .trackingNumber(ORDER_TRACKING_NUMBER)
                .build();
    }

    private PurchaseRequest purchaseRequest() {
        var item = PurchaseOrderItemDto.builder().productId(PRODUCT_ID).build();
        var shippingAddress = PurchaseAddressDto.builder()
                .country(CountryDto.builder().id(COUNTRY_ID).build())
                .state(StateDto.builder().id(STATE_ID).build())
                .build();
        return new PurchaseRequest(shippingAddress, singletonList(item));
    }
}