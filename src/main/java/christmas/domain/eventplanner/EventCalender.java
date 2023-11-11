package christmas.domain.eventplanner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class EventCalender {

    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final List<Integer> starDay = List.of(3, 10, 17, 24, 25, 31);

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

    public static boolean hasStar(int visitDate) {
        return starDay.contains(visitDate);
    }
}
