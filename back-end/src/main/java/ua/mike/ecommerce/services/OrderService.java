package ua.mike.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.mike.ecommerce.persistence.repository.OrderRepository;
import ua.mike.ecommerce.web.dto.OrderDto;
import ua.mike.ecommerce.web.mapper.OrderMapper;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Page<OrderDto> getOrders(long customerId, int page, int size) {
        final var pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        return orderRepository.findAllByCustomerId(customerId, pageable)
                .map(orderMapper::convert);
    }
}