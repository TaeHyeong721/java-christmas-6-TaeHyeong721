package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import java.util.List;

public class EventPlanner {

    public List<Event> findEventsByCustomer(Customer customer) {
        return Event.from(customer);
    }

    public int getDiscountAmount(Customer customer) {
        List<Event> events = findEventsByCustomer(customer);
        return events.stream()
                .mapToInt(event -> event.calculate(customer))
                .sum();
    }
}
