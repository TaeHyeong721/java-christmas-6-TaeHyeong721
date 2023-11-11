package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public enum Event {
    CHRISTMAS_D_DAY_DISCOUNT(Event::calculateChristmasEvent),
    WEEKDAY_DISCOUNT(Event::calculateWeekdayEvent),
    WEEKEND_DISCOUNT(Event::calculateWeekendEvent),
    SPECIAL_DISCOUNT(customer -> calculateSpecialEvent()),
    GIFT_EVENT(customer -> 0);

    private final Function<Customer, Integer> expression;

    Event(Function<Customer, Integer> expression) {
        this.expression = expression;
    }

    public static List<Event> from(Customer customer) {
        List<Event> events = new ArrayList<>();
        int visitDate = customer.getVisitDate();

        if (isChristmasEvent(visitDate)) {
            events.add(CHRISTMAS_D_DAY_DISCOUNT);
        }
        if (isWeekdayEvent(customer)) {
            events.add(WEEKDAY_DISCOUNT);
        }
        if (isWeekendEvent(customer)) {
            events.add(WEEKEND_DISCOUNT);
        }
        if (isSpecialEvent(visitDate)) {
            events.add(SPECIAL_DISCOUNT);
        }
        if (isGiftEvent(customer)) {
            events.add(GIFT_EVENT);
        }

        return events;
    }

    private static boolean isGiftEvent(Customer customer) {
        return customer.getTotalOrderAmount() >= 120_000;
    }

    private static boolean isSpecialEvent(int visitDate) {
        return EventCalender.hasStar(visitDate);
    }

    private static boolean isWeekendEvent(Customer customer) {
        return DayType.WEEKEND == EventCalender.getDayType(customer.getVisitDate())
                && customer.hasMenuByCategory(Category.MAIN_COURSE);
    }

    private static boolean isWeekdayEvent(Customer customer) {
        return DayType.WEEKDAY == EventCalender.getDayType(customer.getVisitDate())
                && customer.hasMenuByCategory(Category.DESSERT);
    }

    private static boolean isChristmasEvent(int visitDate) {
        return visitDate <= EventConstants.CHRISTMAS_DATE.getValue();
    }

    public int calculate(Customer customer) {
        return expression.apply(customer);
    }

    private static int calculateChristmasEvent(Customer customer) {
        int firstDayOfMonth = 1;
        int discountStartAmount = EventConstants.CHRISTMAS_DISCOUNT_START_AMOUNT.getValue();
        int discountIncreaseUnit = EventConstants.CHRISTMAS_DISCOUNT_INCREASE_UNIT.getValue();

        return discountStartAmount + (customer.getVisitDate() - firstDayOfMonth) * discountIncreaseUnit;
    }

    private static int calculateWeekdayEvent(Customer customer) {
        int dessertCount = customer.getCategoryCount(Category.DESSERT);
        return dessertCount * EventConstants.WEEKDAY_DISCOUNT_AMOUNT.getValue();
    }

    private static int calculateWeekendEvent(Customer customer) {
        int mainCount = customer.getCategoryCount(Category.MAIN_COURSE);
        return mainCount * EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue();
    }

    private static int calculateSpecialEvent() {
        return EventConstants.SPECIAL_DISCOUNT_AMOUNT.getValue();
    }
}
