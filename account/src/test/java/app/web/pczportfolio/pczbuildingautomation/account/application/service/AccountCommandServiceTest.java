package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreateEmailNotificationPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByUsernameOrEmailPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringJUnitConfig(AccountCommandServiceTest.TestConfiguration.class)
class AccountCommandServiceTest {

    @Autowired
    AccountCreateUseCase accountCreateUseCase;

    @MockBean
    AccountCreatePort accountCreatePort;
    @MockBean
    AccountFindByUsernameOrEmailPort findByUsernameOrEmailPort;
    @MockBean
    AccountCreateEmailNotificationPort emailNotificationPort;

    @Test
    void createAccountTest() {
        //given
        AccountCommandDto dto = new AccountCommandDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );
        //when
        Mockito.when(findByUsernameOrEmailPort.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        accountCreateUseCase.createAccount(dto);
        //then
        Mockito.verify(accountCreatePort, Mockito.times(1))
                .createAccount(any());
        Mockito.verify(emailNotificationPort, Mockito.times(1))
                .accountCreatedNotification(anyString(), anyString());
    }

    @Test
    void createAccountPasswordsNotEqualTest() {
        //given
        AccountCommandDto dto = new AccountCommandDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "differentPassword"
        );
        //when
        Mockito.when(findByUsernameOrEmailPort
                .findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(BadRequestException.class, () -> accountCreateUseCase.createAccount(dto));
        Mockito.verify(accountCreatePort, Mockito.times(0))
                .createAccount(any());
        Mockito.verify(emailNotificationPort, Mockito.times(0))
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
        Mockito.when(findByUsernameOrEmailPort.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(Account.createFromCommandDto(dto)));
        //then
        assertThrows(BadRequestException.class, () -> accountCreateUseCase.createAccount(dto));
        Mockito.verify(accountCreatePort, Mockito.times(0))
                .createAccount(any());
        Mockito.verify(emailNotificationPort, Mockito.times(0))
                .accountCreatedNotification(anyString(), anyString());
    }


    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account"})
    static class TestConfiguration {
    }
}