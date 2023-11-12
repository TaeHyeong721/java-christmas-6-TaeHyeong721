package christmas.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.restaurant.Menu;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void 총주문_금액을_반환한다() {
        //given
        List<Menu> menus = List.of(
                Menu.T_BONE_STEAK,
                Menu.BBQ_RIBS,
                Menu.CHOCOLATE_CAKE,
                Menu.ZERO_COLA
        );
        Customer customer = Customer.reserveVisit(1, menus);

        //when
        int totalOrderAmount = customer.getOrderAmount();

        //then
        assertThat(totalOrderAmount).isEqualTo(55_000 + 54_000 + 15_000 + 3_000);
    }
}