package christmas.domain.restaurant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "짜장면", "soup"})
    void 이름에_해당하는_메뉴가_없으면_예외_발생(String menuName) {
        Assertions.assertThatThrownBy(() -> Menu.from(menuName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}