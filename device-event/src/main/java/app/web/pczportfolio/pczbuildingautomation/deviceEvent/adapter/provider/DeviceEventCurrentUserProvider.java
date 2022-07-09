package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.provider;

import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.port.DeviceEventPortFindCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class DeviceEventCurrentUserProvider implements DeviceEventPortFindCurrentUser {
    private final SecurityCurrentUser securityCurrentUser;

    @Override
    public String findCurrentUser() {
        return securityCurrentUser.getCurrentUser();
    }
}
