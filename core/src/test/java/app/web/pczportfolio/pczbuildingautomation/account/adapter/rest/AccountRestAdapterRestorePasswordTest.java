package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountConfigurationEmb;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
class AccountRestAdapterRestorePasswordTest {
    private static final String URL = "/api/accounts/password-restore";

    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void generateTokenToRestorePasswordTest() throws Exception {
        //given
        final var accountToGeneratingToken = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("userNew123")
                .withPassword(("user123"))
                .withEmail("userWithoutActivationqwer@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation1233567")
                                .build())
                .build());
        final var emailRequestParameter = accountToGeneratingToken.getEmail();
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/token")
                .param("email", emailRequestParameter);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void generateTokenToRestorePasswordEmailNotFoundTest() throws Exception {
        //given
        final var accountToGeneratingToken = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("userNew9")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation9@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation123456")
                                .build())
                .build());
        final var emailRequestParameter = "differentEmail@gmail.com";
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/token")
                .param("email", emailRequestParameter);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void setNewPasswordTokenTest() throws Exception {
        //given
        final var accountToSetPassword = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("userNew8")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation8@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation1234")
                                .withNewPasswordToken("newPasswordToken1234")
                                .withNewPasswordTokenExpiration(LocalDateTime.now().plusMinutes(2))
                                .build())
                .build());
        final var requestTokenParameter = accountToSetPassword.getAccountConfigurationEmb().getNewPasswordToken();
        final var requestNewPwdParameter = "newPassword123321";
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/new-password")
                .param("token", requestTokenParameter)
                .param("password", requestNewPwdParameter);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void setNewPasswordTokenDoesNotMatch() throws Exception {
        //given
        final var accountToSetPassword = accountJpaRepository.save(AccountEntity.builder()
                .withUsername("userNew123")
                .withPassword(("user123"))
                .withEmail("userWithoutActivation123@gmail.com")
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken("withoutActivation123")
                                .withNewPasswordToken("newPasswordToken12345")
                                .withNewPasswordTokenExpiration(LocalDateTime.now().plusMinutes(2))
                                .build())
                .build());
        final var requestTokenParameter = "differentToken";
        final var requestNewPwdParameter = "newPassword123321";
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/new-password")
                .param("token", requestTokenParameter)
                .param("password", requestNewPwdParameter);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
