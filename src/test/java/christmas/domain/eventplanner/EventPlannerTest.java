package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventPlannerTest {

    @Test
    void 첫날부터_크리스마스까지_크리스마스_디데이_할인이다() {
        //given
        int visitDate = 1;
        EventPlanner eventPlanner = new EventPlanner();

        //when
        Event event = eventPlanner.findEventsByDate(visitDate);

        //then
        assertThat(event).isEqualTo(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @Test
    void 크리스마스_이후는_크리스마스_디데이_할인이_아니다() {
        //given
        int visitDate = 26;
        EventPlanner eventPlanner = new EventPlanner();

        //when
        Event event = eventPlanner.findEventsByDate(visitDate);

        //then
        assertThat(event).isNotEqualTo(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 1000", "15, 2400", "25, 3400"})
    void 크리스마스_디데이_할인은_1000원으로_시작하여_날마다_100원씩_증가(int visitDate, int expectedDiscountAmount) {
        //given
        EventPlanner eventPlanner = new EventPlanner();

        //when
        int discountAmount = eventPlanner.getDiscountAmount(Event.CHRISTMAS_D_DAY_DISCOUNT, visitDate);

        //then
        assertThat(discountAmount).isEqualTo(expectedDiscountAmount);
    }

    @Disabled
    @Test
    void 총주문금액에서_할인금액이_적용되어야_한다() {
        //TODO: 총주문금액과 관련된 기능이 개발된 이후 테스트
    }
}