package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventSetNewStateRequest;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateMsgDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortChannelSendMsg;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseSetNewState;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseSetNewStateImpl implements SwitchDeviceUseCaseSetNewState {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    private final SwitchDevicePortChannelSendMsg switchDevicePortChannelSendMsg;
    private final SwitchDevicePortSave switchDevicePortSave;

    @Override
    @DeviceEventSetNewStateRequest
    @Transactional
    public SwitchDevice setNewStateForSwitchDevice(SwitchDeviceSetNewStateDto switchDeviceSetNewStateDto) throws JsonProcessingException {
        final var switchDevice = getSwitchDeviceOrThrowException(switchDeviceSetNewStateDto.getSwitchDeviceId());
        switchDeviceOwnerValidator.currentUserIsOwnerOrElseThrowException(switchDevice);

        switchDevice.setNewState(switchDeviceSetNewStateDto);
        switchDevicePortSave.saveSwitchDevice(switchDevice);

        final var message = new SwitchDeviceSetNewStateMsgDto(switchDevice);
        switchDevicePortChannelSendMsg.sendMsgToDeviceChannel(switchDevice, message);

        return switchDevice;
    }

    private SwitchDevice getSwitchDeviceOrThrowException(long switchDeviceId) {
        return switchDevicePortFindById.findSwitchDeviceById(switchDeviceId)
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceId + " does not exist"));
    }
}
