package app.web.pczportfolio.pczbuildingautomation.exception;

public class ConditionsNotFulFiledException extends RuntimeException {
    public ConditionsNotFulFiledException(String message) {
        super(message);
    }

    public static Runnable getRunnable(String message) {
        return () -> {
            throw new ConditionsNotFulFiledException(message);
        };
    }
}
