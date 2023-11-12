package christmas.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void 생성시_수량이_1보다_작으면_예외_발생() {
        assertThatThrownBy(() -> new Order(Menu.BBQ_RIBS.getName(), 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴에_없는_이름으로_생성하면_예외_발생() {
        assertThatThrownBy(() -> new Order("없는메뉴", 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}