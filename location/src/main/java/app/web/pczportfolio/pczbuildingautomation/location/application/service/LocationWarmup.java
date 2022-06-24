package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindAccountUsername;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortSave;
import app.web.pczportfolio.pczbuildingautomation.location.domain.AccountParent;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Profile("development")
@Service
class LocationWarmup {
    private final LocationPortSave locationPortSave;
    private final LocationPortFindAccountUsername locationPortFindAccountUsername;
    private final Logger logger;

    public LocationWarmup(LocationPortSave locationPortSave, LocationPortFindAccountUsername locationPortFindAccountUsername) {
        this.locationPortSave = locationPortSave;
        this.locationPortFindAccountUsername = locationPortFindAccountUsername;
        this.logger = LoggerFactory.getLogger(LocationWarmup.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        logger.info("<>--------------------------Warmup for LOCATION ENTITY--------------------------<>");
        locationPortFindAccountUsername.findAccountByUsername("user")
                .ifPresent((accountFacadeDto) -> {
                    locationForDevelopment(accountFacadeDto)
                            .forEach(locationPortSave::saveLocation);
                });
    }

    private List<Location> locationForDevelopment(AccountFacadeDto accountFacadeDto) {
        return Arrays.asList(
                Location.builder()
                        .withName("First location name")
                        .withAccountParent(AccountParent.builder()
                                .withId(accountFacadeDto.getId())
                                .withUsername(accountFacadeDto.getUsername())
                                .build())
                        .build(),
                Location.builder()
                        .withName("Second location name")
                        .withAccountParent(AccountParent.builder()
                                .withId(accountFacadeDto.getId())
                                .withUsername(accountFacadeDto.getUsername())
                                .build())
                        .build(), Location.builder()
                        .withName("Third location name")
                        .withAccountParent(AccountParent.builder()
                                .withId(accountFacadeDto.getId())
                                .withUsername(accountFacadeDto.getUsername())
                                .build())
                        .build()
        );
    }
}
