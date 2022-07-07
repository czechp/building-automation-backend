package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.location.application.dto.LocationCreateCommandDto;
import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseCreate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
class LocationRestAdapterCreate {
    private final LocationUseCaseCreate locationUseCaseCreate;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void createLocation(
            @RequestBody @Valid LocationCreateCommandDto locationCreateCommandDto
    ) {
        locationUseCaseCreate.createLocation(locationCreateCommandDto);
    }
}
