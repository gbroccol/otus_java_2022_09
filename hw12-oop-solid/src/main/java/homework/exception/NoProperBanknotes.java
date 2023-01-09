package homework.exception;

public class NoProperBanknotes extends RuntimeException {

    private final static String message = "Нет подходящих банкнот для выдачи денег";

    public NoProperBanknotes() {
        super(message);
    }
}
