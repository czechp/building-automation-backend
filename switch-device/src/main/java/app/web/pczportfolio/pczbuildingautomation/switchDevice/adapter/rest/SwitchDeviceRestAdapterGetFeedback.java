package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceFeedbackDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.useCase.SwitchDeviceUseCaseGetFeedback;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/switch-devices/feedback")
@CrossOrigin("*")
@AllArgsConstructor
class SwitchDeviceRestAdapterGetFeedback {

    private final SwitchDeviceUseCaseGetFeedback switchDeviceUseCaseGetFeedback;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void receiveFeedbackFromSwitchDevice(@RequestBody @Valid SwitchDeviceFeedbackDto switchDeviceFeedbackDto) {
        switchDeviceUseCaseGetFeedback.receiveFeedbackFromDevice(switchDeviceFeedbackDto);
    }
}
