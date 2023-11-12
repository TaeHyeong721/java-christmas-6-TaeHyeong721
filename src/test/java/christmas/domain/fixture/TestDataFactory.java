package christmas.domain.fixture;

import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Order;
import christmas.domain.restaurant.Orders;
import java.util.List;

public class TestDataFactory {

    public static Orders createSampleOrders() {
        List<Order> orders = List.of(
                new Order(Menu.MUSHROOM_SOUP.getName(), 1),
                new Order(Menu.T_BONE_STEAK.getName(), 1),
                new Order(Menu.BBQ_RIBS.getName(), 1),
                new Order(Menu.SEAFOOD_PASTA.getName(), 1),
                new Order(Menu.CHOCOLATE_CAKE.getName(), 1),
                new Order(Menu.ZERO_COLA.getName(), 1)
        );
        return new Orders(orders);
    }
}
