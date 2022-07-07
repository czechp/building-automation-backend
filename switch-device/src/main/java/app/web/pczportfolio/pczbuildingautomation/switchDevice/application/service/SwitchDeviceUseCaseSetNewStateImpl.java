package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelSendMsg;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseSetNewState;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseSetNewStateImpl implements SwitchDeviceUseCaseSetNewState {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    private final SwitchDevicePortChannelSendMsg switchDevicePortChannelSendMsg;
    private final SwitchDevicePortSave switchDevicePortSave;

    @Override
    public SwitchDevice setNewStateForSwitchDevice(SwitchDeviceSetNewStateDto switchDeviceSetNewStateDto) {
        return null;
    }
}
