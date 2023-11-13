package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.customer.Customer;
import christmas.domain.customer.VisitDate;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Order;
import christmas.domain.restaurant.Orders;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EventPlannerTest {

    private static Orders defaultOrders;

    @BeforeAll
    public static void beforeAll() {
        defaultOrders = new Orders(testOrders());
    }

    private static List<Order> testOrders() {
        return List.of(
                new Order(Menu.MUSHROOM_SOUP.getName(), 1),
                new Order(Menu.T_BONE_STEAK.getName(), 1),
                new Order(Menu.BBQ_RIBS.getName(), 1),
                new Order(Menu.SEAFOOD_PASTA.getName(), 1),
                new Order(Menu.CHOCOLATE_CAKE.getName(), 1),
                new Order(Menu.ZERO_COLA.getName(), 1)
        );
    }

    @Test
    void 고객정보를_받으면_적용되는_이벤트_목록을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(24), defaultOrders);
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