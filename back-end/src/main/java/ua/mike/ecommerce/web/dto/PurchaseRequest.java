package ua.mike.ecommerce.web.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseRequest(PurchaseAddressDto shippingAddress, List<PurchaseOrderItemDto> items) { }