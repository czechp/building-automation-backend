package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseDelete;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/switch-devices")
@CrossOrigin("*")
@AllArgsConstructor
class SwitchDeviceRestAdapterDelete {
    private final SwitchDeviceUseCaseDelete switchDeviceUseCaseDelete;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSwitchDevice(@PathVariable(name = "id") long switchDeviceId) {
        switchDeviceUseCaseDelete.deleteSwitchDevice(switchDeviceId);
    }
}
