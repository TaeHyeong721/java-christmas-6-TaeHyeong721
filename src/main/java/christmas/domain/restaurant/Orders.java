package christmas.domain.restaurant;

import java.util.Collections;
import java.util.List;

public class Orders {

    private static final int MAX_TOTAL_QUANTITY = 20;

    private final List<Order> orders;

    public Orders(List<Order> orders) {
        validateEmpty(orders);
        validateDuplicateMenu(orders);
        validateMaxTotalQuantity(orders);
        validateOnlyBeverage(orders);
        this.orders = orders;
    }

    private void validateEmpty(List<Order> orders) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 주문이 없습니다.");
        }
    }

    private void validateOnlyBeverage(List<Order> orders) {
        if (hasOnlyBeverage(orders)) {
            throw new IllegalArgumentException("[ERROR] 음료만 주문 시, 주문할 수 없습니다.");
        }
    }

    private boolean hasOnlyBeverage(List<Order> orders) {
        return orders.stream()
                .allMatch(Order::isBeverage);
    }

    private void validateDuplicateMenu(List<Order> orders) {
        if (hasDuplicateMenu(orders)) {
            throw new IllegalArgumentException("[ERROR] 중복 메뉴는 허용하지 않습니다.");
        }
    }

    private boolean hasDuplicateMenu(List<Order> orders) {
        return orders.stream()
                .map(Order::getMenu)
                .distinct()
                .count() != orders.size();
    }

    private void validateMaxTotalQuantity(List<Order> orders) {
        int totalQuantity = getTotalQuantity(orders);
        if (totalQuantity > MAX_TOTAL_QUANTITY) {
            throw new IllegalArgumentException("[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다.");
        }
    }

    private int getTotalQuantity(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getQuantity)
                .sum();
    }

    public int getTotalAmount() {
        return orders.stream()
                .mapToInt(Order::getAmount)
                .sum();
    }

    public int getTotalQuantityByCategory(Category category) {
        return orders.stream()
                .filter(order -> order.hasMenuByCategory(category))
                .mapToInt(Order::getQuantity)
                .sum();
    }

    public boolean hasMenuByCategory(Category category) {
        return orders.stream()
                .anyMatch(order -> order.hasMenuByCategory(category));
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
