package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
@Profile("development")
class AccountWarmup {
    private final AccountJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(AccountWarmup.class);
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    void init() {

        logger.info("<>-------------------AccountWarmup-------------------<>");
        Stream.of(
                        new AccountEntity(
                                "admin",
                                passwordEncoder.encode("admin123"),
                                "admin@some123.pl",
                                UUID.randomUUID().toString(),
                                AccountRole.ADMIN
                        ),
                        new AccountEntity(
                                "superuser",
                                passwordEncoder.encode("superuser123"),
                                "superuser@some123.pl", UUID.randomUUID().toString(),
                                AccountRole.SUPERUSER),
                        new AccountEntity(
                                "user",
                                passwordEncoder.encode("user123"),
                                "user@some123.pl", UUID.randomUUID().toString(),
                                AccountRole.USER)
                )
                .map(this::activateAccount)
                .forEach(repository::save);
    }

    private AccountEntity activateAccount(AccountEntity accountEntity) {
        accountEntity.setAdminConfirmed(true);
        accountEntity.setEmailConfirmed(true);
        return accountEntity;
    }
}
