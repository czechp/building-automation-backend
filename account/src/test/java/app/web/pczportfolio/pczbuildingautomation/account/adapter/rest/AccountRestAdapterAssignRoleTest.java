package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseAssignRole;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AccountRestAdapterAssignRoleTest {
    private final static String URL = "/api/accounts/role";
    @Mock
    AccountUseCaseAssignRole accountUseCaseAssignRole;
    AccountRestAdapterAssignRole accountRestAdapterAssignRole;
    MockMvc mockMvc;

    @BeforeEach
    void init() {
        this.accountRestAdapterAssignRole = new AccountRestAdapterAssignRole(accountUseCaseAssignRole);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountRestAdapterAssignRole)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
    }

    @Test
    void accountAssignRoleTest() throws Exception {
        //given
        final var accountId = 1L;
        final var newAccountRole = AccountRole.ADMIN;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/{id}", accountId)
                .param("role", newAccountRole.toString());
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void accountAssignRoleNotFoundTest() throws Exception {
        //given
        final var accountId = 1L;
        final var newAccountRole = AccountRole.ADMIN;
        final var requestBuilder = MockMvcRequestBuilders.patch(URL + "/{id}", accountId)
                .param("role", newAccountRole.toString());
        //when
        doThrow(NotFoundException.class).when(accountUseCaseAssignRole).accountAssignRole(anyLong(), any());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}