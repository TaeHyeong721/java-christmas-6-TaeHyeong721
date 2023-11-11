package christmas.domain.customer;

import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.Category;
import java.util.List;

public class Customer {

    private final int visitDate;
    private final List<Menu> menus;

    private Customer(int visitDate, List<Menu> menus) {
        this.visitDate = visitDate;
        this.menus = menus;
    }

    public static Customer reserveVisit(int visitDate, List<Menu> menus) {
        return new Customer(visitDate, menus);
    }

    public int getVisitDate() {
        return visitDate;
    }

    public int getTotalOrderAmount() {
        return menus.stream()
                .mapToInt(Menu::getPrice)
                .sum();
    }

    public int getCategoryCount(Category category) {
        return (int)menus.stream()
                .filter(menu -> menu.category() == category)
                .count();
    }

    public boolean hasMenuByCategory(Category category) {
        return menus.stream()
                .anyMatch(menu -> menu.category() == category);
    }
}
