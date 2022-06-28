package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.RandomStringGenerator;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountConfigurationEmb;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountEntity;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountJpaRepository;
import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountRestAdapterAssignRoleTest {
    private static final String URl = "/api/accounts/role";

    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void accountAssignRoleTest() throws Exception {
        //given
        final var accountToAssignRole = accountJpaRepository.save(AccountEntity.builder()
                .withUsername(RandomStringGenerator.getRandomString())
                .withPassword(RandomStringGenerator.getRandomString())
                .withEmail(RandomStringGenerator.getRandomEmail())
                .withAccountRole(AccountRole.USER)
                .withAccountConfigurationEmb(
                        AccountConfigurationEmb.builder()
                                .withAdminActivation(false)
                                .withEmailConfirmed(false)
                                .withEnableToken(RandomStringGenerator.getRandomString())
                                .build())
                .build());

        final var requestId = accountToAssignRole.getId();
        final var requestNewRole = AccountRole.ADMIN.toString();
        final var requestBuilder = MockMvcRequestBuilders.patch(URl+"/{id}", requestId)
                .param("role", requestNewRole);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void accountAssignRoleNotFoundTest() throws Exception {
        //given
        final var requestId = Integer.MAX_VALUE;
        final var requestNewRole = AccountRole.ADMIN.toString();
        final var requestBuilder = MockMvcRequestBuilders.patch(URl+"/{id}", requestId)
                .param("role", requestNewRole);
        //when
        //then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
