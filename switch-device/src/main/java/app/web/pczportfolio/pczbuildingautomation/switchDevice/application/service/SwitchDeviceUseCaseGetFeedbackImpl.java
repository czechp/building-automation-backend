package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindById;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortSave;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseGetFeedback;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class SwitchDeviceUseCaseGetFeedbackImpl implements SwitchDeviceUseCaseGetFeedback {
    private final SwitchDevicePortFindById switchDevicePortFindById;
    private final SwitchDeviceOwnerValidator switchDeviceOwnerValidator;
    private final SwitchDevicePortSave switchDevicePortSave;

    @Transactional
    @Override
    public SwitchDevice receiveFeedbackFromDevice(SwitchDeviceFeedbackDto switchDeviceFeedbackDto) {
        final var switchDeviceToUpdate = findSwitchDeviceOrThrowException(switchDeviceFeedbackDto);
        switchDeviceOwnerValidator.currentUserIsOwnerOrElseThrowException(switchDeviceToUpdate);
        switchDeviceToUpdate.receiveFeedback(switchDeviceFeedbackDto);
        switchDevicePortSave.saveSwitchDevice(switchDeviceToUpdate);
        return switchDeviceToUpdate;
    }

    private SwitchDevice findSwitchDeviceOrThrowException(SwitchDeviceFeedbackDto switchDeviceFeedbackDto) {
        return switchDevicePortFindById.findSwitchDeviceById(switchDeviceFeedbackDto.getId())
                .orElseThrow(() -> new NotFoundException("Switch device with id: " + switchDeviceFeedbackDto.getId() + " does not exist"));
    }
}
