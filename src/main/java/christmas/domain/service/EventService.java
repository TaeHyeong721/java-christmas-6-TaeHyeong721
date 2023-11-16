package christmas.domain.service;

import christmas.domain.customer.Customer;
import christmas.domain.customer.VisitDate;
import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventBadge;
import christmas.domain.restaurant.Gift;
import christmas.domain.restaurant.Orders;
import christmas.dto.EventPreviewDto;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventService {

    public List<Event> findEventsByCustomer(Customer customer) {
        return Event.from(customer);
    }

    public Gift gatherEligibleEventGifts(Customer customer) {
        List<Event> events = findEventsByCustomer(customer);

        return events.stream()
                .filter(Event::hasGift)
                .map(Event::getGift)
                .reduce(Gift.empty(), Gift::merge);
    }

    public Map<Event, Integer> getBenefitDetails(Customer customer) {
        Map<Event, Integer> benefitDetails = new HashMap<>();

        List<Event> events = findEventsByCustomer(customer);
        for (Event event : events) {
            benefitDetails.put(event, event.calculateBenefit(customer));
        }

        return Collections.unmodifiableMap(benefitDetails);
    }

    public EventBadge getBadgeForBenefitAmount(Customer customer) {
        int benefitAmount = getBenefitAmount(customer);
        return EventBadge.fromBenefitAmount(benefitAmount);
    }

    private int getBenefitAmount(Customer customer) {
        List<Event> events = findEventsByCustomer(customer);

        return events.stream()
                .mapToInt(event -> event.calculateBenefit(customer))
                .sum();
    }

    public EventPreviewDto getEventPreviewDto(VisitDate visitDate, Orders orders) {
        Customer customer = Customer.reserveVisit(visitDate, orders);
        Gift gift = gatherEligibleEventGifts(customer);
        Map<Event, Integer> benefitDetails = getBenefitDetails(customer);
        List<Event> events = findEventsByCustomer(customer);
        int discountAmount = getDiscountAmount(events, customer);
        EventBadge badge = getBadgeForBenefitAmount(customer);

        return new EventPreviewDto(
                orders,
                gift,
                benefitDetails,
                discountAmount,
                badge
        );
    }

    private int getDiscountAmount(List<Event> events, Customer customer) {
        return events.stream()
                .mapToInt(event -> event.calculateDiscount(customer))
                .sum();
    }
}
