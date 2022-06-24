package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseCreate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class LocationRestAdapterCreateTest {
    private static final String URL = "/api/locations";
    @Mock
    LocationUseCaseCreate locationUseCaseCreate;
    LocationRestAdapterCreate locationRestAdapterCreate;
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        this.locationRestAdapterCreate = new LocationRestAdapterCreate(locationUseCaseCreate);
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationRestAdapterCreate)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void createLocationTest() throws Exception {
        //given
        final var locationToCreateDto = new LocationCreateCommandDto(
                1L,
                "Some new location"
        );
        final var requestBody = objectMapper.writeValueAsString(locationToCreateDto);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    void createLocationAccountNotFoundTest() throws Exception {
        //given
        final var locationToCreateDto = new LocationCreateCommandDto(
                1L,
                "Some new location"
        );
        final var requestBody = objectMapper.writeValueAsString(locationToCreateDto);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        doThrow(NotFoundException.class).when(locationUseCaseCreate).createLocation(any());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}