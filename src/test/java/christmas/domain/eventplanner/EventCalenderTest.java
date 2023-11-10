package christmas.domain.eventplanner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EventCalenderTest {

    @ParameterizedTest
    @MethodSource("provideVisitDateWithExpectedDayOfWeek")
    void 날짜를_입력하면_요일을_구한다(int visitDate, DayOfWeek expectedDayOfWeek) {
        //given
        EventCalender eventCalender = new EventCalender();

        //when
        DayOfWeek dayOfWeek = eventCalender.getDayOfWeek(visitDate);

        //then
        assertThat(dayOfWeek).isEqualTo(expectedDayOfWeek);
    }

    private static Stream<Arguments> provideVisitDateWithExpectedDayOfWeek() {
        return Stream.of(
                Arguments.of(1, DayOfWeek.FRIDAY),
                Arguments.of(13, DayOfWeek.WEDNESDAY),
                Arguments.of(25, DayOfWeek.MONDAY)
        );
    }
}