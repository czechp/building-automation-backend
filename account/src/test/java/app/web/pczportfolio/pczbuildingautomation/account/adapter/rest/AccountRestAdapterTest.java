package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountCreatePort;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountFindByUsernameOrEmailPort;
import app.web.pczportfolio.pczbuildingautomation.account.application.useCase.AccountCreateUseCase;
import app.web.pczportfolio.pczbuildingautomation.configuration.HttpExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitConfig(AccountRestAdapterTest.TestConfiguration.class)
class AccountRestAdapterTest {

    @MockBean
    AccountCreateUseCase accountCreateUseCase;
    @MockBean
    AccountFindByUsernameOrEmailPort accountFindByUsernameOrEmailPort;
    @MockBean
    AccountCreatePort accountCreatePort;

    @Autowired
    AccountRestAdapter accountRestAdapter;

    MockMvc mockMvc;


    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(accountRestAdapter)
                .setControllerAdvice(new HttpExceptionHandler())
                .build();
    }

    @Test
    void createAccountTest() {
        //given
        //when
        //then
    }

    @Configuration
    @ComponentScan("app.web.pczportfolio.pczbuildingautomation.account")
    static class TestConfiguration {

    }

}