package christmas.domain.eventplanner;

import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuCategory;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class EventPlanner {

    private final EventCalender eventCalender;

    public EventPlanner() {
        this.eventCalender = new EventCalender();
    }

    public List<Event> findEventsByDate(int visitDate) {
        List<Event> events = new ArrayList<>();

        if (visitDate <= EventConstants.CHRISTMAS_DATE.getValue()) {
            events.add(Event.CHRISTMAS_D_DAY_DISCOUNT);
        }
        if (isWeekday(visitDate)) {
            events.add(Event.WEEKDAY_DISCOUNT);
        }

        return events;
    }

    private boolean isWeekday(int visitDate) {
        DayOfWeek dayOfWeek = eventCalender.getDayOfWeek(visitDate);
        return switch (dayOfWeek) {
            case SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY -> true;
            case FRIDAY, SATURDAY -> false;
        };
    }

    public int getDiscountAmountByChristmasEvent(int visitDate) {
        return calculateChristmasDDayDiscount(visitDate);
    }

    private int calculateChristmasDDayDiscount(int visitDate) {
        int firstDayOfMonth = 1;
        int discountStartAmount = EventConstants.CHRISTMAS_DISCOUNT_START_AMOUNT.getValue();
        int discountIncreaseUnit = EventConstants.CHRISTMAS_DISCOUNT_INCREASE_UNIT.getValue();

        return discountStartAmount + (visitDate - firstDayOfMonth) * discountIncreaseUnit;
    }

    public int getDiscountAmountByWeekdayEvent(List<Menu> menus) {
            int dessertCount = (int)menus.stream()
                    .filter(menu -> MenuCategory.DESSERT == menu.category())
                    .count();
            return dessertCount * EventConstants.WEEKDAY_DISCOUNT_AMOUNT.getValue();
    }

    public int getDiscountAmount(int visitDate, List<Menu> menus) {
        int discountAmount = 0;

        List<Event> events = findEventsByDate(visitDate);
        for (Event event : events) {
            if (Event.CHRISTMAS_D_DAY_DISCOUNT == event) {
                discountAmount += getDiscountAmountByChristmasEvent(visitDate);
            }
            if (Event.WEEKDAY_DISCOUNT == event) {
                discountAmount += getDiscountAmountByWeekdayEvent(menus);
            }
        }
        return discountAmount;
    }
}
