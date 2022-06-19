package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortDelete;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsername;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityUtilities;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    AccountPortFindByUsername accountPortFindByUsername;
    @Mock
    AccountPortDelete accountPortDelete;
    @Mock
    SecurityUtilities securityUtilities;


    AccountUseCaseDelete accountUseCaseDelete;

    @BeforeEach
    void init() {
        this.accountUseCaseDelete = new AccountUseCaseDeleteImpl(
                accountPortFindById,
                accountPortDelete,
                accountPortFindByUsername,
                securityUtilities);
    }

    @Test
    void deleteAccountByOwner() {
        //given
        final long id = 1L;
        final Account accountToDelete = Account
                .builder()
                .withUsername("user")
                .build();
        final String currentLoggedUser = "user";
        //when
        when(accountPortFindById.findAccountById(anyLong())).thenReturn(Optional.of(accountToDelete));
        when(securityUtilities.getCurrentUser()).thenReturn(currentLoggedUser);
        accountUseCaseDelete.deleteAccount(id);
        //then
        verify(accountPortDelete, times(1)).deleteAccount(accountToDelete);
        verify(securityUtilities, times(1)).getCurrentUser();
    }


    @Test
    void deleteAccountByAdmin() {
        final long id = 1L;
        final Account accountToDelete = Account
                .builder()
                .withUsername("user")
                .build();
        final String currentLoggedUser = "user123";
        final Account currentUserIsAdmin = Account.builder()
                .withAccountRole(AccountRole.ADMIN)
                .build();
        //when
        when(accountPortFindById.findAccountById(anyLong())).thenReturn(Optional.of(accountToDelete));
        when(securityUtilities.getCurrentUser()).thenReturn(currentLoggedUser);
        when(accountPortFindByUsername.findAccountByUsername(anyString())).thenReturn(Optional.of(currentUserIsAdmin));
        accountUseCaseDelete.deleteAccount(id);
        //then
        verify(accountPortDelete, times(1)).deleteAccount(accountToDelete);
        verify(securityUtilities, times(1)).getCurrentUser();
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