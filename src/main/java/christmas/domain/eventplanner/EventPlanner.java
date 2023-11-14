package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Gift;
import java.util.List;

public class EventPlanner {

    public List<Event> findEventsByCustomer(Customer customer) {
        return Event.from(customer);
    }

    public Gift getGift(Customer customer) {
        List<Event> events = findEventsByCustomer(customer);

        return events.stream()
                .map(Event::getGift)
                .filter(gift -> !gift.isEmpty())
                .reduce(Gift.empty(), Gift::merge);
    }
}
