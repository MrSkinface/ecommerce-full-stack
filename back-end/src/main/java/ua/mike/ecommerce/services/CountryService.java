package ua.mike.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mike.ecommerce.persistence.repository.CountryRepository;
import ua.mike.ecommerce.persistence.repository.StateRepository;
import ua.mike.ecommerce.web.dto.CountryDto;
import ua.mike.ecommerce.web.dto.StateDto;
import ua.mike.ecommerce.web.mapper.CountryMapper;
import ua.mike.ecommerce.web.mapper.StateMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;

    private final CountryMapper countryMapper;
    private final StateMapper stateMapper;

    public Set<CountryDto> getCountries() {
        return countryRepository.findAll()
                .stream().map(countryMapper::convert)
                .collect(Collectors.toSet());
    }

    public Set<StateDto> getCountryStates(Long countryId) {
        return stateRepository.findAllByCountryId(countryId)
                .stream().map(stateMapper::convert)
                .collect(Collectors.toSet());
    }
}
