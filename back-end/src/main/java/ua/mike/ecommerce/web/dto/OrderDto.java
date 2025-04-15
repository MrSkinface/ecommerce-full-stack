package ua.mike.ecommerce.web.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderDto(Long id, String trackingNumber, BigDecimal totalPrice, int totalQuantity, LocalDateTime dateCreated) { }