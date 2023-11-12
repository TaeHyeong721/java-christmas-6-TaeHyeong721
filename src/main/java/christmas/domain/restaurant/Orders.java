package christmas.domain.restaurant;

import christmas.util.ErrorMessage;
import java.util.List;

public class Orders {

    private static int MAX_TOTAL_QUANTITY = 20;

    List<Order> orders;

    public Orders(List<Order> orders) {
        validateDuplicateMenu(orders);
        validateMaxTotalQuantity(orders);
        validateOnlyBeverage(orders);
        this.orders = orders;
    }

    private void validateOnlyBeverage(List<Order> orders) {
        boolean isOnlyBeverage = orders.stream()
                .allMatch(Order::isBeverage);
        if (isOnlyBeverage) {
            throw new IllegalArgumentException(ErrorMessage.CANT_ORDERS_ONLY_BEVERAGE.getMessage());
        }
    }

    private void validateDuplicateMenu(List<Order> orders) {
        boolean isDuplicateMenu = orders.stream()
                .map(Order::getMenu)
                .distinct()
                .count() != orders.size();

        if (isDuplicateMenu) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_MENU.getMessage());
        }
    }

    private void validateMaxTotalQuantity(List<Order> orders) {
        int totalQuantity = orders.stream()
                .mapToInt(Order::getQuantity)
                .sum();
        if (totalQuantity > MAX_TOTAL_QUANTITY) {
            throw new IllegalArgumentException(ErrorMessage.TOO_MANY_TOTAL_QUANTITY.getMessage());
        }
    }

    public int getTotalAmount() {
        return orders.stream()
                .mapToInt(Order::getAmount)
                .sum();
    }

    public int getOrderCountByCategory(Category category) {
        return (int) orders.stream()
                .filter(order -> order.hasMenuByCategory(category))
                .count();
    }

    public boolean hasMenuByCategory(Category category) {
        return orders.stream()
                .anyMatch(order -> order.hasMenuByCategory(category));
    }
}
