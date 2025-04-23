package ua.mike.ecommerce.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.resolvers.TestCustomerTokenResolver;
import ua.mike.ecommerce.services.OrderService;
import ua.mike.ecommerce.web.dto.OrderDetailsDto;
import ua.mike.ecommerce.web.dto.OrderDto;
import ua.mike.ecommerce.web.dto.PurchaseRequest;
import ua.mike.ecommerce.web.dto.PurchaseResponse;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.mike.ecommerce.Constants.Header.X_CUSTOMER_TOKEN;
import static ua.mike.ecommerce.Constants.Version.V1;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks OrderController orderController;

    @Mock OrderService orderService;

    private static final String URL = V1 + "orders";

    private static final String CUSTOMER_TOKEN = "customer_token";
    private static final long CUSTOMER_ID = 2L;
    private static final long ORDER_ID = 9L;
    private static final String ORDER_TRACKING_NUMBER = "order_tracking_number";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_URL = "product_url";
    private static final int PRODUCT_QTY = 15;
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.TEN;

    private MockMvc mvc;

    private static final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(orderController)
                .setCustomArgumentResolvers(TestCustomerTokenResolver.builder()
                        .expectedToken(CUSTOMER_TOKEN)
                        .desiredCustomer(Customer.builder().id(CUSTOMER_ID).build())
                        .build())
                .build();
    }

    @Test
    @SneakyThrows
    void getOrdersHistory() {
        int page = 0;
        int size = 3;
        when(orderService.getOrders(CUSTOMER_ID, page, size)).thenReturn(orders(page, size));
        mvc.perform(get(URL + "?page={page}&size={size}", page, size)
                        .header(X_CUSTOMER_TOKEN, CUSTOMER_TOKEN)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(ORDER_ID))
                .andExpect(jsonPath("$.content[0].trackingNumber").value(ORDER_TRACKING_NUMBER));
    }

    @Test
    @SneakyThrows
    void getOrderDetails() {
        when(orderService.getOrderDetails(CUSTOMER_ID, ORDER_ID)).thenReturn(details());
        mvc.perform(get(URL + "/{orderId}", ORDER_ID)
                        .header(X_CUSTOMER_TOKEN, CUSTOMER_TOKEN)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(ORDER_ID))
                .andExpect(jsonPath("$.trackingNumber").value(ORDER_TRACKING_NUMBER))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.items[0].imageUrl").value(PRODUCT_URL))
                .andExpect(jsonPath("$.items[0].quantity").value(PRODUCT_QTY))
                .andExpect(jsonPath("$.items[0].unitPrice").value(PRODUCT_PRICE));
    }

    @Test
    @SneakyThrows
    void purchase() {
        var request = PurchaseRequest.builder().build();
        var trackingUUID = UUID.randomUUID().toString();
        var response = new PurchaseResponse(trackingUUID);
        when(orderService.placeOrder(any(Customer.class), eq(request))).thenReturn(response);

        mvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_CUSTOMER_TOKEN, CUSTOMER_TOKEN)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingUUID").value(trackingUUID));
    }

    private Page<OrderDto> orders(int page, int size) {
        var list = singletonList(order());
        return new PageImpl<>(list, PageRequest.of(page, size), list.size());
    }

    private OrderDto order() {
        return OrderDto.builder()
                .id(ORDER_ID)
                .trackingNumber(ORDER_TRACKING_NUMBER)
                .build();
    }

    private OrderDetailsDto details() {
        return OrderDetailsDto.builder()
                .id(ORDER_ID)
                .trackingNumber(ORDER_TRACKING_NUMBER)
                .items(singletonList(item()))
                .build();
    }

    private OrderDetailsDto.OrderItemDto item() {
        return OrderDetailsDto.OrderItemDto.builder()
                .name(PRODUCT_NAME)
                .imageUrl(PRODUCT_URL)
                .quantity(PRODUCT_QTY)
                .unitPrice(PRODUCT_PRICE)
                .build();
    }
}