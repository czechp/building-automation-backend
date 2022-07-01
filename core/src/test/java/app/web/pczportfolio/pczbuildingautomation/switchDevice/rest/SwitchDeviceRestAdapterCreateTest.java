package app.web.pczportfolio.pczbuildingautomation.switchDevice.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.exception.TestCannotExecuteException;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SwitchDeviceRestAdapterCreateTest {
    private static final String URL = "/api/switch-devices";
    @Autowired
    LocationJpaRepository locationJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void createSwitchDeviceTest() throws Exception {
        //given
        final var user = accountJpaRepository.findByUsername("user")
                .orElseThrow(() -> new TestCannotExecuteException("Account with username: user does not exist"));
        final var locationToFetch = locationJpaRepository.save(new LocationEntity(0L,
                        0L,
                        null,
                        RandomStringGenerator.getRandomString(),
                        new AccountSimpleEntity(user.getId(), user.getUsername())
                )
        );

        final var switchDeviceToCreate = new SwitchDeviceCreateDto(
                locationToFetch.getId(),
                RandomStringGenerator.getRandomString()
        );

        final var requestBody = objectMapper.writeValueAsString(switchDeviceToCreate);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser
    void createSwitchDeviceLocationNotFoundTest() throws Exception {
        //given
        final var switchDeviceToCreate = new SwitchDeviceCreateDto(
                Integer.MAX_VALUE,
                RandomStringGenerator.getRandomString()
        );

        final var requestBody = objectMapper.writeValueAsString(switchDeviceToCreate);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "someGuy")
    void createSwitchDeviceLocationNotOwnToUserTest() throws Exception {
        //given
        final var user = accountJpaRepository.findByUsername("user")
                .orElseThrow(() -> new TestCannotExecuteException("Account with username: user does not exist"));
        final var locationToFetch = locationJpaRepository.save(new LocationEntity(0L,
                        0L,
                        null,
                        RandomStringGenerator.getRandomString(),
                        new AccountSimpleEntity(user.getId(), user.getUsername())
                )
        );

        final var switchDeviceToCreate = new SwitchDeviceCreateDto(
                locationToFetch.getId(),
                RandomStringGenerator.getRandomString()
        );

        final var requestBody = objectMapper.writeValueAsString(switchDeviceToCreate);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
