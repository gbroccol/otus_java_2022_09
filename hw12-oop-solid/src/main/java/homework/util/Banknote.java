package homework.util;

public enum Banknote implements Comparable<Banknote> {

    FIVE_THOUSANDS(5000),
    TWO_THOUSANDS(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDREDS(500),
    TWO_HUNDREDS(200),
    ONE_HUNDRED(100),
    FIFTY(50),
    TEN(10);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
