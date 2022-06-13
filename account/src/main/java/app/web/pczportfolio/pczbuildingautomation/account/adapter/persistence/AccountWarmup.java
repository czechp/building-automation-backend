package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        //TODO: use password encoder here

        logger.info("<>-------------------AccountWarmup-------------------<>");
        Stream.of(
                        new AccountEntity("admin", passwordEncoder.encode("admin123"), "admin@some123.pl"),
                        new AccountEntity("superuser", passwordEncoder.encode("superuser123"), "superuser@some123.pl"),
                        new AccountEntity("user", passwordEncoder.encode("user"), "user@some123.pl")
                )
                .forEach(repository::save);
    }
}