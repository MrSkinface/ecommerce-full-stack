package ua.mike.ecommerce.web.dto;

import lombok.Builder;

@Builder
public record PurchaseOrderItemDto(Long productId, int quantity) { }