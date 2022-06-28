package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.exception.TestCannotExecuteException;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountByUsername;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class LocationRestAdapterDeleteTest {
    private static final String URL = "/api/locations";
    @Autowired
    LocationJpaRepository locationJpaRepository;

    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    LocationPortFindAccountByUsername locationPortFindAccountByUsername;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void deleteLocationTest() throws Exception {
        //given
        final var locationToDelete = locationJpaRepository.save(
                LocationEntity.builder()
                        .withName(RandomStringGenerator.getRandomString())
                        .withAccountSimpleEntity(provideAccountSimpleEntity("user"))
                        .build()
        );
        final var locationIdRequest = locationToDelete.getId();
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", locationIdRequest);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteLocationNotFoundTest() throws Exception {
        //given
        final var locationIdRequest = Integer.MAX_VALUE;
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", locationIdRequest);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteLocationByAdminTest() throws Exception {
        //given
        final var locationToDelete = locationJpaRepository.save(
                LocationEntity.builder()
                        .withName(RandomStringGenerator.getRandomString())
                        .withAccountSimpleEntity(provideAccountSimpleEntity("user"))
                        .build()
        );
        final var locationIdRequest = locationToDelete.getId();
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", locationIdRequest);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    private AccountSimpleEntity provideAccountSimpleEntity(String username) {
        return locationPortFindAccountByUsername.findAccountByUsername(username)
                .map((a) -> new AccountSimpleEntity(a.getId(), a.getUsername()))
                .orElseThrow(() -> new TestCannotExecuteException("Test cannot execute! There is no account with: " + username + " username"));
    }

}
