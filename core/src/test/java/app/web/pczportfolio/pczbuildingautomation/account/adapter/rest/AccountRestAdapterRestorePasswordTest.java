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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
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
                .withUsername(RandomStringGenerator.getRandomString())
                .withPassword(RandomStringGenerator.getRandomString())
                .withEmail(RandomStringGenerator.getRandomEmail())
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken(RandomStringGenerator.getRandomString())
                                .withNewPasswordToken(RandomStringGenerator.getRandomString())
                                .withNewPasswordTokenExpiration(LocalDateTime.now().plusMinutes(2))
                                .build())
                .build());
        final var requestTokenParameter = accountToSetPassword.getAccountConfigurationEmb().getNewPasswordToken();
        final var requestNewPwdParameter = RandomStringGenerator.getRandomString();
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
                .withUsername(RandomStringGenerator.getRandomString())
                .withPassword(RandomStringGenerator.getRandomString())
                .withEmail(RandomStringGenerator.getRandomEmail())
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken(RandomStringGenerator.getRandomString())
                                .withNewPasswordToken(RandomStringGenerator.getRandomString())
                                .withNewPasswordTokenExpiration(LocalDateTime.now().plusMinutes(2))
                                .build())
                .build());
        final var requestTokenParameter = RandomStringGenerator.getRandomString();
        final var requestNewPwdParameter = RandomStringGenerator.getRandomString();
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/new-password")
                .param("token", requestTokenParameter)
                .param("password", requestNewPwdParameter);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
