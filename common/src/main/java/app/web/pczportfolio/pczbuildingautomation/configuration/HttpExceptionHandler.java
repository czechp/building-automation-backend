package app.web.pczportfolio.pczbuildingautomation.configuration;

import app.web.pczportfolio.pczbuildingautomation.exception.ConditionsNotFulFiledException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotEnoughPrivilegesException;
import app.web.pczportfolio.pczbuildingautomation.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler({ConditionsNotFulFiledException.class})
    ResponseEntity<HashMap<String, String>> conditionsNotFulFilledHandler(Exception exception) {
        return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    ResponseEntity<HashMap<String, String>> notFoundHandler(Exception exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotEnoughPrivilegesException.class})
    ResponseEntity<HashMap<String, String>> notEnoughPrivilegesHandler(Exception exception) {
        return buildResponse(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    ResponseEntity<HashMap<String, String>> constraintViolationException(Exception exception) {
        final var exceptionMessage = exception.getMessage();
        final var responseMessage = exceptionMessage
                .substring(exceptionMessage.indexOf(": ") + 2);

        return buildResponse(responseMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<HashMap<String, String>> buildResponse(String message, HttpStatus httpStatus) {
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity
                .status(httpStatus)
                .body(responseBody);
    }
}
