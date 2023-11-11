package christmas.domain.restaurant;

import java.util.Map;

public class Restaurant {

    private final Map<Menu, Integer> gift;

    public Restaurant() {
        this.gift = Map.of(Menu.CHAMPAGNE, 1);
    }

    public Map<Menu, Integer> requestGift() {
        return gift;
    }

    public int getGiftAmount() {
        return gift.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
