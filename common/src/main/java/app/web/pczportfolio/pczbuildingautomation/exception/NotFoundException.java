package app.web.pczportfolio.pczbuildingautomation.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static Runnable getRunnable(String message) {
        return () -> {
            throw new NotFoundException(message);
        };
    }
}
