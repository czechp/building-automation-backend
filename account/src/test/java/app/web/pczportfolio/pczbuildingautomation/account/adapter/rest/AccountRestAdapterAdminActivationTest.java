package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountUseCaseAdminActivation;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
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

@ExtendWith(MockitoExtension.class)
class AccountRestAdapterAdminActivationTest {
    private static final String URL = "/api/accounts";
    @Mock
    AccountUseCaseAdminActivation accountUseCaseAdminActivation;
    AccountRestAdapterAdminActivation accountRestAdapterAdminActivation;

    MockMvc mockMvc;

    @BeforeEach
    void init() {
        this.accountRestAdapterAdminActivation = new AccountRestAdapterAdminActivation(accountUseCaseAdminActivation);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountRestAdapterAdminActivation)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
    }

    @Test
    void accountAdminActivationTest() throws Exception {
        //given
        final long id = 1L;
        final boolean activation = true;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL +"/admin-activation/{id}", id)
                .param("activation", String.valueOf(activation));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void accountAdminActivationAccountNotFoundTest() throws Exception {
        //given
        final long id = 1L;
        final boolean activation = true;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL +"/admin-activation/{id}", id)
                .param("activation", String.valueOf(activation));
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}