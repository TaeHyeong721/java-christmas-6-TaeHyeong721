package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.restaurant.Food;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuCategory;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class EventPlannerTest {

    @Test
    void 첫날부터_크리스마스까지_크리스마스_디데이_할인이다() {
        //given
        int visitDate = 1;
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> events = eventPlanner.findEventsByDate(visitDate);

        //then
        assertThat(events).contains(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @Test
    void 크리스마스_이후는_크리스마스_디데이_할인이_아니다() {
        //given
        int visitDate = 26;
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> events = eventPlanner.findEventsByDate(visitDate);

        //then
        assertThat(events).doesNotContain(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1000", "15, 2400", "25, 3400"})
    void 크리스마스_디데이_할인은_1000원으로_시작하여_날마다_100원씩_증가(int visitDate, int expectedDiscountAmount) {
        //given
        EventPlanner eventPlanner = new EventPlanner();

        //when
        int discountAmount = eventPlanner.getDiscountAmountByChristmasEvent(visitDate);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 78000", "15, 76600", "25, 75600"})
    void 크리스마스_디데이_할인은_총주문금액에서_해당_금액만큼_할인_해야한다(int visitDate, int expectedPaymentAmount) {
        //given
        EventPlanner eventPlanner = new EventPlanner();
        List<Menu> menus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        int totalOrderAmount = menus.stream()
                .mapToInt(Menu::getPrice)
                .sum();

        //when
        int discountAmount = eventPlanner.getDiscountAmountByChristmasEvent(visitDate);

        //then
        assertThat(totalOrderAmount - discountAmount).isEqualTo(expectedPaymentAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 일요일부터_목요일까지는_평일_할인이다(int visitDate) {
        //given
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> event = eventPlanner.findEventsByDate(visitDate);

        //then
        assertThat(event).contains(Event.WEEKDAY_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 금요일_토요일은_평일_할인이_아니다(int visitDate) {
        //given
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> event = eventPlanner.findEventsByDate(visitDate);

        //then
        assertThat(event).doesNotContain(Event.WEEKDAY_DISCOUNT);
    }

    @Test
    void 평일_할인에는_디저트_메뉴_1개당_2023원_할인한다() {
        //given
        EventPlanner eventPlanner = new EventPlanner();
        List<Menu> menus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );

        //when
        int discountAmount = eventPlanner.getDiscountAmountByWeekdayEvent(menus);

        //then
        assertThat(discountAmount).isEqualTo(2_023);
    }

    @ParameterizedTest
    @MethodSource("provideVisitDateAndMenusAndEventCount")
    void 적용되는_이벤트가_여러개면_할인도_중복_적용된다(int visitDate, List<Menu> menus, int ExpectedEventCount) {
        //given
        EventPlanner eventPlanner = new EventPlanner();
        int christmasDiscount = eventPlanner.getDiscountAmountByChristmasEvent(visitDate);
        int weekdayDiscount = eventPlanner.getDiscountAmountByWeekdayEvent(menus);

        //when
        List<Event> events = eventPlanner.findEventsByDate(visitDate);
        int discountAmount = eventPlanner.getDiscountAmount(visitDate, menus);


        //then
        assertThat(events).hasSize(ExpectedEventCount);
        assertThat(discountAmount).isEqualTo(christmasDiscount + weekdayDiscount);
    }

    private static Stream<Arguments> provideVisitDateAndMenusAndEventCount() {
        List<Menu> defaultMenus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        return Stream.of(
                Arguments.of(7, defaultMenus, 2)
        );
    }
}