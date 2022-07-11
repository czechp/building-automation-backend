package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence.DeviceEventJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class DeviceEventAopAdapterFeedbackTest {
    private static final String URL = "/api/switch-devices/feedback";

    @Autowired
    DeviceEventJpaRepository deviceEventJpaRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void init() {
        this.deviceEventJpaRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user")
    void createFeedbackDeviceEventAdvice() throws Exception {
        //given
        final var switchDeviceId = 1L;
        final var switchDeviceFeedbackDto = new SwitchDeviceFeedbackDto(
                switchDeviceId,
                false
        );
        final var requestBody = objectMapper.writeValueAsString(switchDeviceFeedbackDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        //then
        final var listWithSingleEvent = deviceEventJpaRepository.findAll();
        assertThat(listWithSingleEvent, hasSize(1));
    }

    @Test
    @WithMockUser(username = "user")
    void createFailedFeedbackDeviceEventAdviceTest() throws Exception {
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
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        //then
        final var listWithSingleFailedEvent = deviceEventJpaRepository.findAll();
        assertThat(listWithSingleFailedEvent, hasSize(1));
    }
}
