package ua.mike.ecommerce.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.services.CountryService;
import ua.mike.ecommerce.web.dto.CountryDto;
import ua.mike.ecommerce.web.dto.StateDto;

import java.util.Set;

import static ua.mike.ecommerce.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public Set<CountryDto> getCountries() {
        return countryService.getCountries();
    }

    @GetMapping("{id}/states")
    public Set<StateDto> getStates(@PathVariable(name = "id") Long countryId) {
        return countryService.getCountryStates(countryId);
    }
}
