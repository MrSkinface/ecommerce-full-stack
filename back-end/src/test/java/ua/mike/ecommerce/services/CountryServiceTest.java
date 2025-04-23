package ua.mike.ecommerce.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mike.ecommerce.persistence.entity.Country;
import ua.mike.ecommerce.persistence.entity.State;
import ua.mike.ecommerce.persistence.repository.CountryRepository;
import ua.mike.ecommerce.persistence.repository.StateRepository;
import ua.mike.ecommerce.web.mapper.CountryMapper;
import ua.mike.ecommerce.web.mapper.StateMapper;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @InjectMocks CountryService countryService;

    @Mock CountryRepository countryRepository;
    @Mock StateRepository stateRepository;

    @Spy CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    @Spy StateMapper stateMapper = Mappers.getMapper(StateMapper.class);

    private static final long COUNTRY_ID = 3L;
    private static final String COUNTRY_CODE = "country_code";
    private static final String COUNTRY_NAME = "country_name";
    private static final long STATE_ID = 8L;
    private static final String STATE_NAME = "state_name";

    @Test
    void getCountries() {
        var country = country();
        when(countryRepository.findAll()).thenReturn(singletonList(country));

        var result = countryService.getCountries();

        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().get().id()).isEqualTo(COUNTRY_ID);
        assertThat(result.stream().findFirst().get().name()).isEqualTo(COUNTRY_NAME);
        assertThat(result.stream().findFirst().get().code()).isEqualTo(COUNTRY_CODE);

        verify(countryMapper).convert(country);
    }

    @Test
    void getCountryStates() {
        var state = state();
        when(stateRepository.findAllByCountryId(COUNTRY_ID)).thenReturn(singleton(state));

        var result = countryService.getCountryStates(COUNTRY_ID);

        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().get().id()).isEqualTo(STATE_ID);
        assertThat(result.stream().findFirst().get().name()).isEqualTo(STATE_NAME);

        verify(stateMapper).convert(state);
    }

    private Country country() {
        return Country.builder()
                .id(COUNTRY_ID)
                .code(COUNTRY_CODE)
                .name(COUNTRY_NAME)
                .build();
    }

    private State state() {
        return State.builder()
                .id(STATE_ID)
                .name(STATE_NAME)
                .build();
    }
}