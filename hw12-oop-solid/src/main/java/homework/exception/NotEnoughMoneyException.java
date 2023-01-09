package homework.exception;

public class NotEnoughMoneyException extends RuntimeException {

    private final static String message = "Недостаточно средств в банкомате";
    public NotEnoughMoneyException() {
        super(message);
    }
}
