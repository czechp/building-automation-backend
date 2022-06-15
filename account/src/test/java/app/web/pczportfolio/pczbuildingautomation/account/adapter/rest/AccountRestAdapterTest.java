package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountDeletePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByIdPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByUsernameOrEmailPort;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountCommandDto;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@SpringJUnitConfig(AccountRestAdapterTest.TestConfiguration.class)
class AccountRestAdapterTest {
    private static final String URL = "/api/accounts";

    @MockBean
    AccountFindByUsernameOrEmailPort accountFindByUsernameOrEmailPort;
    @MockBean
    AccountCreatePort accountCreatePort;
    @MockBean
    AccountFindByIdPort accountFindByIdPort;
    @MockBean
    AccountDeletePort accountDeletePort;

    @Autowired
    AccountRestAdapter accountRestAdapter;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(accountRestAdapter)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void createAccountTest() throws Exception {
        //given
        AccountCommandDto accountCommandDto = new AccountCommandDto(
                "someUsername",
                "some@gmail.com",
                "somePassword",
                "somePassword");
        String requestBody = objectMapper.writeValueAsString(accountCommandDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        Mockito.when(accountFindByUsernameOrEmailPort
                        .findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createAccountPasswordNotEqualsTest() throws Exception {
        //given
        AccountCommandDto accountCommandDto = new AccountCommandDto(
                "someUsername",
                "some@gmail.com",
                "somePassword",
                "somePassword123");
        String requestBody = objectMapper.writeValueAsString(accountCommandDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        Mockito.when(accountFindByUsernameOrEmailPort
                        .findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createAccountPasswordAccountAlreadyExistsTest() throws Exception {
        //given
        AccountCommandDto accountCommandDto = new AccountCommandDto(
                "someUsername",
                "some@gmail.com",
                "somePassword",
                "somePassword");
        String requestBody = objectMapper.writeValueAsString(accountCommandDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        Mockito.when(accountFindByUsernameOrEmailPort
                        .findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(new Account()));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Configuration
    @ComponentScan("app.web.pczportfolio.pczbuildingautomation.account")
    static class TestConfiguration {

    }

}