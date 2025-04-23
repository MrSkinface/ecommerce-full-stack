package ua.mike.ecommerce.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.resolvers.TestCustomerTokenResolver;
import ua.mike.ecommerce.web.mapper.CustomerMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.mike.ecommerce.Constants.Header.X_CUSTOMER_TOKEN;
import static ua.mike.ecommerce.Constants.Version.V1;

@ExtendWith(MockitoExtension.class)
class CustomersControllerTest {

    @InjectMocks CustomersController controller;

    @Spy CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    private static final String URL = V1 + "customers";

    private static final String CUSTOMER_TOKEN = "customer_token";

    private static final long CUSTOMER_ID = 7L;
    private static final String CUSTOMER_FIRST_NAME = "customer_first_name";
    private static final String CUSTOMER_LAST_NAME = "customer_last_name";
    private static final String CUSTOMER_EMAIL = "customer_email";

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(TestCustomerTokenResolver.builder()
                        .expectedToken(CUSTOMER_TOKEN)
                        .desiredCustomer(customer())
                        .build())
                .build();
    }

    @Test
    @SneakyThrows
    void authorizeCustomer() {
        mvc.perform(post(URL + "/authorize").header(X_CUSTOMER_TOKEN, CUSTOMER_TOKEN))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(CUSTOMER_ID))
                .andExpect(jsonPath("$.firstName").value(CUSTOMER_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(CUSTOMER_LAST_NAME))
                .andExpect(jsonPath("$.email").value(CUSTOMER_EMAIL));
    }

    private Customer customer() {
        return Customer.builder()
                .id(CUSTOMER_ID)
                .firstName(CUSTOMER_FIRST_NAME)
                .lastName(CUSTOMER_LAST_NAME)
                .email(CUSTOMER_EMAIL)
                .build();
    }
}