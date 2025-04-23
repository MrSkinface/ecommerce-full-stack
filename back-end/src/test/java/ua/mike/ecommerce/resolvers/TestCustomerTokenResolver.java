package ua.mike.ecommerce.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ua.mike.ecommerce.Constants;
import ua.mike.ecommerce.config.resolver.AuthenticatedCustomer;
import ua.mike.ecommerce.persistence.entity.Customer;

import java.util.Optional;

@Builder
public class TestCustomerTokenResolver implements HandlerMethodArgumentResolver {

    private String expectedToken;
    private Customer desiredCustomer;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedCustomer.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return Optional.of((HttpServletRequest)webRequest.getNativeRequest())
                .map(request -> request.getHeader(Constants.Header.X_CUSTOMER_TOKEN))
                .filter(token -> token.equals(expectedToken))
                .map(token -> desiredCustomer)
                .orElseThrow(() -> new RuntimeException("Unexpected customer token"));
    }
}