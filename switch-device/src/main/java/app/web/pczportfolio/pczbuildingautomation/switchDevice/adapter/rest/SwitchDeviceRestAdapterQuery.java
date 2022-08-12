package app.web.pczportfolio.pczbuildingautomation.switchDevice.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.dto.SwitchDeviceQueryDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.query.SwitchDeviceQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/switch-devices")
@CrossOrigin("*")
@AllArgsConstructor
class SwitchDeviceRestAdapterQuery {
    private final SwitchDeviceQuery switchDeviceQuery;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<SwitchDeviceQueryDto> findAllSwitchDevices(Pageable pageable) {
        return switchDeviceQuery.findAllSwitchDevices(pageable);
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    List<SwitchDeviceQueryDto> findAllSwitchDevicesByCurrentUser(Pageable pageable) {
        return switchDeviceQuery.findAllSwitchDevicesByCurrentUser(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    SwitchDeviceQueryDto findSwitchDeviceBtId(@PathVariable(name = "id") long switchDeviceId) {
        return switchDeviceQuery.findSwitchDeviceById(switchDeviceId);
    }

    @GetMapping("/location/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<SwitchDeviceQueryDto> findByLocationIdAndCurrentUser(
            @PathVariable(name = "id") long locationId,
            Pageable pageable
    ) {
        return switchDeviceQuery.findByLocationIdAndCurrentUser(locationId,pageable);
    }

}
