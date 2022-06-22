package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindById;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseAssignRole;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseAssignRoleImplTest {
    @Mock
    AccountPortSave accountPortSave;
    @Mock
    AccountPortFindById accountPortFindById;

    AccountUseCaseAssignRole accountUseCaseAssignRole;

    @BeforeEach
    void init() {
        this.accountUseCaseAssignRole = new AccountUseCaseAssignRoleImpl(accountPortFindById, accountPortSave);
    }

    @Test
    void accountAssignRoleTest() {
        //given
        final long accountId = 1L;
        final var newAccountRole = AccountRole.ADMIN;
        final var fetchedAccount = Account.builder()
                .withAccountRole(AccountRole.USER)
                .build();
        //when
        when(accountPortFindById.findAccountById(anyLong())).thenReturn(Optional.of(fetchedAccount));
        final var accountWithNewRole = accountUseCaseAssignRole.accountAssignRole(accountId, newAccountRole);
        //then
        assertEquals(newAccountRole, accountWithNewRole.getAccountRole());
    }

    @Test
    void accountAssignRoleNotFoundTest() {
        //given
        final long accountId = 1L;
        final var newAccountRole = AccountRole.ADMIN;
        //when
        when(accountPortFindById.findAccountById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, ()->accountUseCaseAssignRole.accountAssignRole(accountId, newAccountRole));
    }
}