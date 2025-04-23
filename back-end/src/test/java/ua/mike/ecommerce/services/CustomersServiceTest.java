package ua.mike.ecommerce.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.persistence.repository.CustomerRepository;
import ua.mike.ecommerce.web.exception.NotAcceptableException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.mike.ecommerce.services.CustomersService.ACCEPTABLE_ISSUER;
import static ua.mike.ecommerce.services.CustomersService.E_MAIL;
import static ua.mike.ecommerce.services.CustomersService.FIRST_NAME;
import static ua.mike.ecommerce.services.CustomersService.SECOND_NAME;

@ExtendWith(MockitoExtension.class)
class CustomersServiceTest {

    @InjectMocks CustomersService customersService;

    @Mock CustomerRepository customerRepository;

    private static final String CUSTOMER_TOKEN = "customer_token";
    private static final long CUSTOMER_ID = 7L;
    private static final String CUSTOMER_FIRST_NAME = "customer_first_name";
    private static final String CUSTOMER_LAST_NAME = "customer_last_name";
    private static final String CUSTOMER_EMAIL = "customer_email";

    @Mock DecodedJWT decodedJWT;

    @Test
    void authorizeCustomer_UserExists() {
        try (var jwt = mockStatic(JWT.class)) {
            jwt.when(() -> JWT.decode(CUSTOMER_TOKEN)).thenReturn(decodedJWT);
            when(decodedJWT.getIssuer()).thenReturn(ACCEPTABLE_ISSUER);

            var claim = mock(Claim.class);
            when(decodedJWT.getClaim(E_MAIL)).thenReturn(claim);
            when(claim.asString()).thenReturn(CUSTOMER_EMAIL);

            var customer = customer();
            when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.of(customer));

            assertThat(customersService.authorizeCustomer(CUSTOMER_TOKEN)).isEqualTo(customer);

            verify(customerRepository, never()).save(any());
        }
    }

    @Test
    void authorizeCustomer_UserNotExist_CreateNewOne() {
        try (var jwt = mockStatic(JWT.class)) {
            jwt.when(() -> JWT.decode(CUSTOMER_TOKEN)).thenReturn(decodedJWT);
            when(decodedJWT.getIssuer()).thenReturn(ACCEPTABLE_ISSUER);

            var emailClaim = mock(Claim.class);
            when(decodedJWT.getClaim(E_MAIL)).thenReturn(emailClaim);
            when(emailClaim.asString()).thenReturn(CUSTOMER_EMAIL);

            var firstNameClaim = mock(Claim.class);
            when(decodedJWT.getClaim(FIRST_NAME)).thenReturn(firstNameClaim);
            when(firstNameClaim.asString()).thenReturn(CUSTOMER_FIRST_NAME);

            var lastNameClaim = mock(Claim.class);
            when(decodedJWT.getClaim(SECOND_NAME)).thenReturn(lastNameClaim);
            when(lastNameClaim.asString()).thenReturn(CUSTOMER_LAST_NAME);

            when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.empty());
            var customer = customer();
            when(customerRepository.save(any(Customer.class))).thenReturn(customer);

            assertThat(customersService.authorizeCustomer(CUSTOMER_TOKEN)).isEqualTo(customer);

            verify(customerRepository).save(argThat(entity -> {
                assertThat(entity.getFirstName()).isEqualTo(CUSTOMER_FIRST_NAME);
                assertThat(entity.getLastName()).isEqualTo(CUSTOMER_LAST_NAME);
                assertThat(entity.getEmail()).isEqualTo(CUSTOMER_EMAIL);
                return true;
            }));
        }
    }

    @Test
    void authorizeCustomer_UnknownIssuer_NotAcceptable() {
        try (var jwt = mockStatic(JWT.class)) {
            jwt.when(() -> JWT.decode(CUSTOMER_TOKEN)).thenReturn(decodedJWT);
            when(decodedJWT.getIssuer()).thenReturn("unknown");
            assertThatThrownBy(() -> customersService.authorizeCustomer(CUSTOMER_TOKEN))
                    .isInstanceOf(NotAcceptableException.class)
                    .hasMessageContaining("Issuer not acceptable");
        }
    }

    @Test
    void authorizeCustomer_CorruptedToken_NotAcceptable() {
        try (var jwt = mockStatic(JWT.class)) {
            jwt.when(() -> JWT.decode(CUSTOMER_TOKEN)).thenThrow(new JWTVerificationException("JWTVerificationException"));
            assertThatThrownBy(() -> customersService.authorizeCustomer(CUSTOMER_TOKEN))
                    .isInstanceOf(NotAcceptableException.class)
                    .hasMessage("JWT token not acceptable");
        }
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