package christmas.domain.eventplanner;

public enum EventConstants {
    CHRISTMAS_DATE(25),
    CHRISTMAS_DISCOUNT_START_AMOUNT(1_000),
    CHRISTMAS_DISCOUNT_INCREASE_UNIT(100),
    WEEKDAY_DISCOUNT_AMOUNT(2_023),
    WEEKEND_DISCOUNT_AMOUNT(2_023),
    SPECIAL_DISCOUNT_AMOUNT(1_000),
    MINIMUM_ORDER_AMOUNT_FOR_EVENT(10_000),
    MINIMUM_AMOUNT_FOR_GIFT_EVENT(120_000);

    private final int value;

    EventConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
