package ua.mike.ecommerce.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Resource with id [%s] not found";

    public NotFoundException(Long resourceId) {
        super(DEFAULT_MESSAGE.formatted(resourceId));
    }
}
