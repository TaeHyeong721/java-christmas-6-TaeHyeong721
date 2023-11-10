package christmas.domain.eventplanner;

public enum ChristmasEventConstants {
    D_DAY(25),
    DISCOUNT_START_AMOUNT(1_000),
    DISCOUNT_INCREASE_UNIT(100);

    private final int value;

    ChristmasEventConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
