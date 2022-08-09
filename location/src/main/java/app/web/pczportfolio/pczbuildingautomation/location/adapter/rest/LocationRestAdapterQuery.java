package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationQueryDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.query.LocationQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
@CrossOrigin("*")
class LocationRestAdapterQuery {
    private final LocationQuery locationQuery;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    List<LocationQueryDto> findAllLocations(Pageable pageable) {
        return locationQuery.findLocationsAll(pageable);
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    List<LocationQueryDto> findALocationsByCurrentUser(Pageable pageable) {
        return locationQuery.findLocationsByCurrentUser(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocationQueryDto findLocationById(
            @PathVariable(name = "id") long locationId
    ) {
        return locationQuery.findLocationById(locationId);
    }
}
