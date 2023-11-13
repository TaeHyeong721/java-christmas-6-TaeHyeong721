package christmas.dto;

import christmas.domain.eventplanner.Event;
import christmas.domain.eventplanner.EventBadge;
import christmas.domain.restaurant.Gift;
import christmas.domain.restaurant.Order;
import christmas.domain.restaurant.Orders;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventPreviewDto {

    private final Orders orders;
    private final Gift gift;
    private final Map<Event, Integer> benefitDetails;
    private final int discountAmount;
    private final EventBadge badge;

    public EventPreviewDto(
            Orders orders,
            Gift gift,
            Map<Event, Integer> benefitDetails,
            int discountAmount,
            EventBadge badge
    ) {
        this.orders = orders;
        this.gift = gift;
        this.benefitDetails = benefitDetails;
        this.discountAmount = discountAmount;
        this.badge = badge;
    }

    public String getOrderMenu() {
        StringBuilder sb = new StringBuilder();
        List<Order> orderList = orders.getOrders();
        for (Order order : orderList) {
            sb.append(formatItem(order.getMenuName(), order.getQuantity()));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getTotalOrderAmount() {
        return String.format("%s원\n", formatAmount(orders.getTotalAmount()));
    }

    public String getGiftMenu() {
        if (gift.isEmpty()) {
            return "없음\n";
        }

        StringBuilder sb = new StringBuilder();
        gift.getItems().forEach((menu, quantity) -> {
            sb.append(formatItem(menu.getName(), quantity));
            sb.append("\n");
        });

        return sb.toString();
    }

    private String formatItem(String name, Integer quantity) {
        return String.format("%s %d개", name, quantity);
    }

    public String getBenefitDetails() {
        if (benefitDetails.isEmpty()) {
            return "없음\n";
        }

        StringBuilder sb = new StringBuilder();
        benefitDetails.forEach((event, discount) -> {
            sb.append(String.format("%s: -%s원", event.getName(), formatAmount(discount)));
            sb.append("\n");
        });

        return sb.toString();
    }

    public String getBenefitAmount() {
        if (benefitDetails.isEmpty()) {
            return "0원\n";
        }

        int benefitAmount = benefitDetails.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        return String.format("-%s원\n", formatAmount(benefitAmount));
    }

    public String getPaymentAmount() {
        int ordersAmount = orders.getTotalAmount();

        return String.format("%s원\n", formatAmount(ordersAmount - discountAmount));
    }

    public String getEventBadge() {
        if (badge.equals(EventBadge.NONE)) {
            return "없음\n";
        }

        return badge.getName();
    }

    private String formatAmount(int number) {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(number);
    }
}
