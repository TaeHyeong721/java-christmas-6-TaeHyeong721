package christmas.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.restaurant.Food;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuCategory;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void 총주문_금액을_반환한다() {
        //given
        List<Menu> menus = List.of(
                new Menu(MenuCategory.MAIN_COURSE, Food.T_BONE_STEAK),
                new Menu(MenuCategory.MAIN_COURSE, Food.BBQ_RIBS),
                new Menu(MenuCategory.DESSERT, Food.CHOCOLATE_CAKE),
                new Menu(MenuCategory.BEVERAGE, Food.ZERO_COLA)
        );
        Customer customer = Customer.reserveVisit(1, menus);

        //when
        int totalOrderAmount = customer.getTotalOrderAmount();

        //then
        assertThat(totalOrderAmount).isEqualTo(55_000 + 54_000 + 15_000 + 3_000);
    }
}