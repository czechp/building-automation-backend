package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseRestorePassword;
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
class AccountRestAdapterRestorePasswordTest {
    private static final String URL = "/api/accounts/password-restore";
    @Mock
    AccountUseCaseRestorePassword accountUseCaseRestorePassword;

    AccountRestAdapterRestorePassword accountRestAdapterRestorePassword;

    MockMvc mockMvc;

    @BeforeEach
    void init() {
        this.accountRestAdapterRestorePassword = new AccountRestAdapterRestorePassword(accountUseCaseRestorePassword);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountRestAdapterRestorePassword)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
    }

    @Test
    void generateTokenToRestorePasswordTest() throws Exception {
        //given
        final var email = "someEmail@gmail.com";
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/token")
                .param("email", email);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void generateTokenToRestorePasswordNotFoundTest() throws Exception {
        //given
        final var email = "someEmail@gmail.com";
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/token")
                .param("email", email);
        //when
        doThrow(NotFoundException.class).when(accountUseCaseRestorePassword)
                        .generateTokenToRestorePassword(email);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void generateTokenToRestorePasswordEmailsNotMatchTest() throws Exception {
        //given
        final var email = "someEmail@gmail.com";
        final var requestBuilder = MockMvcRequestBuilders.get(URL + "/token")
                .param("email", email);
        //when
        doThrow(ConditionsNotFulFiledException.class).when(accountUseCaseRestorePassword)
                .generateTokenToRestorePassword(email);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}