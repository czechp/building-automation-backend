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
class EventDeviceRestAdapterQuery {
    private final DeviceEventQuery deviceEventQuery;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<DeviceEventQueryDto> findAll(Pageable pageable) {
        return deviceEventQuery.findAllDeviceEvents(pageable);
    }

}
