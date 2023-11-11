package christmas.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class RestaurantTest {

    @Test
    void 증정품을_요청하면_샴폐인_1개를_제공한다() {
        //given
        Restaurant restaurant = new Restaurant();
        Map<Menu, Integer> expectedGift = Map.of(
                new Menu(Category.BEVERAGE, Food.CHAMPAGNE), 1
        );

        //when
        Map<Menu, Integer> giftMenu = restaurant.requestGiftMenu();

        //then
        assertThat(giftMenu).isEqualTo(expectedGift);
    }
}