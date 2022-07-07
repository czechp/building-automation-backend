package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsernameOrEmail;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortNotifierCreate;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseCreateImplTest {
    @Mock
    AccountPortFindByUsernameOrEmail accountPortFindByUsernameOrEmail;
    @Mock
    AccountPortSave accountPortSave;
    @Mock
    AccountPortNotifierCreate accountPortCreateNotifier;
    @Mock
    PasswordEncoder passwordEncoder;
    AccountUseCaseCreate accountUseCaseCreate;

    @BeforeEach
    void init() {
        this.accountUseCaseCreate = new AccountUseCaseCreateImpl(passwordEncoder,
                accountPortFindByUsernameOrEmail,
                accountPortCreateNotifier,
                accountPortSave);
    }

    @Test
    void createAccount() {
        //given
        final AccountCreateCmdDto accountToCreate = new AccountCreateCmdDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );
        //when
        when(accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        Account newAccount = accountUseCaseCreate.createAccount(accountToCreate);
        //then
        verify(accountPortSave, times(1)).saveAccount(any());
        verify(accountPortCreateNotifier, times(1)).notifyAboutNewAccount(anyString(), anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        assertEquals(accountToCreate.getUsername(), newAccount.getUsername());
        assertEquals(accountToCreate.getEmail(), newAccount.getEmail());
        assertNotNull(newAccount.getAccountConfiguration().getEnableToken());
        assertFalse(newAccount.getAccountConfiguration().isEmailConfirmed());
        assertFalse(newAccount.getAccountConfiguration().isAdminActivation());
    }

    @Test
    void createAccountAlreadyExists() {
        //given
        final AccountCreateCmdDto accountToCreate = new AccountCreateCmdDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );

        final Account accountExisted = Account.builder().build();

        //when
        when(accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(accountExisted));
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> accountUseCaseCreate.createAccount(accountToCreate));
    }

    @Test
    void createAccountPasswordAreDifferentTest() {
        //given
        final AccountCreateCmdDto accountToCreate = new AccountCreateCmdDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword123"
        );
        //when
        when(accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(ConditionsNotFulFiledException.class, () -> accountUseCaseCreate.createAccount(accountToCreate));
    }

    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account.application"})
    static class TestConfiguration {
    }
}