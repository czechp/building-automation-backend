package app.web.pczportfolio.pczbuildingautomation.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
