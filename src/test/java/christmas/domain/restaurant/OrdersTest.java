package christmas.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class OrdersTest {

    @Test
    void 생성시_중복_메뉴가_있는_경우_예외_발생() {
        List<Order> orders = List.of(
                new Order(Menu.BBQ_RIBS.getName(), 1),
                new Order(Menu.BBQ_RIBS.getName(), 2)
        );
        assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 생성시_주문_메뉴가_20개_초과_시_예외_발생() {
        List<Order> orders = List.of(
                new Order(Menu.BBQ_RIBS.getName(), 8),
                new Order(Menu.ZERO_COLA.getName(), 13)
        );
        assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 생성시_음료만_주문하는_경우_예외_발생() {
        List<Order> orders = List.of(
                new Order(Menu.CHAMPAGNE.getName(), 1),
                new Order(Menu.ZERO_COLA.getName(), 1)
        );
        assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}