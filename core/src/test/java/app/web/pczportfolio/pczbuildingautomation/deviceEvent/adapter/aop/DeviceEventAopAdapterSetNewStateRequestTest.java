package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence.DeviceEventJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
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
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
class DeviceEventAopAdapterSetNewStateRequestTest {
    private static final String URL = "/api/switch-devices/new-state";

    @Autowired
    DeviceEventJpaRepository deviceEventJpaRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        deviceEventJpaRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user")
    void setNewStateRequestEventAdviceTest() throws Exception {
        //given
        final var requestBodyDto = new SwitchDeviceSetNewStateDto(1L, true);
        final var requestBody = objectMapper.writeValueAsString(requestBodyDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        //then
        final var listWithSingleEvent = deviceEventJpaRepository.findAll();
        assertThat(listWithSingleEvent, hasSize(1));
    }


    @Test
    @WithMockUser(username = "user")
    void setNewStateRequestFailedEventAdviceTest() throws Exception {
        //given
        final var requestBodyDto = new SwitchDeviceSetNewStateDto(Integer.MAX_VALUE, true);
        final var requestBody = objectMapper.writeValueAsString(requestBodyDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when\
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        //then
        final var listWithSingleEvent = deviceEventJpaRepository.findAll();
        assertThat(listWithSingleEvent, hasSize(1));
    }
}
