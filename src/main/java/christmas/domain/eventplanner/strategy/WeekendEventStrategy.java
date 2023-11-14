package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.DayType;
import christmas.domain.eventplanner.EventCalender;
import christmas.domain.restaurant.Category;

public class WeekendEventStrategy implements EventStrategy {

    private static final int DISCOUNT_AMOUNT_PER_QUANTITY = 2_023;

    @Override
    public boolean checkEligibility(Customer customer) {
        return DayType.WEEKEND == EventCalender.getDayType(customer.getVisitDate())
                && customer.hasMenuByCategory(Category.MAIN_COURSE);
    }

    @Override
    public int calculateDiscount(Customer customer) {
        int mainQuantity = customer.getTotalMenuQuantityByCategory(Category.MAIN_COURSE);
        return mainQuantity * DISCOUNT_AMOUNT_PER_QUANTITY;
    }
}
