package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import java.util.List;

public class EventPlanner {

    public List<Event> findEventsByCustomer(Customer customer) {
        return Event.from(customer);
    }
}
