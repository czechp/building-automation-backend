package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceCreateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseCreate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/switch-devices")
@CrossOrigin("*")
@AllArgsConstructor
class SwitchDeviceRestAdapterCreate {
    private final SwitchDeviceUseCaseCreate switchDeviceUseCaseCreate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createSwitchDevice(@RequestBody @Valid SwitchDeviceCreateDto switchDeviceCreateDto) {
        switchDeviceUseCaseCreate.createSwitchDevice(switchDeviceCreateDto);
    }
}
