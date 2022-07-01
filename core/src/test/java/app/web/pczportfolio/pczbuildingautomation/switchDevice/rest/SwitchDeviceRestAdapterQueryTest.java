package app.web.pczportfolio.pczbuildingautomation.switchDevice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SwitchDeviceRestAdapterQueryTest {
    private static final String URL = "/api/switch-devices";

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findAllSwitchDevicesTest() throws Exception {
        //given
        final var requestBuilder = MockMvcRequestBuilders.get(URL);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
