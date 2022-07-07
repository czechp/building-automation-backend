package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.spring;

import app.web.pczportfolio.pczbuildingautomation.location.event.LocationDeleteEvent;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseDelete;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SwitchDeviceEventConsumerAdapter implements ApplicationListener<LocationDeleteEvent> {
    private final SwitchDeviceUseCaseDelete switchDeviceUseCaseDelete;

    @Override
    public void onApplicationEvent(LocationDeleteEvent locationDeleteEvent) {
        switchDeviceUseCaseDelete.deleteSwitchDevicesLocationRemoved(locationDeleteEvent.getId());
    }
}
