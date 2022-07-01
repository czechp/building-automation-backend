package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountConfigurationEmb;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
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
class AccountRestAdapterAdminActivationTest {
    private static final String URL = "/api/accounts/admin-activation";

    @Autowired
    AccountJpaRepository accountJpaRepository;


    @Autowired
    MockMvc mockMvc;


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void accountAdminActivation() throws Exception {
        //given
        final var accountToAdminActivation = accountJpaRepository.save(AccountEntity.builder()
                .withUsername(RandomStringGenerator.getRandomString())
                .withPassword(RandomStringGenerator.getRandomString())
                .withEmail(RandomStringGenerator.getRandomEmail())
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken(RandomStringGenerator.getRandomString())
                                .build())
                .build());
        final var activation = true;
        final var requestBuilder = MockMvcRequestBuilders
                .patch(URL + "/{id}", accountToAdminActivation.getId())
                .param("activation", String.valueOf(activation));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void accountAdminActivationNotFound() throws Exception {
        //given
        final var accountId = Integer.MAX_VALUE;
        final var activation = true;
        final var requestBuilder = MockMvcRequestBuilders
                .patch(URL + "/{id}", accountId)
                .param("activation", String.valueOf(activation));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
