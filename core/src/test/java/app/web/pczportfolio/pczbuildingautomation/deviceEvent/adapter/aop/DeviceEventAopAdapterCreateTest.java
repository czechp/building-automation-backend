package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence.DeviceEventJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.exception.TestCannotExecuteException;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@Transactional
class DeviceEventAopAdapterCreateTest {
    private static final String URL = "/api/switch-devices";

    @Autowired
    DeviceEventJpaRepository deviceEventJpaRepository;

    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    LocationJpaRepository locationJpaRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        deviceEventJpaRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void createDeviceEventAdviceTest() throws Exception {
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
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
        //then
        final var eventsList = deviceEventJpaRepository.findAll();
        assertThat(eventsList, hasSize(1));
    }

    @Test
    @WithMockUser
    void createFailedDeviceEventAdviceTest() throws Exception {
        final var switchDeviceToCreate = new SwitchDeviceCreateDto(
                Integer.MAX_VALUE,
                RandomStringGenerator.getRandomString()
        );

        final var requestBody = objectMapper.writeValueAsString(switchDeviceToCreate);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //then
        final var eventFailedList = deviceEventJpaRepository.findAll();
        assertThat(eventFailedList, hasSize(1));
    }
}
