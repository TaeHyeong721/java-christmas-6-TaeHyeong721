package christmas.domain.restaurant;

import java.util.Map;

public class Restaurant {

    public Map<Menu, Integer> requestGift() {
        return Map.of(
                Menu.CHAMPAGNE, 1
        );
    }
}
