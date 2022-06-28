package app.web.pczportfolio.pczbuildingautomation.account.application.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface AccountQueryDto {
    long getId();

    String getUsername();

    String getEmail();

    LocalDateTime getCreationTimestamp();

    @Value("#{target.accountRole}")
    String getRole();

}
