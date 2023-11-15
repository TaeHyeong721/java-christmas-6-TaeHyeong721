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
import java.util.StringJoiner;

public class EventPreviewDto {

    private static final String LINE_SEPARATOR = System.lineSeparator();

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
        StringJoiner joiner = new StringJoiner(LINE_SEPARATOR);
        List<Order> orderList = orders.getOrders();
        for (Order order : orderList) {
            joiner.add(formatItem(order.getMenuName(), order.getQuantity()));
        }

        return joiner.toString();
    }

    public String getTotalOrderAmount() {
        return String.format("%s원", formatAmount(orders.getTotalAmount()));
    }

    public String getGiftMenu() {
        if (gift.isEmpty()) {
            return "없음";
        }

        StringJoiner joiner = new StringJoiner(LINE_SEPARATOR);
        gift.getItems().forEach((menu, quantity) -> {
            joiner.add(formatItem(menu.getName(), quantity));
        });

        return joiner.toString();
    }

    private String formatItem(String name, Integer quantity) {
        return String.format("%s %d개", name, quantity);
    }

    public String getBenefitDetails() {
        if (benefitDetails.isEmpty()) {
            return "없음";
        }

        StringJoiner joiner = new StringJoiner(LINE_SEPARATOR);
        benefitDetails.forEach((event, discount) -> {
            joiner.add(String.format("%s: -%s원", event.getName(), formatAmount(discount)));
        });

        return joiner.toString();
    }

    public String getBenefitAmount() {
        if (benefitDetails.isEmpty()) {
            return "0원";
        }

        int benefitAmount = benefitDetails.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        return String.format("-%s원", formatAmount(benefitAmount));
    }

    public String getPaymentAmount() {
        int ordersAmount = orders.getTotalAmount();

        return String.format("%s원", formatAmount(ordersAmount - discountAmount));
    }

    public String getEventBadge() {
        if (EventBadge.NONE.equals(badge)) {
            return "없음";
        }

        return badge.getName();
    }

    private String formatAmount(int number) {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(number);
    }
}
