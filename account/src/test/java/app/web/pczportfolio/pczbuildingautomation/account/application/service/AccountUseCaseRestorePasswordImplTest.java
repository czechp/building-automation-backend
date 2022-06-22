package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByEmail;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortNotifierRestorePasswordToken;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.domain.AccountConfiguration;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseRestorePasswordImplTest {
    @Mock
    AccountPortFindByEmail accountPortFindByEmail;
    @Mock
    AccountPortSave accountPortSave;
    @Mock
    AccountPortNotifierRestorePasswordToken accountPortNotifierRestorePasswordToken;

    AccountUseCaseRestorePasswordImpl accountUseCaseRestorePassword;

    @BeforeEach
    void init() {
        this.accountUseCaseRestorePassword = new AccountUseCaseRestorePasswordImpl(
                accountPortFindByEmail,
                accountPortSave,
                accountPortNotifierRestorePasswordToken
        );
    }

    @Test
    void generateTokenToRestorePasswordTest() {
        //given
        final var email = "someEmail@gmail.com";
        final var fetchedAccount = Account.builder()
                .withEmail("someEmail@gmail.com")
                .withAccountConfiguration(
                        AccountConfiguration.builder()
                                .withNewPasswordTokenExpiration(LocalDateTime.of(1991, 3, 1, 22, 12))
                                .withEnableToken("")
                                .build()
                )
                .build();
        //when
        when(accountPortFindByEmail.findAccountByEmail(anyString())).thenReturn(Optional.of(fetchedAccount));
        final var accountWithGeneratedToken = accountUseCaseRestorePassword.generateTokenToRestorePassword(email);
        //then
        boolean tokenGenerated = !accountWithGeneratedToken.getAccountConfiguration().getNewPasswordToken().equals("");
        assertTrue(tokenGenerated);
        LocalDateTime tokenExpiration = accountWithGeneratedToken.getAccountConfiguration().getNewPasswordTokenExpiration();
        boolean tokenIsAfter = tokenExpiration.isAfter(LocalDateTime.now()
                .plusMinutes(AccountConfiguration.NEW_PASSWORD_TOKEN_LIVING_MINUTES_DURATION)
                .minusMinutes(1));
        boolean tokenIfBefore = tokenExpiration.isBefore(LocalDateTime.now()
                .plusMinutes(AccountConfiguration.NEW_PASSWORD_TOKEN_LIVING_MINUTES_DURATION)
                .plusMinutes(1)
        );
        assertTrue(tokenIsAfter && tokenIfBefore);
        verify(accountPortNotifierRestorePasswordToken, times(1)).sendRestorePasswordToken(anyString(), anyString());
        verify(accountPortSave, times(1)).saveAccount(fetchedAccount);
    }

    @Test
    void generateTokenToRestorePasswordAccountNotFoundTest() {
        //given
        final var email = "someEmail@gmail.com";
        //when
        when(accountPortFindByEmail.findAccountByEmail(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> accountUseCaseRestorePassword.generateTokenToRestorePassword(email));
    }

    @Test
    void generateTokenToRestorePasswordEmailNotMatchedTest() {
        //given
        final var email = "someEmail@gmail.com";
        final var fetchedAccount = Account.builder()
                .withEmail("someEmail123@gmail.com")
                .withAccountConfiguration(
                        AccountConfiguration.builder()
                                .withNewPasswordTokenExpiration(LocalDateTime.of(1991, 3, 1, 22, 12))
                                .withEnableToken("")
                                .build()
                )
                .build();
        //when
        when(accountPortFindByEmail.findAccountByEmail(anyString())).thenReturn(Optional.of(fetchedAccount));
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> accountUseCaseRestorePassword.generateTokenToRestorePassword(email));
    }

}