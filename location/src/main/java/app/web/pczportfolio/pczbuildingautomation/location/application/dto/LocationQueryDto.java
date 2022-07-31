package app.web.pczportfolio.pczbuildingautomation.location.application.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LocationQueryDto {
    long getId();

    @Value("#{target.accountSimpleEntity.username}")
    String getOwner();

    String getName();

    LocalDateTime getCreationTimestamp();

    String getClientUUID();

    String getClientName();
}
