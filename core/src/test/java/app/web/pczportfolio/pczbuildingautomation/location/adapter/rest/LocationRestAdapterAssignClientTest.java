package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.exception.TestCannotExecuteException;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationJpaRepository;
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
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class LocationRestAdapterAssignClientTest {
    private static final String URL = "/api/locations/client";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LocationJpaRepository locationJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Test
    @WithMockUser(username = "user")
    void assignClientToLocationTest() throws Exception {
        //given
        final var locationId = 1L;
        final var clientName = RandomStringGenerator.getRandomString();
        final var clientUUID = RandomStringGenerator.getRandomString();
        final var requestBuilder
                = MockMvcRequestBuilders.patch(URL + "/assign/{id}", locationId)
                .param("clientName", clientName)
                .param("clientUUID", clientUUID);

        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user")
    void assignClientToLocationNotFoundTest() throws Exception {
        //given
        final var locationId = Integer.MAX_VALUE;
        final var clientName = RandomStringGenerator.getRandomString();
        final var clientUUID = RandomStringGenerator.getRandomString();
        final var requestBuilder
                = MockMvcRequestBuilders.patch(URL + "/assign/{id}", locationId)
                .param("clientName", clientName)
                .param("clientUUID", clientUUID);

        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    void assignClientToLocationUIIDAlreadyExistsTest() throws Exception {
        //given
        final var clientName = RandomStringGenerator.getRandomString();
        final var clientUUID = RandomStringGenerator.getRandomString();
        final var locationId = prepareLocationWithClientUUID(clientUUID);
        final var requestBuilder
                = MockMvcRequestBuilders.patch(URL + "/assign/{id}", locationId)
                .param("clientName", clientName)
                .param("clientUIID", clientUUID);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "superuser")
    void assignClientToLocationUserIsNotOwner() throws Exception {
        //given
        final var clientName = RandomStringGenerator.getRandomString();
        final var clientUUID = RandomStringGenerator.getRandomString();
        final var locationId = 1L;
        final var requestBuilder
                = MockMvcRequestBuilders.patch(URL + "/assign/{id}", locationId)
                .param("clientName", clientName)
                .param("clientUUID", clientUUID);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Transactional
    public long prepareLocationWithClientUUID(String clientUUID) {
        AccountSimpleEntity accountSimpleEntity = accountJpaRepository.findById(3L)
                .map(a -> new AccountSimpleEntity(a.getId(), a.getUsername()))
                .orElseThrow(() -> new TestCannotExecuteException("There is no account with id: 3"));

        LocationEntity locationEntityWithClientUUID = new LocationEntity(
                0L,
                0L,
                null,
                "user",
                accountSimpleEntity,
                clientUUID,
                ""
        );

        LocationEntity savedLocation = locationJpaRepository.save(locationEntityWithClientUUID);
        return savedLocation.getId();
    }


    @Test
    @WithMockUser("user")
    void clearClientInLocationTest() throws Exception {
        //given
        final var locationId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/clear/{id}", locationId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser("user")
    void clearClientInLocationNotFoundTest() throws Exception {
        //given
        final var locationId = Integer.MAX_VALUE;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/clear/{id}", locationId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @WithMockUser("superuser")
    void clearClientInLocationIsNotOwnerTest() throws Exception {
        //given
        final var locationId = 1L;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/clear/{id}", locationId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
