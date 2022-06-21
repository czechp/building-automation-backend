package app.web.pczportfolio.pczbuildingautomation.account.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence.AccountRole;
import app.web.pczportfolio.pczbuildingautomation.account.application.port.AccountPortSave;
import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import app.web.pczportfolio.pczbuildingautomation.account.domain.AccountConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("development")
class AccountWarmup {
    private final AccountPortSave accountPortSave;
    final private Logger logger;
    final private PasswordEncoder passwordEncoder;

    public AccountWarmup(AccountPortSave accountPortSave, PasswordEncoder passwordEncoder) {
        this.accountPortSave = accountPortSave;
        this.passwordEncoder = passwordEncoder;
        this.logger = LoggerFactory.getLogger(AccountWarmup.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<>--------------------------Warmup for ACCOUNT ENTITY--------------------------<>");
        accountForDevelopment()
                .forEach(accountPortSave::saveAccount);
    }

    private List<Account> accountForDevelopment() {
        return Arrays.asList(
                Account.builder()
                        .withUsername("admin")
                        .withPassword(passwordEncoder.encode("admin123"))
                        .withEmail("admin@gmail.com")
                        .withAccountRole(AccountRole.ADMIN)
                        .withEnableToken("admin321")
                        .withEmailConfirmed(true)
                        .withAccountConfiguration(AccountConfiguration.builder().withAdminActivation(true).build())
                        .build(),
                Account.builder()
                        .withUsername("superuser")
                        .withPassword(passwordEncoder.encode("superuser123"))
                        .withEmail("superuser@gmail.com")
                        .withAccountRole(AccountRole.SUPERUSER)
                        .withEnableToken("superuser321")
                        .withEmailConfirmed(true)
                        .withAccountConfiguration(AccountConfiguration.builder().withAdminActivation(true).build())
                        .build(),
                Account.builder()
                        .withUsername("user")
                        .withPassword(passwordEncoder.encode("user123"))
                        .withEmail("user@gmail.com")
                        .withAccountRole(AccountRole.USER)
                        .withEnableToken("user321")
                        .withEmailConfirmed(true)
                        .withAccountConfiguration(AccountConfiguration.builder().withAdminActivation(true).build())
                        .build(),
                Account.builder()
                        .withUsername("userWithoutActivation")
                        .withPassword(passwordEncoder.encode("user123"))
                        .withEmail("userWithoutActivation@gmail.com")
                        .withAccountRole(AccountRole.USER)
                        .withEnableToken("user321")
                        .withEmailConfirmed(true)
                        .withAccountConfiguration(AccountConfiguration.builder().withAdminActivation(false).build())
                        .build()
        );
    }

}
