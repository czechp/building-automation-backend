package app.web.pczportfolio.pczbuildingautomation.configuration;

import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiled;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler({ConditionsNotFulFiled.class})
    ResponseEntity<HashMap<String, String>> conditionsNotFulFilledHandler(Exception exception) {
        return buildResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    ResponseEntity<HashMap<String, String>> notFoundHandler(Exception exception) {
        return buildResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<HashMap<String, String>> buildResponse(Exception exception, HttpStatus httpStatus) {
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("message", exception.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity
                .status(httpStatus)
                .body(responseBody);
    }
}
