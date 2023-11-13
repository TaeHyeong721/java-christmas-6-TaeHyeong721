package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Category;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public enum Event {
    CHRISTMAS_D_DAY_DISCOUNT(Event::calculateChristmasEvent, "크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT(Event::calculateWeekdayEvent, "평일 할인"),
    WEEKEND_DISCOUNT(Event::calculateWeekendEvent, "주말 할인"),
    SPECIAL_DISCOUNT(customer -> calculateSpecialEvent(), "특별 할인"),
    GIFT_EVENT(customer -> 0, "증정 이벤트");

    private final Function<Customer, Integer> expression;
    private final String name;

    Event(Function<Customer, Integer> expression, String name) {
        this.expression = expression;
        this.name = name;
    }

    public static List<Event> from(Customer customer) {
        if (isNotEligibleForEvent(customer)) {
            return Collections.emptyList();
        }

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

    private static boolean isNotEligibleForEvent(Customer customer) {
        return customer.getOrderAmount() < EventConstants.MINIMUM_ORDER_AMOUNT_FOR_EVENT.getValue();
    }

    private static boolean isGiftEvent(Customer customer) {
        return customer.getOrderAmount() >= EventConstants.MINIMUM_AMOUNT_FOR_GIFT_EVENT.getValue();
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

    public int calculateDiscount(Customer customer) {
        return expression.apply(customer);
    }

    private static int calculateChristmasEvent(Customer customer) {
        int firstDayOfMonth = 1;
        int discountStartAmount = EventConstants.CHRISTMAS_DISCOUNT_START_AMOUNT.getValue();
        int discountIncreaseUnit = EventConstants.CHRISTMAS_DISCOUNT_INCREASE_UNIT.getValue();

        return discountStartAmount + (customer.getVisitDate() - firstDayOfMonth) * discountIncreaseUnit;
    }

    private static int calculateWeekdayEvent(Customer customer) {
        int dessertCount = customer.getTotalMenuQuantityByCategory(Category.DESSERT);
        return dessertCount * EventConstants.WEEKDAY_DISCOUNT_AMOUNT.getValue();
    }

    private static int calculateWeekendEvent(Customer customer) {
        int mainCount = customer.getTotalMenuQuantityByCategory(Category.MAIN_COURSE);
        return mainCount * EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue();
    }

    private static int calculateSpecialEvent() {
        return EventConstants.SPECIAL_DISCOUNT_AMOUNT.getValue();
    }

    public String getName() {
        return name;
    }
}
