package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EventTest {

    @Test
    void 첫날부터_크리스마스까지_크리스마스_디데이_할인이다() {
        //given
        int visitDate = 1;

        //when
        List<Event> events = Event.from(visitDate);

        //then
        assertThat(events).contains(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @Test
    void 크리스마스_이후는_크리스마스_디데이_할인이_아니다() {
        //given
        int visitDate = 26;

        //when
        List<Event> events = Event.from(visitDate);

        //then
        assertThat(events).doesNotContain(Event.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 일요일부터_목요일까지는_평일_할인이다(int visitDate) {
        List<Event> events = Event.from(visitDate);

        assertThat(events).contains(Event.WEEKDAY_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 금요일_토요일은_평일_할인이_아니다(int visitDate) {
        List<Event> events = Event.from(visitDate);

        assertThat(events).doesNotContain(Event.WEEKDAY_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 금요일_토요일은_주말할인이다(int visitDate) {
        List<Event> events = Event.from(visitDate);

        assertThat(events).contains(Event.WEEKEND_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 일요일부터_목요일까지는_주말할인이_아니다(int visitDate) {
        List<Event> events = Event.from(visitDate);

        assertThat(events).doesNotContain(Event.WEEKEND_DISCOUNT);
    }

    @Test
    void 이벤트_달력에_별이_있으면_특별_할인이다() {
        //given
        int visitDate = 3;

        //when
        List<Event> events = Event.from(visitDate);

        //then
        assertThat(events).contains(Event.SPECIAL_DISCOUNT);
    }

    @Test
    void 이벤트_달력에_별이_없으면_특별_할인이_아니다() {
        //given
        int visitDate = 4;

        //when
        List<Event> events = Event.from(visitDate);

        //then
        assertThat(events).doesNotContain(Event.SPECIAL_DISCOUNT);
    }

    @Test
    void 이벤트는_중복_적용가능하다() {
        //given
        int visitDate = 3;

        //when
        List<Event> events = Event.from(visitDate);

        //then
        assertThat(events).hasSize(3);
        assertThat(events).contains(
                Event.CHRISTMAS_D_DAY_DISCOUNT,
                Event.WEEKDAY_DISCOUNT,
                Event.SPECIAL_DISCOUNT
        );
    }
}