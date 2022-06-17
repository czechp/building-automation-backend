package app.web.pczportfolio.pczbuildingautomation.exception;

import java.util.function.Supplier;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public static Runnable getRunnable(String message) {
        return () -> {
            throw new BadRequestException(message);
        };
    }
}
