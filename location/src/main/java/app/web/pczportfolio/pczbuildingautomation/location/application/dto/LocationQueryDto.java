package app.web.pczportfolio.pczbuildingautomation.location.application.dto;

import org.springframework.beans.factory.annotation.Value;

public interface LocationQueryDto {
    long getId();

    @Value("#{target.accountSimpleEntity.username}")
    String getOwner();
    String getName();
}
