package christmas.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.customer.VisitDate;
import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventBadge;
import christmas.domain.fixture.TestDataFactory;
import christmas.domain.restaurant.Gift;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Order;
import christmas.domain.restaurant.Orders;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventServiceTest {

    private static Orders sampleOrders;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        this.eventService = new EventService();
    }

    @BeforeAll
    public static void beforeAll() {
        sampleOrders = TestDataFactory.createSampleOrders();
    }

    @Test
    void 고객정보를_받으면_적용되는_이벤트_목록을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(24), sampleOrders);

        //when
        List<Event> events = eventService.findEventsByCustomer(customer);

        //then
        assertThat(events).containsExactly(
                Event.CHRISTMAS_D_DAY_DISCOUNT,
                Event.WEEKDAY_DISCOUNT,
                Event.SPECIAL_DISCOUNT,
                Event.GIFT_EVENT
        );
    }

    @Test
    void 증정품을_주는_이벤트가_있으면_증정품을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(3), sampleOrders);
        Gift expectedGift = Gift.asGiveaway();

        //when
        Gift gift = eventService.gatherEligibleEventGifts(customer);

        //then
        assertThat(gift.getItems()).isEqualTo(expectedGift.getItems());
    }

    @Test
    void 증정_이벤트_적용시_증정품을_제공한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(3), sampleOrders);
        Set<Menu> allMenus = EnumSet.allOf(Menu.class);

        //when
        Gift gift = eventService.gatherEligibleEventGifts(customer);
        Map<Menu, Integer> giftItems = gift.getItems();


        //then
        assertThat(giftItems.keySet()).allMatch(allMenus::contains);
        assertThat(giftItems.values()).allMatch(count -> count > 0);
    }

    @Test
    void 증정_이벤트_미적용시_증정품은_없다() {
        //given
        List<Order> orders = List.of(
                new Order(Menu.MUSHROOM_SOUP.getName(), 1),
                new Order(Menu.CHOCOLATE_CAKE.getName(), 1)
        );
        Orders noGiftOrders = new Orders(orders);
        Customer customer = Customer.reserveVisit(new VisitDate(3), noGiftOrders);

        //when
        Gift gift = eventService.gatherEligibleEventGifts(customer);

        //then
        assertThat(gift.isEmpty()).isTrue();
    }

    @Test
    void 고객정보를_받으면_혜택_내역을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(24), sampleOrders);
        Map<Event, Integer> expectedBenefitDetails = createSampleBenefitDetails(customer);

        //when
        Map<Event, Integer> benefitDetails = eventService.getBenefitDetails(customer);

        //then
        assertThat(benefitDetails).isEqualTo(expectedBenefitDetails);
    }

    private Map<Event, Integer> createSampleBenefitDetails(Customer customer) {
        Map<Event, Integer> benefitDetails = new HashMap<>();
        benefitDetails.put(Event.CHRISTMAS_D_DAY_DISCOUNT, Event.CHRISTMAS_D_DAY_DISCOUNT.calculateDiscount(customer));
        benefitDetails.put(Event.WEEKDAY_DISCOUNT, Event.WEEKDAY_DISCOUNT.calculateDiscount(customer));
        benefitDetails.put(Event.SPECIAL_DISCOUNT, Event.SPECIAL_DISCOUNT.calculateDiscount(customer));
        benefitDetails.put(Event.GIFT_EVENT, Event.GIFT_EVENT.getGift().getTotalAmount());

        return benefitDetails;
    }

    @Test
    void 총혜택_금액에_따라_이벤트_배지를_부여한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(24), sampleOrders);

        //when
        EventBadge badge = eventService.getBadgeForBenefitAmount(customer);

        //then
        assertThat(badge).isEqualTo(EventBadge.SANTA);
    }
}