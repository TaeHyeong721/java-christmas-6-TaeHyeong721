package christmas.domain.eventplanner;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventBadgeTest {

    @ParameterizedTest
    @MethodSource("provideBenefitAmountWithExpectedBadge")
    void 총혜택_금액에_따라_이벤트_배지가_부여된다(int benefitAmount, EventBadge expectedBadge) {
        EventBadge eventBadge = EventBadge.fromBenefitAmount(benefitAmount);

        Assertions.assertThat(eventBadge).isEqualTo(expectedBadge);
    }

    private static Stream<Arguments> provideBenefitAmountWithExpectedBadge() {
        return Stream.of(
                Arguments.of(3_000, EventBadge.NONE),
                Arguments.of(6_000, EventBadge.STAR),
                Arguments.of(12_000, EventBadge.TREE),
                Arguments.of(25_000, EventBadge.SANTA)
        );
    }
}