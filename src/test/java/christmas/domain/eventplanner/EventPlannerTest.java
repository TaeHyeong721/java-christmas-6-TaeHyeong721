package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Food;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuCategory;
import java.util.List;
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
                new Menu(MenuCategory.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        Customer customer = Customer.reserveVisit(3, menus);
        EventPlanner eventPlanner = new EventPlanner();

        //when
        List<Event> events = eventPlanner.findEventsByCustomer(customer);

        //then
        assertThat(events).contains(
                Event.CHRISTMAS_D_DAY_DISCOUNT,
                Event.WEEKDAY_DISCOUNT,
                Event.SPECIAL_DISCOUNT
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