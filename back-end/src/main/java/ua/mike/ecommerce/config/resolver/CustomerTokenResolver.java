package ua.mike.ecommerce.config.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ua.mike.ecommerce.Constants;
import ua.mike.ecommerce.services.CustomersService;
import ua.mike.ecommerce.web.exception.UnauthorizedException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerTokenResolver implements HandlerMethodArgumentResolver {

    private final CustomersService customersService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedCustomer.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return Optional.of((HttpServletRequest)webRequest.getNativeRequest())
                .map(request -> request.getHeader(Constants.Header.X_CUSTOMER_TOKEN))
                .map(customersService::authorizeCustomer)
                .orElseThrow(UnauthorizedException::new);
    }
}