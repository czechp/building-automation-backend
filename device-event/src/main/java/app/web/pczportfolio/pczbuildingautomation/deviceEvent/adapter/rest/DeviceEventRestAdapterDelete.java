package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseDelete;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device-events")
@CrossOrigin("*")
@AllArgsConstructor
class DeviceEventRestAdapterDelete {
    private final DeviceEventUseCaseDelete deviceEventUseCaseDelete;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    void deleteDeviceEvent(
            @PathVariable(name = "id") long deviceEventId
    ) {
        deviceEventUseCaseDelete.deleteDeviceEvent(deviceEventId);
    }
}
