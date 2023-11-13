package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Gift;

public interface EventStrategy {

    boolean checkEligibility(Customer customer);

    int calculateDiscount(Customer customer);

    int calculateBenefit(Customer customer);

    Gift getGift();
}
