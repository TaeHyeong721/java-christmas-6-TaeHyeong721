package christmas.domain.eventplanner;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class EventCalender {

    private static final int YEAR = 2023;
    private static final int MONTH = 12;

    public DayOfWeek getDayOfWeek(int visitDate) {
        return LocalDate.of(YEAR, MONTH, visitDate)
                .getDayOfWeek();
    }
}
