package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.*;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountDeleteByIdUseCase;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@SpringJUnitConfig(AccountCommandServiceTest.TestConfiguration.class)
class AccountCommandServiceTest {

    @MockBean
    AccountCreatePort accountCreatePort;
    @MockBean
    AccountFindByUsernameOrEmailPort accountFindByUsernameOrEmailPort;
    @MockBean
    AccountCreateEmailNotificationPort accountEmailNotificationPort;
    @MockBean
    AccountFindByIdPort accountFindByIdPort;
    @MockBean
    AccountDeletePort accountDeletePort;
    @Autowired
    AccountCreateUseCase accountCreateUseCase;

    @Autowired
    AccountDeleteByIdUseCase accountDeleteByIdUseCase;

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
        Mockito.when(accountFindByUsernameOrEmailPort.findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        accountCreateUseCase.createAccount(dto);
        //then
        Mockito.verify(accountCreatePort, Mockito.times(1))
                .createAccount(any());
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
        Mockito.when(accountFindByUsernameOrEmailPort
                .findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(BadRequestException.class, () -> accountCreateUseCase.createAccount(dto));
        Mockito.verify(accountCreatePort, Mockito.times(0))
                .createAccount(any());
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
        Mockito.when(accountFindByUsernameOrEmailPort.findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(Account.createFromCommandDto(dto)));
        //then
        assertThrows(BadRequestException.class, () -> accountCreateUseCase.createAccount(dto));
        Mockito.verify(accountCreatePort, Mockito.times(0))
                .createAccount(any());
        Mockito.verify(accountEmailNotificationPort, Mockito.times(0))
                .accountCreatedNotification(anyString(), anyString());
    }

    @Test
    void accountDeleteTest() {
        //given
        final long id = 1L;
        //when
        Mockito.when(accountFindByIdPort.findAccountById(anyLong()))
                .thenReturn(Optional.of(new Account()));
        //then
    }

    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account"})
    static class TestConfiguration {
    }
}