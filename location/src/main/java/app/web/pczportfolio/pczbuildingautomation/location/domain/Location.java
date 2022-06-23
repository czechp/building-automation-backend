package app.web.pczportfolio.pczbuildingautomation.location.domain;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence.LocationEntity;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCommandDto;
import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private long id;
    private String name;
    private AccountParent accountParent;

    public static Location mapFromEntity(LocationEntity locationEntity) {
        return Location.builder()
                .withId(locationEntity.getId())
                .withName(locationEntity.getName())
                .withAccountParent(
                        AccountParent.builder()
                                .withId(locationEntity.getAccountSimpleEntity().getId())
                                .withUsername(locationEntity.getAccountSimpleEntity().getUsername())
                                .build())
                .build();
    }

    public static Location create(LocationCommandDto locationCommandDto, AccountFacadeDto accountFacadeDto) {
        return new Location(
                0L,
                locationCommandDto.getName(),
                new AccountParent(accountFacadeDto.getId(), accountFacadeDto.getUsername())
        );
    }
}
