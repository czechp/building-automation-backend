package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceSetNewStateDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseSetNewState;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/switch-devices/new-state")
@CrossOrigin("*")
@AllArgsConstructor
class SwitchDeviceRestAdapterSetNewState {
    private final SwitchDeviceUseCaseSetNewState switchDeviceUseCaseSetNewState;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void setNewStateForSwitchDevice(@RequestBody @NotNull SwitchDeviceSetNewStateDto switchDeviceSetNewStateDto) throws JsonProcessingException {
        switchDeviceUseCaseSetNewState.setNewStateForSwitchDevice(switchDeviceSetNewStateDto);
    }
}
