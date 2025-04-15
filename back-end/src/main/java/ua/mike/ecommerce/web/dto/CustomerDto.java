package ua.mike.ecommerce.web.dto;

import lombok.Builder;

@Builder
public record CustomerDto(Long id, String firstName, String lastName, String email) { }