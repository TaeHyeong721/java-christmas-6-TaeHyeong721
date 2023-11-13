package christmas.domain.eventplanner.strategy;

import christmas.domain.customer.Customer;

public interface EventStrategy {

    boolean checkEligibility(Customer customer);
    int calculateDiscount(Customer customer);
}
