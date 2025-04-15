package ua.mike.ecommerce.web.dto;

import lombok.Builder;

@Builder
public record ProductCategoryDto(Long id, String name) { }
