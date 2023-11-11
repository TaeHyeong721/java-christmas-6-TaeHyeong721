package christmas.domain.service;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventPlanner;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Restaurant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventService {

    private final EventPlanner eventPlanner;
    private final Restaurant restaurant;

    public EventService() {
        this.eventPlanner = new EventPlanner();
        this.restaurant = new Restaurant();
    }

    public List<Event> findEventByCustomer(Customer customer) {
        return eventPlanner.findEventsByCustomer(customer);
    }

    public Map<Menu, Integer> getGiftMenu(Customer customer) {
        List<Event> events = eventPlanner.findEventsByCustomer(customer);
        if (events.contains(Event.GIFT_EVENT)) {
            return restaurant.requestGift();
        }
        return Collections.emptyMap();
    }

    public Map<Event, Integer> getBenefitDetails(Customer customer) {
        Map<Event, Integer> benefitDetails = eventPlanner.findEventsByCustomer(customer).stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        event -> calculateBenefitForEvent(event, customer)
                ));

        return Collections.unmodifiableMap(benefitDetails);
    }

    private int calculateBenefitForEvent(Event event, Customer customer) {
        if (Event.GIFT_EVENT == event) {
            return getGiftAmount();
        }
        return event.calculate(customer);
    }

    private int getGiftAmount() {
        Map<Menu, Integer> giftMenu = restaurant.requestGift();
        return giftMenu.keySet().stream()
                .mapToInt(menu -> menu.getPrice() * giftMenu.getOrDefault(menu, 0))
                .sum();
    }
}
