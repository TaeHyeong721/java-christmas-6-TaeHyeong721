package christmas.domain.customer;

import christmas.domain.restaurant.Category;
import christmas.domain.restaurant.Orders;

public class Customer {

    private final int visitDate;
    private final Orders orders;

    private Customer(int visitDate, Orders orders) {
        this.visitDate = visitDate;
        this.orders = orders;
    }

    public static Customer reserveVisit(int visitDate, Orders menus) {
        return new Customer(visitDate, menus);
    }

    public int getVisitDate() {
        return visitDate;
    }

    public int getOrderAmount() {
        return orders.getTotalAmount();
    }

    public int getTotalMenuQuantityByCategory(Category category) {
        return orders.getTotalMenuQuantityByCategory(category);
    }

    public boolean hasMenuByCategory(Category category) {
        return orders.hasMenuByCategory(category);
    }
}
