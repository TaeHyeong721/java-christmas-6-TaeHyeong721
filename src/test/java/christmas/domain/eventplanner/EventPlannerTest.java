package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Food;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Category;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventPlannerTest {

    @Test
    void 고객정보를_받으면_적용되는_이벤트_내역을_반환한다() {
        //given
        List<Menu> menus = List.of(
                new Menu(Category.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(Category.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(Category.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(Category.MAIN_COURSE, Food.SEAFOOD_PASTA),
                new Menu(Category.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(Category.BEVERAGE, Food.ZERO_COLA)
        );
        Customer customer = Customer.reserveVisit(3, menus);
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> events = eventPlanner.findEventsByCustomer(customer);

        //then
        assertThat(events).containsExactly(
                Event.CHRISTMAS_D_DAY_DISCOUNT,
                Event.WEEKDAY_DISCOUNT,
                Event.SPECIAL_DISCOUNT,
                Event.GIFT_EVENT
        );
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
        Customer customer = Customer.reserveVisit(visitDate, menus);

        //when
        List<Event> events = eventPlanner.findEventsByCustomer(customer);
        int discountAmount = eventPlanner.getDiscountAmount(customer);

        //then
        assertThat(events).hasSize(ExpectedEventCount);
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    private static Stream<Arguments> provideVisitDateAndMenusAndEventCount() {
        List<Menu> menus = List.of(
                new Menu(Category.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(Category.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(Category.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(Category.MAIN_COURSE, Food.SEAFOOD_PASTA),
                new Menu(Category.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(Category.BEVERAGE, Food.ZERO_COLA)
        );
        return Stream.of(
                Arguments.of(3, menus, 4, 4223),
                Arguments.of(7, menus, 3, 3623),
                Arguments.of(8, menus, 3, 7769)
        );
    }

    @Test
    void 총주문_금액이_12만원_이상일_때_증정품_샴폐인_1개를_제공한다() {
        //given
        List<Menu> menus = List.of(
                new Menu(Category.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(Category.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(Category.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(Category.MAIN_COURSE, Food.SEAFOOD_PASTA),
                new Menu(Category.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(Category.BEVERAGE, Food.ZERO_COLA)
        );
        Customer customer = Customer.reserveVisit(1, menus);
        EventPlanner eventPlanner = new EventPlanner();
        Map<Menu, Integer> expectedGiftMenu = Map.of(
                new Menu(Category.BEVERAGE, Food.CHAMPAGNE), 1
        );

        //when
        Map<Menu, Integer> giftMenu = eventPlanner.getGiftMenu(customer);

        //then
        assertThat(giftMenu).isEqualTo(expectedGiftMenu);
    }

    @Test
    void 총주문_금액이_12만원_미만일_때_증정품은_없다() {
        //given
        List<Menu> menus = List.of(
                new Menu(Category.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(Category.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(Category.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(Category.BEVERAGE, Food.ZERO_COLA)
        );
        Customer customer = Customer.reserveVisit(1, menus);
        EventPlanner eventPlanner = new EventPlanner();
        Map<Menu, Integer> expectedGiftMenu = Map.of();

        //when
        Map<Menu, Integer> giftMenu = eventPlanner.getGiftMenu(customer);

        //then
        assertThat(giftMenu).isEqualTo(expectedGiftMenu);
    }


}