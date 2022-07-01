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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SwitchDeviceRestAdapterDeleteTest {
    private static final String URL = "/api/switch-devices";

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void deleteSwitchDeviceTest() throws Exception {
        //given
        final var switchDeviceId = 1L;
        final var requestBuilder = MockMvcRequestBuilders
                .delete(URL + "/{id}", switchDeviceId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @WithMockUser(username = "user")
    void deleteSwitchDeviceNotFoundTest() throws Exception {
        //given
        final var switchDeviceId = Integer.MAX_VALUE;
        final var requestBuilder = MockMvcRequestBuilders
                .delete(URL + "/{id}", switchDeviceId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @WithMockUser(username = "superuser")
    void deleteSwitchDeviceUserIsNotOwnerTest() throws Exception {
        //given
        final var switchDeviceId = 1L;
        final var requestBuilder = MockMvcRequestBuilders
                .delete(URL + "/{id}", switchDeviceId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
