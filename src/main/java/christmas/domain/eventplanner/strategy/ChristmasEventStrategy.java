package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;

public class ChristmasEventStrategy implements EventStrategy{

    private static final int CHRISTMAS_DATE = 25;
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int DISCOUNT_START_AMOUNT = 1_000;
    private static final int DISCOUNT_INCREASE_UNIT = 100;

    @Override
    public boolean checkEligibility(Customer customer) {
        return customer.getVisitDate() <= CHRISTMAS_DATE;
    }

    @Override
    public int calculateDiscount(Customer customer) {
        return DISCOUNT_START_AMOUNT + (customer.getVisitDate() - FIRST_DAY_OF_MONTH) * DISCOUNT_INCREASE_UNIT;
    }
}
