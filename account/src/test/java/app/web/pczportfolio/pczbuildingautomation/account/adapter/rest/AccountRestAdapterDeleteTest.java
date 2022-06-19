package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseDelete;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AccountRestAdapterDeleteTest {
    private static final String URL = "/api/accounts";
    @Mock
    AccountUseCaseDelete accountUseCaseDelete;
    AccountRestAdapterDelete accountRestAdapterDelete;
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        this.accountRestAdapterDelete = new AccountRestAdapterDelete(accountUseCaseDelete);
        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountRestAdapterDelete)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
    }

    @Test
    void deleteAccountTest() throws Exception {
        //given
        final long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void deleteAccountNotExistsTest() throws Exception {
        //given
        final long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        doThrow(new NotFoundException("Account not exists")).when(accountUseCaseDelete).deleteAccount(anyLong());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteAccountNoPrivilegesTest() throws Exception {
        //given
        final long id = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "/{id}", id);
        //when
        doThrow(new NotEnoughPrivilegesException("You have no permission to delete the account")).when(accountUseCaseDelete).deleteAccount(anyLong());
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}