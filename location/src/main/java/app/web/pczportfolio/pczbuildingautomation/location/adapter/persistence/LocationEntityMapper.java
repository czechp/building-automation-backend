package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.location.domain.AccountParent;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

class LocationEntityMapper {

    static LocationEntity mapToEntity(Location location) {
        return new LocationEntity(
                location.getId(),
                location.getVersion(),
                location.getCreationTimestamp(),
                location.getName(),
                new AccountSimpleEntity(
                        location.getAccountParent().getId(),
                        location.getAccountParent().getUsername()
                ),
                location.getClientUUID(),
                location.getClientName()
        );
    }


    static Location mapToDomain(LocationEntity locationEntity) {
        return Location.builder()
                .withId(locationEntity.getId())
                .withVersion(locationEntity.getVersion())
                .withCreationTimestamp(locationEntity.getCreationTimestamp())
                .withName(locationEntity.getName())
                .withAccountParent(
                        AccountParent.builder()
                                .withId(locationEntity.getAccountSimpleEntity().getId())
                                .withUsername(locationEntity.getAccountSimpleEntity().getUsername())
                                .build()
                )
                .withClientUUID(locationEntity.getClientUUID())
                .withClientName(locationEntity.getClientName())
                .build();
    }
}
