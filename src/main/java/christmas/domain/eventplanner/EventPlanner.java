package christmas.domain.eventplanner;

import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuCategory;
import java.util.List;

public class EventPlanner {

    public List<Event> findEventsByDate(int visitDate) {
        return Event.from(visitDate);
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

    public int getDiscountAmountByWeekendEvent(List<Menu> menus) {
        int mainCount = (int)menus.stream()
                .filter(menu -> MenuCategory.MAIN_COURSE == menu.category())
                .count();
        return mainCount * EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue();
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
            if (Event.WEEKEND_DISCOUNT == event) {
                discountAmount += getDiscountAmountByWeekendEvent(menus);
            }
        }
        return discountAmount;
    }
}
