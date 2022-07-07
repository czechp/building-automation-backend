package app.web.pczportfolio.pczbuildingautomation.switchDevice.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.exception.TestCannotExecuteException;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.location.dto.LocationSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence.SwitchDeviceEntity;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence.SwitchDeviceJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
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

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SwitchDeviceRestAdapterGetFeedbackTest {
    private static final String URL = "/api/switch-devices/feedback";

    @Autowired
    SwitchDeviceJpaRepository switchDeviceJpaRepository;

    @Autowired
    LocationJpaRepository locationJpaRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void receiveFeedbackFromSwitchDeviceTest() throws Exception {
        //given
        final var switchDeviceNewState = true;

        LocationEntity locationEntity = locationJpaRepository.findById(1L)
                .orElseThrow(() -> new TestCannotExecuteException("Location  with id: 1 does not exist"));
        SwitchDeviceEntity switchDeviceEntity = switchDeviceJpaRepository.save(
                SwitchDeviceEntity.builder()
                        .withLocationSimpleEntity(new LocationSimpleEntity(locationEntity.getId(), locationEntity.getName()))
                        .withName(RandomStringGenerator.getRandomString())
                        .withOwner(locationEntity.getAccountSimpleEntity().getUsername())
                        .withCreationTimestamp(LocalDateTime.now())
                        .withLastFeedbackTimeStamp(LocalDateTime.now())
                        .withLastSetCommandTimestamp(LocalDateTime.now())
                        .withExpectedState(switchDeviceNewState)
                        .build()
        );

        final var switchDeviceFeedbackDto = new SwitchDeviceFeedbackDto(
                switchDeviceEntity.getId(),
                switchDeviceNewState
        );

        final var requestBody = objectMapper.writeValueAsString(switchDeviceFeedbackDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "superuser")
    void receiveFeedbackFromSwitchDeviceUserAreNotOwnerTest() throws Exception {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceNewState = true;
        final var switchDeviceFeedbackDto = new SwitchDeviceFeedbackDto(
                switchDeviceId,
                switchDeviceNewState
        );
        final var requestBody = objectMapper.writeValueAsString(switchDeviceFeedbackDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user")
    void receiveFeedbackFromSwitchDeviceNotFoundTest() throws Exception {
        //given
        final var switchDeviceId = Integer.MAX_VALUE;
        final var switchDeviceNewState = true;
        final var switchDeviceFeedbackDto = new SwitchDeviceFeedbackDto(
                switchDeviceId,
                switchDeviceNewState
        );
        final var requestBody = objectMapper.writeValueAsString(switchDeviceFeedbackDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
