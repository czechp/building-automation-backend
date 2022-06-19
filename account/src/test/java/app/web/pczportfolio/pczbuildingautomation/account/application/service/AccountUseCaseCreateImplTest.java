package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortCreateNotifier;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsernameOrEmail;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(AccountUseCaseCreateImplTest.TestConfiguration.class)
class AccountUseCaseCreateImplTest {
    @MockBean
    AccountPortFindByUsernameOrEmail accountPortFindByUsernameOrEmail;
    @MockBean
    AccountPortSave accountPortSave;
    @MockBean
    AccountPortCreateNotifier accountPortCreateNotifier;
    @MockBean
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountUseCaseCreate accountUseCaseCreate;

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
        assertNotNull(newAccount.getEnableToken());
        assertFalse(newAccount.isEmailConfirmed());
        assertFalse(newAccount.isAdminActivation());
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
        assertThrows(ConditionsNotFulFiled.class, () -> accountUseCaseCreate.createAccount(accountToCreate));
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
        assertThrows(ConditionsNotFulFiled.class, () -> accountUseCaseCreate.createAccount(accountToCreate));
    }

    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account.application"})
    static class TestConfiguration {
    }
}