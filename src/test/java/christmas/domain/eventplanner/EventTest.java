package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.fixture.TestDataFactory;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Order;
import christmas.domain.restaurant.Orders;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class EventTest {

    private static Orders sampleOrders;

    @BeforeAll
    public static void beforeAll() {
        sampleOrders = TestDataFactory.createSampleOrders();
    }

    @Test
    void 총주문_금액이_만원_미만이면_이벤트_미적용() {
        //given

        //when

        //then
    }

    @Test
    void 첫날부터_크리스마스까지_크리스마스_디데이_할인이다() {
        //given
        Customer customer = Customer.reserveVisit(1, sampleOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(events).contains(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @Test
    void 크리스마스_이후는_크리스마스_디데이_할인이_아니다() {
        //given
        Customer customer = Customer.reserveVisit(26, sampleOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(events).doesNotContain(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1000", "15, 2400", "25, 3400"})
    void 크리스마스_디데이_할인은_1000원으로_시작하여_날마다_100원씩_증가(int visitDate, int expectedDiscountAmount) {
        //given
        Event christmasDDayDiscount = Event.CHRISTMAS_D_DAY_DISCOUNT;
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);

        //when
        int discountAmount = christmasDDayDiscount.calculate(customer);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 일요일부터_목요일까지는_평일_할인이다(int visitDate) {
        //given
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);
        DayType dayType = EventCalender.getDayType(visitDate);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(dayType).isEqualTo(DayType.WEEKDAY);
        assertThat(events).contains(Event.WEEKDAY_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 금요일_토요일은_평일_할인이_아니다(int visitDate) {
        //given
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);
        DayType dayType = EventCalender.getDayType(visitDate);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(dayType).isNotEqualTo(DayType.WEEKDAY);
        assertThat(events).doesNotContain(Event.WEEKDAY_DISCOUNT);
    }

    @Test
    void 평일_할인이지만_디저트_메뉴를_주문하지_않으면_이벤트_미적용() {
        //given
        int visitDate = 3;
        List<Order> orders = List.of(
                new Order(Menu.T_BONE_STEAK.getName(), 1)
        );
        Orders noDessertOrders = new Orders(orders);
        Customer customer = Customer.reserveVisit(visitDate, noDessertOrders);
        DayType dayType = EventCalender.getDayType(visitDate);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(dayType).isEqualTo(DayType.WEEKDAY);
        assertThat(events).doesNotContain(Event.WEEKDAY_DISCOUNT);
    }

    @Test
    void 평일_할인에는_디저트_메뉴_1개당_2023원_할인한다() {
        //given
        Event weekdayDiscount = Event.WEEKDAY_DISCOUNT;
        Customer customer = Customer.reserveVisit(1, sampleOrders);
        int expectedDiscountAmount = EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue();

        //when
        int discountAmount = weekdayDiscount.calculate(customer);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 금요일_토요일은_주말할인이다(int visitDate) {
        //given
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);
        DayType dayType = EventCalender.getDayType(visitDate);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(dayType).isEqualTo(DayType.WEEKEND);
        assertThat(events).contains(Event.WEEKEND_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 일요일부터_목요일까지는_주말할인이_아니다(int visitDate) {
        //given
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);
        DayType dayType = EventCalender.getDayType(visitDate);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(dayType).isNotEqualTo(DayType.WEEKEND);
        assertThat(events).doesNotContain(Event.WEEKEND_DISCOUNT);
    }

    @Test
    void 주말_할인이지만_메인_메뉴를_주문하지_않으면_이벤트_미적용() {
        //given
        int visitDate = 1;
        List<Order> orders = List.of(
                new Order(Menu.MUSHROOM_SOUP.getName(), 5)
        );
        Orders noMainOrders = new Orders(orders);
        Customer customer = Customer.reserveVisit(visitDate, noMainOrders);
        DayType dayType = EventCalender.getDayType(visitDate);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(dayType).isEqualTo(DayType.WEEKEND);
        assertThat(events).doesNotContain(Event.WEEKDAY_DISCOUNT);
    }

    @Test
    void 주말_할인에는_메인_메뉴_1개당_2023원_할인한다() {
        //given
        Event weekendDiscount = Event.WEEKEND_DISCOUNT;
        Customer customer = Customer.reserveVisit(1, sampleOrders);
        int expectedDiscountAmount = EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue() * 3;

        //when
        int discountAmount = weekendDiscount.calculate(customer);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @Test
    void 이벤트_달력에_별이_있으면_특별_할인이다() {
        //given
        int visitDate = 3;
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(EventCalender.hasStar(visitDate)).isTrue();
        assertThat(events).contains(Event.SPECIAL_DISCOUNT);
    }

    @Test
    void 이벤트_달력에_별이_없으면_특별_할인이_아니다() {
        //given
        int visitDate = 4;
        Customer customer = Customer.reserveVisit(visitDate, sampleOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(EventCalender.hasStar(visitDate)).isFalse();
        assertThat(events).doesNotContain(Event.SPECIAL_DISCOUNT);
    }

    @Test
    void 특별_할인이면_총주문_금액에서_1000원_할인한다() {
        //given
        Event specialDiscount = Event.SPECIAL_DISCOUNT;
        Customer customer = Customer.reserveVisit(1, sampleOrders);
        int expectedDiscountAmount = EventConstants.SPECIAL_DISCOUNT_AMOUNT.getValue();

        //when
        int discountAmount = specialDiscount.calculate(customer);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @Test
    void 총주문_금액이_12만원_이상일_때_증정이벤트() {
        //given
        Customer customer = Customer.reserveVisit(1, sampleOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(customer.getOrderAmount()).isGreaterThanOrEqualTo(120_000);
        assertThat(events).contains(Event.GIFT_EVENT);
    }

    @Test
    void 총주문_금액이_12만원_미만일_때_증정이벤트는_없다() {
        //given
        List<Order> orders = List.of(
                new Order(Menu.MUSHROOM_SOUP.getName(), 1),
                new Order(Menu.CHOCOLATE_CAKE.getName(), 1)
        );
        Orders noGiftOrders = new Orders(orders);
        Customer customer = Customer.reserveVisit(1, noGiftOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(customer.getOrderAmount()).isLessThan(120_000);
        assertThat(events).doesNotContain(Event.GIFT_EVENT);
    }

    @Test
    void 이벤트는_중복_적용가능하다() {
        //given
        Customer customer = Customer.reserveVisit(3, sampleOrders);

        //when
        List<Event> events = Event.from(customer);

        //then
        assertThat(events).containsExactly(
                Event.CHRISTMAS_D_DAY_DISCOUNT,
                Event.WEEKDAY_DISCOUNT,
                Event.SPECIAL_DISCOUNT,
                Event.GIFT_EVENT
        );
    }
}