package app.web.pczportfolio.pczbuildingautomation.location.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountSimpleEntity;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;

class LocationEntityMapper {
    static Location mapToDomain(LocationEntity locationEntity) {
        return Location.mapFromEntity(locationEntity);
    }

    static LocationEntity mapToEntity(Location location) {
        return new LocationEntity(
                location.getId(),
                location.getVersion(),
                location.getCreationTimestamp(),
                location.getName(),
                new AccountSimpleEntity(
                        location.getAccountParent().getId(),
                        location.getAccountParent().getUsername()
                )
        );
    }
}
