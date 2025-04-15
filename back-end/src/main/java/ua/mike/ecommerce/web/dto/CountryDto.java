package ua.mike.ecommerce.web.dto;

import lombok.Builder;

@Builder
public record CountryDto(Long id, String code, String name) { }
