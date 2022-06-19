package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiled;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountRestAdapterCreateTest {
    private static final String URL = "/api/accounts";

    @Mock
    AccountUseCaseCreate accountUseCaseCreate;

    ObjectMapper objectMapper;
    MockMvc mockMvc;
    AccountRestAdapterCreate accountAdapterCreate;

    @BeforeEach
    void initTest() {
        this.accountAdapterCreate = new AccountRestAdapterCreate(accountUseCaseCreate);
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountAdapterCreate)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
    }


    @Test
    void createAccount() throws Exception {
        //given
        final AccountCreateCmdDto accountToCreate = new AccountCreateCmdDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );
        final String requestBody = objectMapper.writeValueAsString(accountToCreate);
        final Account resultFromUseCase = Account.builder().build();
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        when(accountUseCaseCreate.createAccount(accountToCreate)).thenReturn(resultFromUseCase);
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createAccountPasswordConditionsNotFullFilledTest() throws Exception {
        //given
        final AccountCreateCmdDto accountToCreate = new AccountCreateCmdDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword123"
        );
        final String requestBody = objectMapper.writeValueAsString(accountToCreate);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        when(accountUseCaseCreate.createAccount(accountToCreate)).thenThrow(new ConditionsNotFulFiled("Password not equal"));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account.application"})
    static class TestConfiguration {

    }
}