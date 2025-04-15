package ua.mike.ecommerce.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.persistence.repository.CustomerRepository;
import ua.mike.ecommerce.web.exception.NotAcceptableException;

@Service
@RequiredArgsConstructor
public class CustomersService {

    private static final String ACCEPTABLE_ISSUER = "https://accounts.google.com";

    private static final String E_MAIL = "email";
    private static final String FIRST_NAME = "given_name";
    private static final String SECOND_NAME = "family_name";

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer authorizeCustomer(String authToken) {
        var decoded = this.decode(authToken);
        return customerRepository.findByEmail(decoded.getClaim(E_MAIL).asString())
                .orElseGet(() -> customerRepository.save(Customer.builder()
                        .email(decoded.getClaim(E_MAIL).asString())
                        .firstName(decoded.getClaim(FIRST_NAME).asString())
                        .lastName(decoded.getClaim(SECOND_NAME).asString())
                        .build()));
    }

    private DecodedJWT decode(String token) {
        try {
            var decoded = JWT.decode(token);
            if (!ACCEPTABLE_ISSUER.equals(decoded.getIssuer())) throw new NotAcceptableException("Issuer not acceptable");
            return decoded;
        } catch (JWTVerificationException e) {
            throw new NotAcceptableException("JWT token not acceptable");
        }
    }
}