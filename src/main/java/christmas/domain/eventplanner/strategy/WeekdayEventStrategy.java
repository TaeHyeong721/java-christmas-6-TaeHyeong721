package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.DayType;
import christmas.domain.eventplanner.EventCalender;
import christmas.domain.restaurant.Category;
import christmas.domain.restaurant.Gift;

public class WeekdayEventStrategy implements EventStrategy {

    private static final int DISCOUNT_AMOUNT_PER_QUANTITY = 2_023;

    @Override
    public boolean checkEligibility(Customer customer) {
        return DayType.WEEKDAY == EventCalender.getDayType(customer.getVisitDate())
                && customer.hasMenuByCategory(Category.DESSERT);
    }

    @Override
    public int calculateDiscount(Customer customer) {
        int dessertQuantity = customer.getTotalMenuQuantityByCategory(Category.DESSERT);
        return dessertQuantity * DISCOUNT_AMOUNT_PER_QUANTITY;
    }

    @Override
    public int calculateBenefit(Customer customer) {
        return calculateDiscount(customer);
    }

    @Override
    public Gift getGift() {
        return Gift.empty();
    }
}
