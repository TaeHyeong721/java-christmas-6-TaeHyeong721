package christmas.domain.eventplanner;

import java.util.ArrayList;
import java.util.List;

public enum Event {
    CHRISTMAS_D_DAY_DISCOUNT,
    WEEKDAY_DISCOUNT,
    WEEKEND_DISCOUNT;

    public static List<Event> from(int visitDate) {
        List<Event> events = new ArrayList<>();

        if (visitDate <= EventConstants.CHRISTMAS_DATE.getValue()) {
            events.add(Event.CHRISTMAS_D_DAY_DISCOUNT);
        }
        if (DayType.WEEKDAY == EventCalender.getDayType(visitDate)) {
            events.add(Event.WEEKDAY_DISCOUNT);
        }
        if (DayType.WEEKEND == EventCalender.getDayType(visitDate)) {
            events.add(Event.WEEKEND_DISCOUNT);
        }

        return events;
    }
}
