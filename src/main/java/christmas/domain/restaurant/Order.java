package christmas.domain.restaurant;

import christmas.util.ErrorMessage;

public class Order {

    private final Menu menu;
    private final int quantity;

    public Order(String menuName, int quantity) {
        validateQuantity(quantity);

        this.menu = Menu.from(menuName);
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException(ErrorMessage.QUANTITY_BELOW_MINIMUM.getMessage());
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isBeverage() {
        return Category.BEVERAGE == menu.getCategory();
    }
}
