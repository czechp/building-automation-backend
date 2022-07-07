package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.spring;


import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindAll;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCheckError;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class SwitchDeviceCronAdapterCheckError {
    private final SwitchDevicePortFindAll switchDevicePortFindAll;
    private final SwitchDeviceUseCaseCheckError switchDeviceUseCaseCheckError;

    public SwitchDeviceCronAdapterCheckError(SwitchDevicePortFindAll switchDevicePortFindAll, SwitchDeviceUseCaseCheckError switchDeviceUseCaseCheckError) {
        this.switchDevicePortFindAll = switchDevicePortFindAll;
        this.switchDeviceUseCaseCheckError = switchDeviceUseCaseCheckError;
    }

    @Scheduled(fixedDelay = 30_000)
    @Transactional
    public void checkErrorsForAllDevices() {
        switchDevicePortFindAll.findAllSwitchDevices(Pageable.unpaged())
                .stream()
                .map(SwitchDevice::getId)
                .forEach(switchDeviceUseCaseCheckError::checkSwitchDeviceError);
    }
}

