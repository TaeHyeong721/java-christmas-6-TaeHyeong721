package christmas.domain.service;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventPlanner;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Restaurant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Event, Integer> benefitDetails = new HashMap<>();

        List<Event> events = eventPlanner.findEventsByCustomer(customer);
        for (Event event : events) {
            benefitDetails.put(event, event.calculate(customer));
        }

        if (benefitDetails.containsKey(Event.GIFT_EVENT)) {
            benefitDetails.put(Event.GIFT_EVENT, getGiftAmount());
        }

        return Collections.unmodifiableMap(benefitDetails);
    }

    private int getGiftAmount() {
        return restaurant.getGiftAmount();
    }

    public int getBenefitAmount(Customer customer) {
        List<Event> events = eventPlanner.findEventsByCustomer(customer);

        int benefitAmount = events.stream()
                .mapToInt(event -> event.calculate(customer))
                .sum();

        if (events.contains(Event.GIFT_EVENT)) {
            benefitAmount += getGiftAmount();
        }

        return benefitAmount;
    }
}
