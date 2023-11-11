package christmas.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.eventplanner.Event;
import christmas.domain.restaurant.Menu;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    void 증정_이벤트_적용시_샴페인_1개를_증정한다() {
        //given
        Customer customer = Customer.reserveVisit(3, defaultMenus);
        Map<Menu, Integer> expectedGift = Map.of(Menu.CHAMPAGNE, 1);

        //when
        List<Event> events = eventService.findEventByCustomer(customer);
        Map<Menu, Integer> gift = eventService.getGiftMenu(customer);

        //then
        assertThat(events).contains(Event.GIFT_EVENT);
        assertThat(gift).isEqualTo(expectedGift);
    }

    @Test
    void 증정_이벤트_미적용시_증정품은_없다() {
        //given
        List<Menu> menus = List.of(Menu.T_BONE_STEAK);
        Customer customer = Customer.reserveVisit(3, menus);

        //when
        List<Event> events = eventService.findEventByCustomer(customer);
        Map<Menu, Integer> gift = eventService.getGiftMenu(customer);

        //then
        assertThat(events).doesNotContain(Event.GIFT_EVENT);
        assertThat(gift).isEqualTo(Collections.emptyMap());
    }

    @Test
    void 고객정보를_받으면_혜택_내역을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);
        Map<Menu, Integer> giftMenu = eventService.getGiftMenu(customer);
        Map<Event, Integer> expectedBenefitDetails = Map.of(
                Event.CHRISTMAS_D_DAY_DISCOUNT, Event.CHRISTMAS_D_DAY_DISCOUNT.calculate(customer),
                Event.WEEKDAY_DISCOUNT, Event.WEEKDAY_DISCOUNT.calculate(customer),
                Event.SPECIAL_DISCOUNT, Event.SPECIAL_DISCOUNT.calculate(customer),
                Event.GIFT_EVENT, getGiftAmount(giftMenu)
        );

        //when
        Map<Event, Integer> benefitDetails = eventService.getBenefitDetails(customer);

        //then
        assertThat(benefitDetails).isEqualTo(expectedBenefitDetails);
    }

    @Test
    void 고객정보를_받으면_총혜택_금액을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);
        int expectedChristmasEventDiscount = Event.CHRISTMAS_D_DAY_DISCOUNT.calculate(customer);
        int expectedWeekdayDiscount = Event.WEEKDAY_DISCOUNT.calculate(customer);
        int expectedSpecialDiscount = Event.SPECIAL_DISCOUNT.calculate(customer);
        int expectedGiftAmount = getGiftAmount(eventService.getGiftMenu(customer));

        //when
        int benefitAmount = eventService.getBenefitAmount(customer);

        //then
        assertThat(benefitAmount).isEqualTo(expectedChristmasEventDiscount + expectedWeekdayDiscount + expectedSpecialDiscount + expectedGiftAmount);
    }

    private static int getGiftAmount(Map<Menu, Integer> giftMenu) {
        return giftMenu.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}