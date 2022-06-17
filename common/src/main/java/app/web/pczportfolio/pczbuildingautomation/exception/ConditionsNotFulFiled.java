package app.web.pczportfolio.pczbuildingautomation.exception;

public class ConditionsNotFulFiled extends RuntimeException {
    public ConditionsNotFulFiled(String message) {
        super(message);
    }

    public static Runnable getRunnable(String message) {
        return () -> {
            throw new ConditionsNotFulFiled(message);
        };
    }
}
