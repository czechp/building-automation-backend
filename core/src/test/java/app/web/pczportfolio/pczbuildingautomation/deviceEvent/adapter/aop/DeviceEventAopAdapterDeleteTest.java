package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.persistence.DeviceEventJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@ActiveProfiles({"test"})
@Transactional
@AutoConfigureMockMvc
class DeviceEventAopAdapterDeleteTest {
    private static final String URL_SWITCH_DEVICES = "/api/switch-devices";
    private static final String URL_LOCATIONS = "/api/locations";

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
    void deleteDeviceEventAdviceSingleTest() throws Exception {
        //given
        final var switchDeviceId = 1L;
        final var requestBuilder = MockMvcRequestBuilders
                .delete(URL_SWITCH_DEVICES + "/{id}", switchDeviceId);
        //when
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        //then
        final var listWithSingleEvent = deviceEventJpaRepository.findAll();
        assertThat(listWithSingleEvent, hasSize(1));
    }

    @Test
    @WithMockUser(username = "user")
    void deleteDeviceEventAdviceManyTest() throws Exception {
        //given
        final var locationId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.delete(URL_LOCATIONS + "/{id}", locationId);
        //when
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        //then
        final var listOfThreeDeviceEvent = deviceEventJpaRepository.findAll();
        assertThat(listOfThreeDeviceEvent, hasSize(3));
    }
}
