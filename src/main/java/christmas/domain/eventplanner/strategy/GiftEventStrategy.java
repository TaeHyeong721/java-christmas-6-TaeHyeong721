package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;

public class GiftEventStrategy implements EventStrategy{

    private static final int MINIMUM_AMOUNT = 120_000;
    private static final int DISCOUNT_AMOUNT_ZERO = 0;

    @Override
    public boolean checkEligibility(Customer customer) {
        return customer.getOrderAmount() >= MINIMUM_AMOUNT;
    }

    @Override
    public int calculateDiscount(Customer customer) {
        return DISCOUNT_AMOUNT_ZERO;
    }
}
