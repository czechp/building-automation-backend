package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
class DeviceEventRestAdapterDeleteTest {
    private static final String URL = "/api/device-events";
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDeviceEventTest() throws Exception {
        //given
        final var deviceEventIdToDelete = 1L;
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", deviceEventIdToDelete);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDeviceEventNOtFoundTest() throws Exception {
        //given
        final var deviceEventIdToDelete = Integer.MAX_VALUE;
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", deviceEventIdToDelete);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
