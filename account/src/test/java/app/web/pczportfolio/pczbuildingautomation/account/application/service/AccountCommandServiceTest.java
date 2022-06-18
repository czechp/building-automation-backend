package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCommandPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountNotificationPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountAdminActivateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountDeleteByIdUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountEmailConfirmUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.domain.AccountTestUseCases;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiled;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringJUnitConfig(AccountCommandServiceTest.TestConfiguration.class)
class AccountCommandServiceTest {

    @MockBean
    AccountCommandPort accountCommandPort;
    @MockBean
    AccountNotificationPort accountEmailNotificationPort;

    @Autowired
    AccountCreateUseCase accountCreateUseCase;

    @Autowired
    AccountDeleteByIdUseCase accountDeleteByIdUseCase;

    @Autowired
    AccountAdminActivateUseCase accountAdminActivateUseCase;

    @Autowired
    AccountEmailConfirmUseCase accountEmailConfirmUseCase;

    @Test
    void createAccountTest() {
        //given
        final AccountCommandDto dto = new AccountCommandDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );
        //when
        Mockito.when(accountCommandPort.findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        accountCreateUseCase.createAccount(dto);
        //then
        Mockito.verify(accountCommandPort, Mockito.times(1))
                .saveAccount(any());
        Mockito.verify(accountEmailNotificationPort, Mockito.times(1))
                .accountCreatedNotification(anyString(), anyString());
    }

    @Test
    void createAccountPasswordsNotEqualTest() {
        //given
        final AccountCommandDto dto = new AccountCommandDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "differentPassword"
        );
        //when
        Mockito.when(accountCommandPort
                .findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(ConditionsNotFulFiled.class, () -> accountCreateUseCase.createAccount(dto));
        Mockito.verify(accountCommandPort, Mockito.times(0))
                .saveAccount(any());
        Mockito.verify(accountEmailNotificationPort, Mockito.times(0))
                .accountCreatedNotification(anyString(), anyString());
    }

    @Test
    void createAccountUsernameOrEmailExistsTest() {
        //given
        AccountCommandDto dto = new AccountCommandDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );
        //when
        Mockito.when(accountCommandPort.findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(Account.create(dto)));
        //then
        assertThrows(ConditionsNotFulFiled.class, () -> accountCreateUseCase.createAccount(dto));
        Mockito.verify(accountCommandPort, Mockito.times(0))
                .saveAccount(any());
        Mockito.verify(accountEmailNotificationPort, Mockito.times(0))
                .accountCreatedNotification(anyString(), anyString());
    }

    @Test
    void accountDeleteTest() {
        //given
        final long id = 1L;
        final String commonUsername = "user";
        final Account accountToDelete = Account.create(new AccountCommandDto(
                commonUsername,
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        ));
        final Account currentLoggedUser = Account.create(new AccountCommandDto(
                commonUsername,
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        ));
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong()))
                .thenReturn(Optional.of(accountToDelete));

        Mockito.when((accountCommandPort.findCurrentLoggedUser()))
                .thenReturn(Optional.of(currentLoggedUser));

        accountDeleteByIdUseCase.deleteAccountById(id);
        //then
        Mockito.verify(accountCommandPort, Mockito.times(1)).deleteAccount(any());
    }


    @Test
    void accountDeleteNotExistsTest() {
        //given
        final long id = 1L;
        final String commonUsername = "user";
        final Account accountToDelete = Account.create(new AccountCommandDto(
                commonUsername,
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        ));
        final Account currentLoggedUser = Account.create(new AccountCommandDto(
                commonUsername,
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        ));
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong()))
                .thenReturn(Optional.empty());

        Mockito.when((accountCommandPort.findCurrentLoggedUser()))
                .thenReturn(Optional.of(currentLoggedUser));

        //then
        assertThrows(NotFoundException.class, () -> accountDeleteByIdUseCase.deleteAccountById(id));
        Mockito.verify(accountCommandPort, Mockito.times(0)).deleteAccount(any());
    }

    @Test
    void adminActivateTest() {
        //given
        final long accountId = 1L;
        final boolean accountActivation = true;
        final Account account = Account.create(new AccountCommandDto(
                        "someUsername",
                        "someEmail@gmail.com",
                        "somePassword",
                        "somePassword"
                )
        );
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong())).thenReturn(Optional.of(account));
        Account activatedAccount = accountAdminActivateUseCase.accountAdminActivation(accountId, accountActivation);
        //then
        assertEquals(accountActivation, activatedAccount.isAdminActivation());
        Mockito.verify(accountCommandPort, Mockito.times(1)).saveAccount(any());
    }

    @Test
    void adminActivateAccountNotFoundTest() {
        final long accountId = 1L;
        final boolean accountActivation = true;
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> accountAdminActivateUseCase.accountAdminActivation(accountId, accountActivation));

    }

    @Test
    void emailConfirmedTest() {
        //given
        final Account account = AccountTestUseCases.cases.get("accountToEmailConfirmed");
        final String token = account.getEnableToken();
        //when
        Mockito.when(accountCommandPort.findAccountByEnableToken(any())).thenReturn(Optional.of(account));
        Account emailConfirmedAccount = accountEmailConfirmUseCase.accountConfirmEmail(token);
        //then
        assertTrue(emailConfirmedAccount.isEmailConfirmed());
        Mockito.verify(accountCommandPort, Mockito.times(1)).saveAccount(any());

    }

    @Test
    void emailConfirmedTokensNotEqualTest() {
        //given
        final Account account = AccountTestUseCases.cases.get("accountToEmailConfirmed");
        final String token = "Another token";
        //when
        Mockito.when(accountCommandPort.findAccountByEnableToken(any())).thenReturn(Optional.of(account));
        //then
        assertThrows(ConditionsNotFulFiled.class, () -> accountEmailConfirmUseCase.accountConfirmEmail(token));
    }


    @Test
    void emailConfirmedAccountNotFoundTest() {
        //given
        //when
        Mockito.when(accountCommandPort.findAccountByEnableToken(any())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> accountEmailConfirmUseCase.accountConfirmEmail(anyString()));
    }
    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account"})
    static class TestConfiguration {
    }
}