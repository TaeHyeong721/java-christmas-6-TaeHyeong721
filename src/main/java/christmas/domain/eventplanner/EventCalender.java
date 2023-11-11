package christmas.domain.eventplanner;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class EventCalender {

    private static final int YEAR = 2023;
    private static final int MONTH = 12;

    public static DayOfWeek getDayOfWeek(int visitDate) {
        return LocalDate.of(YEAR, MONTH, visitDate)
                .getDayOfWeek();
    }

    public static DayType getDayType(int visitDate) {
        DayOfWeek dayOfWeek = getDayOfWeek(visitDate);
        return switch (dayOfWeek) {
            case SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY -> DayType.WEEKDAY;
            case FRIDAY, SATURDAY -> DayType.WEEKEND;
        };
    }
}
