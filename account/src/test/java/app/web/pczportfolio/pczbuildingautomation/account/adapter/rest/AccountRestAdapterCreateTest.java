package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortCreateNotifier;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortFindByUsernameOrEmail;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(AccountRestAdapterCreateTest.TestConfiguration.class)
class AccountRestAdapterCreateTest {
    private static final String URL = "/api/accounts";
    @MockBean
    AccountPortFindByUsernameOrEmail accountPortFindByUsernameOrEmail;
    @MockBean
    AccountPortSave accountPortSave;
    @MockBean
    AccountPortCreateNotifier accountPortCreateNotifier;
    @MockBean
    PasswordEncoder passwordEncoder;
    @Autowired
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

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        when(accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createAccountPasswordNotEqualsTest() throws Exception {
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
        when(accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createAccountExistsTest() throws Exception {
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

        final Account existedAccount = Account.builder()
                .withUsername("someUsername")
                .withEmail("someEmail@gmail.com")
                .build();
        //when
        when(accountPortFindByUsernameOrEmail.findAccountByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(existedAccount));
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Configuration
    @ComponentScan({"app.web.pczportfolio.pczbuildingautomation.account.application"})
    static class TestConfiguration {

    }
}