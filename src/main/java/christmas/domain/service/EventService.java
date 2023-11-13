package christmas.domain.service;

import christmas.domain.customer.Customer;
import christmas.domain.customer.VisitDate;
import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventBadge;
import christmas.domain.eventplanner.EventPlanner;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Orders;
import christmas.domain.restaurant.Restaurant;
import christmas.dto.EventPreviewDto;
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

    private int getBenefitAmount(Customer customer) {
        List<Event> events = eventPlanner.findEventsByCustomer(customer);
        int benefitAmount = getDiscountAmount(events, customer);

        if (events.contains(Event.GIFT_EVENT)) {
            benefitAmount += getGiftAmount();
        }

        return benefitAmount;
    }

    public EventBadge getBadgeForBenefitAmount(Customer customer) {
        int benefitAmount = getBenefitAmount(customer);
        return EventBadge.fromBenefitAmount(benefitAmount);
    }

    public EventPreviewDto getEventPreviewDto(VisitDate visitDate, Orders orders) {
        Customer customer = Customer.reserveVisit(visitDate, orders);
        Map<Menu, Integer> giftMenu = getGiftMenu(customer);
        Map<Event, Integer> benefitDetails = getBenefitDetails(customer);
        List<Event> events = eventPlanner.findEventsByCustomer(customer);
        int discountAmount = getDiscountAmount(events, customer);
        EventBadge badge = getBadgeForBenefitAmount(customer);

        return new EventPreviewDto(
                orders,
                giftMenu,
                benefitDetails,
                discountAmount,
                badge
        );
    }

    private int getDiscountAmount(List<Event> events, Customer customer) {
        return events.stream()
                .mapToInt(event -> event.calculate(customer))
                .sum();
    }
}
