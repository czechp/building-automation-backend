package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SwitchDeviceOwnerValidator {
    private final SwitchDevicePortFindCurrentUserAccount switchDevicePortFindCurrentUserAccount;

    public boolean currentUserIsOwner(SwitchDevice switchDevice) {
        return validateOwning(switchDevice.getOwner());
    }

    private boolean validateOwning(String owner) {
        return switchDevicePortFindCurrentUserAccount
                .findCurrentUserAccount()
                .filter(account -> account.getUsername().equals(owner) || account.getRole().equals("ADMIN"))
                .isPresent();
    }

}
