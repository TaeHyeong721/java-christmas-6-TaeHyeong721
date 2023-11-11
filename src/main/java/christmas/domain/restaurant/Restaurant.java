package christmas.domain.restaurant;

import java.util.Map;

public class Restaurant {

    public Map<Menu, Integer> requestGiftMenu() {
        return Map.of(
                new Menu(Category.BEVERAGE, Food.CHAMPAGNE), 1
        );
    }
}
