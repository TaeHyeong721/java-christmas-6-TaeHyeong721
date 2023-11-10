package christmas.domain.eventplanner;

public class EventPlanner {

    public Event findEventsByDate(int visitDate) {
        if (visitDate <= ChristmasEventConstants.D_DAY.getValue()) {
            return Event.CHRISTMAS_D_DAY_DISCOUNT;
        }
        return Event.NONE;
    }

    public int getDiscountAmount(Event event, int visitDate) {
        if (event == Event.CHRISTMAS_D_DAY_DISCOUNT) {
            return calculateChristmasDDayDiscount(visitDate);
        }
        return 0;
    }

    private int calculateChristmasDDayDiscount(int visitDate) {
        int firstDayOfMonth = 1;
        int discountStartAmount = ChristmasEventConstants.DISCOUNT_START_AMOUNT.getValue();
        int discountIncreaseUnit = ChristmasEventConstants.DISCOUNT_INCREASE_UNIT.getValue();

        return discountStartAmount + (visitDate - firstDayOfMonth) * discountIncreaseUnit;
    }
}
