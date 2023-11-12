package christmas.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventBadge;
import christmas.domain.restaurant.Menu;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventServiceTest {

    private static List<Menu> defaultMenus;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        this.eventService = new EventService();
    }

    @BeforeAll
    public static void beforeAll() {
        defaultMenus = List.of(
                Menu.MUSHROOM_SOUP,
                Menu.T_BONE_STEAK,
                Menu.BBQ_RIBS,
                Menu.SEAFOOD_PASTA,
                Menu.CHOCOLATE_CAKE,
                Menu.ZERO_COLA
        );
    }

    @Test
    void 증정_이벤트_적용시_증정품을_제공한다() {
        //given
        Customer customer = Customer.reserveVisit(3, defaultMenus);
        Set<Menu> allMenus = EnumSet.allOf(Menu.class);

        //when
        List<Event> events = eventService.findEventsByCustomer(customer);
        Map<Menu, Integer> gift = eventService.getGiftMenu(customer);

        //then
        assertThat(events).contains(Event.GIFT_EVENT);
        assertThat(gift.keySet()).allMatch(allMenus::contains);
        assertThat(gift.values()).allMatch(count -> count > 0);
    }

    @Test
    void 증정_이벤트_미적용시_증정품은_없다() {
        //given
        List<Menu> menus = List.of(Menu.T_BONE_STEAK);
        Customer customer = Customer.reserveVisit(3, menus);

        //when
        List<Event> events = eventService.findEventsByCustomer(customer);
        Map<Menu, Integer> gift = eventService.getGiftMenu(customer);

        //then
        assertThat(events).doesNotContain(Event.GIFT_EVENT);
        assertThat(gift).isEqualTo(Collections.emptyMap());
    }

    @Test
    void 고객정보를_받으면_혜택_내역을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);
        List<Event> events = eventService.findEventsByCustomer(customer);
        Map<Menu, Integer> giftMenu = eventService.getGiftMenu(customer);
        Map<Event, Integer> expectedBenefitDetails = getExpectedBenefitDetails(events, customer, giftMenu);

        //when
        Map<Event, Integer> benefitDetails = eventService.getBenefitDetails(customer);

        //then
        assertThat(benefitDetails).isEqualTo(expectedBenefitDetails);
    }

    private Map<Event, Integer> getExpectedBenefitDetails(
            List<Event> events,
            Customer customer,
            Map<Menu, Integer> giftMenu
    ) {
        Map<Event, Integer> expectedBenefitDetails = new HashMap<>();
        for (Event event : events) {
            expectedBenefitDetails.put(event, event.calculate(customer));
        }
        if (events.contains(Event.GIFT_EVENT)) {
            expectedBenefitDetails.put(Event.GIFT_EVENT, getGiftAmount(giftMenu));
        }
        return expectedBenefitDetails;
    }

    @Test
    void 고객정보를_받으면_총혜택_금액을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);
        List<Event> events = eventService.findEventsByCustomer(customer);
        int expectedDiscountAmount = discountAmount(events, customer);
        int expectedGiftAmount = getGiftAmount(eventService.getGiftMenu(customer));

        //when
        int benefitAmount = eventService.getBenefitAmount(customer);

        //then
        assertThat(benefitAmount).isEqualTo(expectedDiscountAmount + expectedGiftAmount);
    }

    private int discountAmount(List<Event> events, Customer customer) {
        return events.stream()
                .mapToInt(event -> event.calculate(customer))
                .sum();
    }

    private int getGiftAmount(Map<Menu, Integer> giftMenu) {
        return giftMenu.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    @Test
    void 고객정보를_받으면_할인_후_예상_결제_금액을_계산한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);
        List<Event> events = eventService.findEventsByCustomer(customer);
        int orderAmount = customer.getOrderAmount();
        int discountAmount = discountAmount(events, customer);

        //when
        int paymentAmount = eventService.calculatePaymentAmount(customer);

        //then
        assertThat(paymentAmount).isEqualTo(orderAmount - discountAmount);
    }

    @Test
    void 총혜택_금액에_따라_이벤트_배지를_부여한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);

        //when
        EventBadge badge = eventService.getBadgeForBenefitAmount(customer);

        //then
        assertThat(badge).isEqualTo(EventBadge.SANTA);
    }
}