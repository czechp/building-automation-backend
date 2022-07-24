package app.web.pczportfolio.pczbuildingautomation.location.adapter.rest;

import app.web.pczportfolio.pczbuildingautomation.location.application.useCase.LocationUseCaseAssignClient;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations/client")
@CrossOrigin("*")
@AllArgsConstructor
class LocationRestAdapterAssignClient {
    private final LocationUseCaseAssignClient locationUseCaseAssignClient;

    @PatchMapping("/assign/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void assignClientToLocation(
            @PathVariable(name = "id") long locationId,
            @RequestParam(name = "clientName") @Length(min = 3) String clientName,
            @RequestParam(name = "clientUUID") @Length(min = 5) String clientUUID
    ) {
        locationUseCaseAssignClient.assignClient(locationId, clientName, clientUUID);
    }

    @PatchMapping("/clear/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void clearClientInLocation(@PathVariable(name = "id") long locationId) {
        locationUseCaseAssignClient.clearClient(locationId);
    }


}
