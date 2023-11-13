package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Gift;
import java.util.List;

public class EventPlanner {

    private final Gift gift;

    public EventPlanner() {
        this.gift = Gift.asGiveaway();
    }

    public List<Event> findEventsByCustomer(Customer customer) {
        return Event.from(customer);
    }

    public Gift requestGift() {
        return gift;
    }
}
