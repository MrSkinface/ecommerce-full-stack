package ua.mike.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.persistence.entity.Country;
import ua.mike.ecommerce.persistence.repository.CountryRepository;

import java.util.List;

/**
 * Created by mike on 12.04.2024 13:14
 */
@RestController
@RequestMapping("api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;

    @GetMapping
    public List<String> getAllCountries() {
        // todo just for test
        return countryRepository.findAll()
                .stream()
                .map(Country::getName)
                .toList();
    }
}
