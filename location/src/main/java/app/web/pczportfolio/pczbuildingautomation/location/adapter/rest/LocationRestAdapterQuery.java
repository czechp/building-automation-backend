package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.query.LocationQuery;
import app.web.pczportfolio.pczbuildingautomation.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
class LocationRestAdapterQuery {
    private final LocationQuery locationQuery;

    @Secured({"ROLE_ADMIN"})
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<LocationQueryDto> findAllLocation() {
        return locationQuery.findLocationsAll();
    }

    @GetMapping("/{id}")
    LocationQueryDto findLocationById(
            @PathVariable(name = "id") long locationId
    ) {
        return locationQuery.findLocationById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id: " + locationId + " does not exist"));
    }

    @GetMapping("/account")
    List<LocationQueryDto> findLocationByCurrentUser() {
        return locationQuery.findLocationsByCurrentUser();
    }

}
