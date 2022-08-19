package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.dto.DeviceEventQueryDto;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query.DeviceEventQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-events")
@CrossOrigin("*")
@AllArgsConstructor
class DeviceEventRestAdapterQuery {
    private final DeviceEventQuery deviceEventQuery;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<DeviceEventQueryDto> findAll(Pageable pageable) {
        return deviceEventQuery.findAllDeviceEvents(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    DeviceEventQueryDto findEventDeviceById(@PathVariable(name = "id") long deviceEventId) {
        return deviceEventQuery.findById(deviceEventId);
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    List<DeviceEventQueryDto> findDeviceEvents(Pageable pageable) {
        return deviceEventQuery.findByCurrentUser(pageable);
    }

    @GetMapping("/device/{deviceId}")
    List<DeviceEventQueryDto> findDeviceEventByDeviceId(@PathVariable(name = "deviceId") long deviceId) {
        return deviceEventQuery.findByDeviceId(deviceId);
    }

}
