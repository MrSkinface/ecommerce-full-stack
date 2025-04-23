package ua.mike.ecommerce.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mike.ecommerce.services.CountryService;
import ua.mike.ecommerce.web.dto.CountryDto;
import ua.mike.ecommerce.web.dto.StateDto;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.mike.ecommerce.Constants.Version.V1;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @InjectMocks CountryController countryController;

    @Mock CountryService countryService;

    private static final String URL = V1 + "countries";

    private static final long COUNTRY_ID = 3L;
    private static final String COUNTRY_CODE = "country_code";
    private static final String COUNTRY_NAME = "country_name";
    private static final long STATE_ID = 8L;
    private static final String STATE_NAME = "state_name";

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @SneakyThrows
    void getCountries() {
        when(countryService.getCountries()).thenReturn(singleton(country()));
        mvc.perform(get(URL)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(COUNTRY_ID))
                .andExpect(jsonPath("$[0].code").value(COUNTRY_CODE))
                .andExpect(jsonPath("$[0].name").value(COUNTRY_NAME));
    }

    @Test
    @SneakyThrows
    void getStates() {
        when(countryService.getCountryStates(COUNTRY_ID)).thenReturn(singleton(state()));
        mvc.perform(get(URL + "/{countryId}/states", COUNTRY_ID)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(STATE_ID))
                .andExpect(jsonPath("$[0].name").value(STATE_NAME));
    }

    private CountryDto country() {
        return CountryDto.builder()
                .id(COUNTRY_ID)
                .code(COUNTRY_CODE)
                .name(COUNTRY_NAME)
                .build();
    }

    private StateDto state() {
        return StateDto.builder()
                .id(STATE_ID)
                .name(STATE_NAME)
                .build();
    }
}