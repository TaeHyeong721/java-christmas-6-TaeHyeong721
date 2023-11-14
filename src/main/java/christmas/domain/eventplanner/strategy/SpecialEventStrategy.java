package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.EventCalender;

public class SpecialEventStrategy implements EventStrategy{

    private static final int DISCOUNT_AMOUNT = 1_000;

    @Override
    public boolean checkEligibility(Customer customer) {
        return EventCalender.hasStar(customer.getVisitDate());
    }

    @Override
    public int calculateDiscount(Customer customer) {
        return DISCOUNT_AMOUNT;
    }
}
