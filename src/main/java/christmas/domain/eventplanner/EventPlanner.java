package christmas.domain.eventplanner;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Restaurant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventPlanner {

    private final Restaurant restaurant;

    public EventPlanner() {
        this.restaurant = new Restaurant();
    }

    public List<Event> findEventsByCustomer(Customer customer) {
        return Event.from(customer);
    }

    public Map<Menu, Integer> getGiftMenu(Customer customer) {
        List<Event> events = Event.from(customer);
        if (events.contains(Event.GIFT_EVENT)) {
            return restaurant.requestGiftMenu();
        }
        return Collections.emptyMap();
    }

    public int getDiscountAmount(Customer customer) {
        List<Event> events = findEventsByCustomer(customer);
        return events.stream()
                .mapToInt(event -> event.calculate(customer))
                .sum();
    }
}
