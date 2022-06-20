package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseEmailConfirmation;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AccountRestAdapterEmailConfirmationTest {
    private static final String URL = "/api/accounts/email-confirmation";
    @Mock
    AccountUseCaseEmailConfirmation accountUseCaseEmailConfirmation;
    AccountRestAdapterEmailConfirmation accountRestAdapterEmailConfirmation;

    MockMvc mockMvc;

    @BeforeEach
    void init() {
        this.accountRestAdapterEmailConfirmation = new AccountRestAdapterEmailConfirmation(accountUseCaseEmailConfirmation);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountRestAdapterEmailConfirmation)
                .setControllerAdvice(HttpExceptionHandler.class)
                .build();
    }

    @Test
    void accountEmailConfirmationTest() throws Exception {
        //given
        final String token = "123321";
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .param("token", token);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void accountEmailConfirmationAccountNotFoundTest() throws Exception {
        //given
        final String token = "123321";
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .param("token", token);
        //when
        doThrow(NotFoundException.class).when(accountUseCaseEmailConfirmation)
                .accountEmailConfirmation(anyString());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void accountEmailConfirmationAccountTokensNotEqual() throws Exception {
        //given
        final String token = "123321";
        final var requestBuilder = MockMvcRequestBuilders.patch(URL)
                .param("token", token);
        //when
        doThrow(ConditionsNotFulFiledException.class).when(accountUseCaseEmailConfirmation)
                .accountEmailConfirmation(anyString());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}