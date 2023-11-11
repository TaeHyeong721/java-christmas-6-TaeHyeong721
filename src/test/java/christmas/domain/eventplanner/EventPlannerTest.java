package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.restaurant.Category;
import christmas.domain.restaurant.Food;
import christmas.domain.restaurant.Menu;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EventPlannerTest {

    private static List<Menu> defaultMenus;

    @BeforeAll
    public static void beforeAll() {
        defaultMenus = List.of(
                new Menu(Category.APPETIZER, Food.MUSHROOM_SOUP),
                new Menu(Category.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(Category.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(Category.MAIN_COURSE, Food.SEAFOOD_PASTA),
                new Menu(Category.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(Category.BEVERAGE, Food.ZERO_COLA)
        );
    }

    @Test
    void 고객정보를_받으면_적용되는_이벤트_목록을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(24, defaultMenus);
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
}