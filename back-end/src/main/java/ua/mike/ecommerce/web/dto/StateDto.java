package ua.mike.ecommerce.web.dto;

import lombok.Builder;

@Builder
public record StateDto(Long id, String name) { }
