package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.account.application.dto.AccountCreateCmdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("development")
class AccountRestAdapterCreateTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void someTest() throws Exception {
        //given
        final var accountCreateDto = new AccountCreateCmdDto(
                "someUsername",
                "someEmail@gmail.com",
                "somePassword",
                "somePassword"
        );

        final var requestBody = objectMapper.writeValueAsString(accountCreateDto);

        final var requestBuilder = MockMvcRequestBuilders.post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
