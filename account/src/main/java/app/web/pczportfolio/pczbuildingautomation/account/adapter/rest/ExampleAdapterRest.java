package app.web.pczportfolio.pczbuildingautomation.account.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
class ExampleAdapterRest {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    void test() {

    }
}
