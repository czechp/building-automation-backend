package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountRestAdapterDeleteTest {
    private static final String URL = "/api/accounts";
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteAccountByAdminTest() throws Exception {
        //given
        final var accountToDelete = accountJpaRepository.save(
                AccountEntity.builder()
                .withUsername("userNew")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation")
                                .build())
                .build());
        final var requestId = accountToDelete.getId();
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", requestId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @WithMockUser(username = "accountOwner")
    void deleteAccountByOwnerTest() throws Exception {
        //given
        final var accountToDelete = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("accountOwner")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation")
                                .build())
                .build());
        final var requestId = accountToDelete.getId();
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", requestId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "someUser")
    void deleteAccountByNotOwnerTest() throws Exception {
        //given
        final var accountToDelete = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("someUser")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation")
                                .build())
                .build());
        final var requestId = accountToDelete.getId();
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", requestId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "accountOwner")
    void deleteAccountNotFoundTest() throws Exception {
        //given
        final var accountToDelete = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("userNew")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation")
                                .build())
                .build());
        final var requestId = accountToDelete.getId();
        final var requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", requestId);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
