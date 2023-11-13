package christmas.domain.restaurant;

import java.util.Collections;
import java.util.Map;

public class Gift {

    private final Map<Menu, Integer> gift;

    private Gift(Map<Menu, Integer> gift) {
        this.gift = gift;
    }

    public static Gift asGiveaway() {
        return new Gift(
                Map.of(Menu.CHAMPAGNE, 1)
        );
    }

    public static Gift empty() {
        return new Gift(Collections.emptyMap());
    }

    public boolean isEmpty() {
        return gift.isEmpty();
    }

    public Map<Menu, Integer> getItems() {
        return gift;
    }

    public int getTotalAmount() {
        return gift.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
