package christmas.domain.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class VisitDateTest {

    @Test
    void 방문할_날짜가_1이상_31이하의_숫자가_아닌경우_예외_발생() {
        Assertions.assertThatThrownBy(() -> new VisitDate(32))
                .isInstanceOf(IllegalArgumentException.class);
    }
}