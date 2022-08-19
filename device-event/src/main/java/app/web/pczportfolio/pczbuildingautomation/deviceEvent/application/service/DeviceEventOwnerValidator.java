package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.AccountFacade;
import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceEventOwnerValidator {

    private final AccountFacade accountFacade;

    public void validateDeviceEventOwningOrThrowException(DeviceEvent deviceEvent) {
        if(!isOwner(deviceEvent))
            throw new NotEnoughPrivilegesException("You are not owner of event with id: " + deviceEvent.getId());

    }

    private boolean isOwner(DeviceEvent deviceEvent) {
        AccountFacadeDto currentUserAccount = getCurrentUserAccount();
        return deviceEvent.checkOwning(currentUserAccount.getUsername()) || currentUserAccount.getRole().equals("ADMIN");
    }

    private AccountFacadeDto getCurrentUserAccount() {
        return accountFacade.findCurrentUserAccount().orElseThrow(() -> new NotEnoughPrivilegesException("You are not logged"));
    }

}
