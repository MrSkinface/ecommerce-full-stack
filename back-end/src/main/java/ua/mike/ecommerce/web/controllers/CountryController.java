package ua.mike.ecommerce.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.persistence.repository.CountryRepository;
import ua.mike.ecommerce.persistence.repository.StateRepository;
import ua.mike.ecommerce.web.dto.CountryDto;
import ua.mike.ecommerce.web.dto.StateDto;
import ua.mike.ecommerce.web.mapper.CountryMapper;
import ua.mike.ecommerce.web.mapper.StateMapper;

import java.util.Set;
import java.util.stream.Collectors;

import static ua.mike.ecommerce.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;

    private final CountryMapper countryMapper;
    private final StateMapper stateMapper;

    @GetMapping
    public Set<CountryDto> getCountries() {
        return countryRepository.findAll()
                .stream().map(countryMapper::convert)
                .collect(Collectors.toSet());
    }

    @GetMapping("{id}/states")
    public Set<StateDto> getStates(@PathVariable(name = "id") Long countryId) {
        return stateRepository.findAllByCountryId(countryId)
                .stream().map(stateMapper::convert)
                .collect(Collectors.toSet());
    }
}
