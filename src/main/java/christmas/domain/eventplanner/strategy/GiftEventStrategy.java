package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Gift;

public class GiftEventStrategy implements EventStrategy{

    private static final int MINIMUM_AMOUNT = 120_000;
    private static final int DISCOUNT_AMOUNT_ZERO = 0;

    private final Gift gift;

    public GiftEventStrategy() {
        this.gift = Gift.asGiveaway();
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        return customer.getTotalOrderAmount() >= MINIMUM_AMOUNT;
    }

    @Override
    public int calculateDiscount(Customer customer) {
        return DISCOUNT_AMOUNT_ZERO;
    }

    @Override
    public int calculateBenefit(Customer customer) {
        return gift.getTotalAmount();
    }

    @Override
    public Gift getGift() {
        return gift;
    }
}
