package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;


import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseDelete;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
class LocationRestAdapterDelete {
    private LocationUseCaseDelete locationUseCaseDelete;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLocation(@PathVariable(name = "id") long locationId) {
        locationUseCaseDelete.deleteLocationById(locationId);
    }
}
