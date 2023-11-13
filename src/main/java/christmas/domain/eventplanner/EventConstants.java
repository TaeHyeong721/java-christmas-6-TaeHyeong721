package christmas.domain.eventplanner;

public enum EventConstants {
    MINIMUM_ORDER_AMOUNT_FOR_EVENT(10_000);

    private final int value;

    EventConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
