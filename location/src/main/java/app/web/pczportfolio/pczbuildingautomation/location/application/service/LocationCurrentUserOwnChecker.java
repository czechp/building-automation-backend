package app.web.pczportfolio.pczbuildingautomation.location.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.port.LocationPortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationCurrentUserOwnChecker {
    private final LocationPortFindCurrentUserAccount locationPortFindCurrentUserAccount;

    public boolean checkCurrentUserOwning(LocationQueryDto locationQueryDto) {
        return validateOwning(locationQueryDto.getOwner());
    }


    public boolean checkCurrentUserOwning(Location location) {
        return validateOwning(location.getAccountParent().getUsername());
    }
    private boolean validateOwning(String owner) {
        AccountFacadeDto currentUserAccount = getCurrentUserAccount();
        return currentUserAccount.getRole().equals("ADMIN") || currentUserAccount.getUsername().equals(owner);
    }

    private AccountFacadeDto getCurrentUserAccount() {
        return locationPortFindCurrentUserAccount.findAccountOfCurrentUser()
                .orElseThrow(() -> new NotFoundException("Currently none user logged"));
    }

}
