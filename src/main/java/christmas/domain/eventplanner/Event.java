package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.strategy.ChristmasEventStrategy;
import christmas.domain.eventplanner.strategy.EventStrategy;
import christmas.domain.eventplanner.strategy.GiftEventStrategy;
import christmas.domain.eventplanner.strategy.SpecialEventStrategy;
import christmas.domain.eventplanner.strategy.WeekdayEventStrategy;
import christmas.domain.eventplanner.strategy.WeekendEventStrategy;
import christmas.domain.restaurant.Gift;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Event {
    CHRISTMAS_D_DAY_DISCOUNT(new ChristmasEventStrategy(), "크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT(new WeekdayEventStrategy(), "평일 할인"),
    WEEKEND_DISCOUNT(new WeekendEventStrategy(), "주말 할인"),
    SPECIAL_DISCOUNT(new SpecialEventStrategy(), "특별 할인"),
    GIFT_EVENT(new GiftEventStrategy(), "증정 이벤트");

    private final EventStrategy eventStrategy;
    private final String name;

    Event(EventStrategy eventStrategy, String name) {
        this.eventStrategy = eventStrategy;
        this.name = name;
    }

    public static List<Event> from(Customer customer) {
        if (isNotEligibleForEvent(customer)) {
            return Collections.emptyList();
        }

        return Arrays.stream(Event.values())
                .filter(event -> event.appliesToCustomer(customer))
                .toList();
    }

    private static boolean isNotEligibleForEvent(Customer customer) {
        return customer.getTotalOrderAmount() < EventConstants.MINIMUM_ORDER_AMOUNT_FOR_EVENT.getValue();
    }

    private boolean appliesToCustomer(Customer customer) {
        return eventStrategy.checkEligibility(customer);
    }

    public int calculateDiscount(Customer customer) {
        return eventStrategy.calculateDiscount(customer);
    }

    public int calculateBenefit(Customer customer) {
        return eventStrategy.calculateBenefit(customer);
    }

    public Gift getGift() {
        return eventStrategy.getGift();
    }

    public String getName() {
        return name;
    }
}
