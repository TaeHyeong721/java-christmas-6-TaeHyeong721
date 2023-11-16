package christmas.domain.customer;

import christmas.domain.restaurant.Category;
import christmas.domain.restaurant.Orders;

public class Customer {

    private final VisitDate visitDate;
    private final Orders orders;

    private Customer(VisitDate visitDate, Orders orders) {
        this.visitDate = visitDate;
        this.orders = orders;
    }

    public static Customer reserveVisit(VisitDate visitDate, Orders orders) {
        return new Customer(visitDate, orders);
    }

    public int getVisitDate() {
        return visitDate.value();
    }

    public int getTotalOrderAmount() {
        return orders.getTotalAmount();
    }

    public int getTotalMenuQuantityByCategory(Category category) {
        return orders.getTotalQuantityByCategory(category);
    }

    public boolean hasMenuByCategory(Category category) {
        return orders.hasMenuByCategory(category);
    }
}
