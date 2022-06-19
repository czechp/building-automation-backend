package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortDelete;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseDeleteImplTest {
    @Mock
    AccountPortFindById accountPortFindById;
    @Mock
    AccountPortDelete accountPortDelete;
    AccountUseCaseDelete accountUseCaseDelete;

    @BeforeEach
    void init() {
        this.accountUseCaseDelete = new AccountUseCaseDeleteImpl(accountPortFindById, accountPortDelete);
    }

    @Test
    void deleteAccount() {
        //given
        final long id = 1L;
        final Account accountToDelete = Account.builder().build();
        //when
        when(accountPortFindById.findAccountById(anyLong())).thenReturn(Optional.of(accountToDelete));
        accountUseCaseDelete.deleteAccount(id);
        //then
        verify(accountPortDelete, times(1)).deleteAccount(accountToDelete);
    }


    @Test
    void deleteAccountNotExists() {
        //given
        final long id = 1L;
        //when
        when(accountPortFindById.findAccountById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> accountUseCaseDelete.deleteAccount(id));
    }

    @Configuration
    @ComponentScan("app.web.pczportfolio.pczbuildingautomation.account.application")
    static class TestConfiguration {

    }
}