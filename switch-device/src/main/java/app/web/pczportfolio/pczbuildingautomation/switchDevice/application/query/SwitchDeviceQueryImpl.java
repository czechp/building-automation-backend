package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.query;

import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceQueryDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindAll;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindByLocationIdAndOwner;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindByOwner;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service.SwitchDeviceOwnerValidator;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class SwitchDeviceQueryImpl implements SwitchDeviceQuery {
    private final SwitchDevicePortFindAll switchDevicePortFindAll;
    private final SwitchDevicePortFindByOwner switchDevicePortFindByOwner;
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SecurityCurrentUser securityCurrentUser;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    private final SwitchDevicePortFindByLocationIdAndOwner switchDevicePortFindByLocationIdAndOwner;

    @Override
    public List<SwitchDeviceQueryDto> findAllSwitchDevices(Pageable pageable) {
        return switchDevicePortFindAll.findAllSwitchDevices(pageable)
                .stream()
                .map(SwitchDeviceQueryDto::toQueryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SwitchDeviceQueryDto> findAllSwitchDevicesByCurrentUser(Pageable pageable) {
        final var currentUser = securityCurrentUser.getCurrentUser();
        return switchDevicePortFindByOwner.findSwitchDevicesByOwner(currentUser, pageable)
                .stream()
                .map(SwitchDeviceQueryDto::toQueryDto)
                .collect(Collectors.toList());
    }

    @Override
    public SwitchDeviceQueryDto findSwitchDeviceById(long switchDeviceId) {
        SwitchDevice switchDevice = switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceId + " does not exist"));
        if (switchDeviceOwnerValidator.currentUserIsOwner(switchDevice))
            return SwitchDeviceQueryDto.toQueryDto(switchDevice);
        else throw new NotEnoughPrivilegesException("You are not owner of switch device with id: " + switchDeviceId);
    }

    @Override
    public List<SwitchDeviceQueryDto> findByLocationIdAndCurrentUser(long locationId, Pageable pageable) {
        final var currentUser = securityCurrentUser.getCurrentUser();
        return switchDevicePortFindByLocationIdAndOwner.findSwitchDevicesByLocationIdAndOwner(locationId, currentUser, pageable)
                .stream()
                .map(SwitchDeviceQueryDto::toQueryDto)
                .collect(Collectors.toList());
    }
}
