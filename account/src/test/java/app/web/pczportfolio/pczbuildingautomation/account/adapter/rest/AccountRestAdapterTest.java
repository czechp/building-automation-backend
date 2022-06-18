package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCommandPort;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.domain.AccountTestUseCases;
import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCommandDto;
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

import static org.mockito.ArgumentMatchers.*;

@SpringJUnitConfig(AccountRestAdapterTest.TestConfiguration.class)
class AccountRestAdapterTest {
    private static final String URL = "/api/accounts";

    @MockBean
    AccountCommandPort accountCommandPort;
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
        Mockito.when(accountCommandPort
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
        Mockito.when(accountCommandPort.findAccountByUsernameOrEmail(anyString(), anyString()))
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
        Mockito.when(accountCommandPort
                        .findAccountByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(new Account()));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteAccountTest() throws Exception {
        //given
        final long id = 1L;
        final String commonUsername = "user";
        final Account accountToDelete = Account.create(new AccountCommandDto(
                commonUsername,
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        ));
        final Account currentLoggedUser = Account.create(new AccountCommandDto(
                commonUsername,
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        ));
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong()))
                .thenReturn(Optional.of(accountToDelete));

        Mockito.when((accountCommandPort.findCurrentLoggedUser()))
                .thenReturn(Optional.of(currentLoggedUser));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteAccountNotExistsTest() throws Exception {
        final long id = 1L;
        //given
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong()))
                .thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void accountAdminActivationTest() throws Exception {
        //given
        final long id = 1L;
        final boolean activation = true;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/admin-activation/{id}", id)
                .param("activation", "true");
        final Account account = Account.create(new AccountCommandDto("someUsername",
                "someEmail@gmail.com",
                "somePwd",
                "somePwd"
        ));
        //when
        Mockito.when(accountCommandPort.findAccountById(anyLong())).thenReturn(Optional.of(account));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void accountAdminActivationAccountNotExistsTest() throws Exception {
        //given
        final long id = 1L;
        final boolean activation = true;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/admin-activation/{id}", id)
                .param("activation", "true");

        Mockito.when(accountCommandPort.findAccountById(anyLong())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void accountEmailConfirmationTest() throws Exception {
        //given
        final Account account = AccountTestUseCases.cases.get("accountToEmailConfirmed");
        final String token = account.getEnableToken();
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/email-confirmation/{token}", token);
        //when
        Mockito.when(accountCommandPort.findAccountByEnableToken(any())).thenReturn(Optional.of(account));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void accountEmailConfirmationAccountNotFoundTest() throws Exception {
        final String token = "12345";
        //given
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/email-confirmation/{token}", token);
        //when
        Mockito.when(accountCommandPort.findAccountByEnableToken(any())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void accountEmailConfirmationTokensDoNotMatch() throws Exception {
        final Account account = AccountTestUseCases.cases.get("accountToEmailConfirmed");
        final String token = "different token";
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL + "/email-confirmation/{token}", token);
        //when
        Mockito.when(accountCommandPort.findAccountByEnableToken(any())).thenReturn(Optional.of(account));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Configuration
    @ComponentScan("app.web.pczportfolio.pczbuildingautomation.account")
    static class TestConfiguration {

    }

}