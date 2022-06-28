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
class AccountRestAdapterEmailConfirmationTest {
    private static final String URL = "/api/accounts/email-confirmation";
    private static final String ACCOUNT_OWNER = "someUser";
    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = ACCOUNT_OWNER)
    void accountEmailConfirmationTest() throws Exception {
        //given
        final var accountToEmailConfirmation = accountJpaRepository.save(
                AccountEntity.builder()
                        .withUsername(ACCOUNT_OWNER)
                        .withPassword(("user123"))
                        .withEmail("userWithoutActivation@gmail.com")
                        .withAccountRole(AccountRole.USER)
                        .withAccountConfigurationEmb(
                                AccountConfigurationEmb.builder()
                                        .withAdminActivation(false)
                                        .withEmailConfirmed(false)
                                        .withEnableToken("SomeEnableToken")
                                        .build())
                        .build()
        );
        final var requestToken = accountToEmailConfirmation.getAccountConfigurationEmb().getEnableToken();
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .param("token", requestToken);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = ACCOUNT_OWNER)
    void accountEmailConfirmationTokenNotFoundTest() throws Exception {
        //given
        final var accountToEmailConfirmation = accountJpaRepository.save(
                AccountEntity.builder()
                        .withUsername(ACCOUNT_OWNER)
                        .withPassword(("user123"))
                        .withEmail("userWithoutActivation@gmail.com")
                        .withAccountRole(AccountRole.USER)
                        .withAccountConfigurationEmb(
                                AccountConfigurationEmb.builder()
                                        .withAdminActivation(false)
                                        .withEmailConfirmed(false)
                                        .withEnableToken("SomeEnableToken")
                                        .build())
                        .build()
        );
        final var requestToken = "Different token";
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .param("token", requestToken);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
