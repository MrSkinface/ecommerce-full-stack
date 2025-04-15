package ua.mike.ecommerce.web.dto;

import lombok.Builder;

@Builder
public record PurchaseAddressDto(CountryDto country, StateDto state, String city, String street, String zip) { }