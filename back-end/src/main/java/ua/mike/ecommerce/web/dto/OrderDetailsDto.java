package ua.mike.ecommerce.web.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDetailsDto(Long id, String trackingNumber, BigDecimal totalPrice, int totalQuantity, LocalDateTime dateCreated, List<OrderItemDto> items) {

    public record OrderItemDto(String name, String imageUrl, int quantity, BigDecimal unitPrice) { }
}