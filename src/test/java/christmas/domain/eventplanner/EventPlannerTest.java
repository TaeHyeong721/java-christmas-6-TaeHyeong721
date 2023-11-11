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

class EventPlannerTest {

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

    @Test
    void 평일_할인에는_디저트_메뉴_1개당_2023원_할인한다() {
        //given
        EventPlanner eventPlanner = new EventPlanner();
        List<Menu> menus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.DESSERT, Food.ICE_CREAM)
        );
        int expectedDiscountAmount = EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue() * 2;

        //when
        int discountAmount = eventPlanner.getDiscountAmountByWeekdayEvent(menus);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @Test
    void 주말_할인에는_메인_메뉴_1개당_2023원_할인한다() {
        //given
        EventPlanner eventPlanner = new EventPlanner();
        List<Menu> menus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        int expectedDiscountAmount = EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue() * 2;

        //when
        int discountAmount = eventPlanner.getDiscountAmountByWeekendEvent(menus);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @Test
    void 특별_할인이면_총주문_금액에서_1000원_할인한다() {
        //given
        EventPlanner eventPlanner = new EventPlanner();
        List<Menu> menus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        int expectedDiscountAmount = EventConstants.SPECIAL_DISCOUNT_AMOUNT.getValue();

        //when
        int discountAmount = eventPlanner.getDiscountAmountBySpecialDiscount();

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }


    @ParameterizedTest
    @MethodSource("provideVisitDateAndMenusAndEventCount")
    void 적용되는_이벤트가_여러개면_할인도_중복_적용된다(
            int visitDate,
            List<Menu> menus,
            int ExpectedEventCount,
            int expectedDiscountAmount
    ) {
        //given
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> events = eventPlanner.findEventsByDate(visitDate);
        int discountAmount = eventPlanner.getDiscountAmount(visitDate, menus);

        //then
        assertThat(events).hasSize(ExpectedEventCount);
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    private static Stream<Arguments> provideVisitDateAndMenusAndEventCount() {
        List<Menu> defaultMenus = List.of(
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        return Stream.of(
                Arguments.of(3, defaultMenus, 3, 4223),
                Arguments.of(7, defaultMenus, 2, 3623),
                Arguments.of(8, defaultMenus, 2, 3723)
        );
    }
}