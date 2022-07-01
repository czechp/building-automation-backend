package app.web.pczportfolio.pczbuildingautomation.switchDevice.rest;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.persistence.SwitchDeviceJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SwitchDeviceRestAdapterQueryTest {
    private static final String URL = "/api/switch-devices";

    @Autowired
    SwitchDeviceJpaRepository switchDeviceJpaRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findAllSwitchDevicesTest() throws Exception {
        //given
        final var totalSwitchNumber = (int) switchDeviceJpaRepository.count();
        final var requestBuilder = MockMvcRequestBuilders.get(URL);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(totalSwitchNumber)));
    }

    @Test
    @WithMockUser(username = "user")
    void findAllSwitchDevicesByCurrentUserTest() throws Exception {
        //given
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/account");
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "user")
    void findSwitchDeviceByIdTest() throws Exception {
        //given
        final var accountId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", accountId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findSwitchDeviceByIdUserIsAdminTest() throws Exception {
        //given
        final var accountId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", accountId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "superuser")
    void findSwitchDeviceByIdUserNotOwnerTest() throws Exception {
        //given
        final var accountId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}", accountId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
