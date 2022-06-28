package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationOwnerValidator {
    private final LocationPortFindCurrentUserAccount findCurrentUserAccount;

    public boolean currentUserIsOwner(Location location) {
        return validateOwning(location.getAccountParent().getUsername());
    }

    public boolean currentUserIsOwner(LocationQueryDto locationQueryDto) {
        return validateOwning(locationQueryDto.getOwner());
    }
    private boolean validateOwning(String locationOwner) {
        return findCurrentUserAccount.findAccountOfCurrentUser()
                .filter(a -> a.getUsername().equals(locationOwner) || a.getRole().equals("ADMIN"))
                .isPresent();
    }

}
