package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.rest.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AccountRestAdapterLoginTest {
    private static final String URL = "/api/accounts/login";
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void loginTest() throws Exception {
        //given
        final var loginDto = LoginDto.builder()
                .withUsername("user")
                .withPassword("user123")
                .build();
        final var requestBody = objectMapper.writeValueAsString(loginDto);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void loginFailedTest() throws Exception {
        //given
        final var loginDto = LoginDto.builder()
                .withUsername(RandomStringGenerator.getRandomString())
                .withPassword(RandomStringGenerator.getRandomString())
                .build();
        final var requestBody = objectMapper.writeValueAsString(loginDto);
        final var requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
