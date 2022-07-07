package app.web.pczportfolio.pczbuildingautomation.switchDevice.rest;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@ActiveProfiles({"test"})
@Transactional
class SwitchDeviceRestAdapterSetNewStateTest {
    private static final String URL = "/api/switch-devices/new-state";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void setNewStateForSwitchDeviceTest() throws Exception {
        //given
        final var  requestBodyDto = new SwitchDeviceSetNewStateDto(1L, true);
        final var requestBody = objectMapper.writeValueAsString(requestBodyDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @WithMockUser(username = "user")
    void setNewStateForSwitchDeviceNotFoundTest() throws Exception {
        //given
        final var  requestBodyDto = new SwitchDeviceSetNewStateDto(Integer.MAX_VALUE, true);
        final var requestBody = objectMapper.writeValueAsString(requestBodyDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @WithMockUser(username = "superuser")
    void setNewStateForSwitchDeviceUserNotOwnerTest() throws Exception {
        //given
        final var  requestBodyDto = new SwitchDeviceSetNewStateDto(1L, true);
        final var requestBody = objectMapper.writeValueAsString(requestBodyDto);
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }
}
