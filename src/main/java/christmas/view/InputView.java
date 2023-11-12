package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.restaurant.Order;
import christmas.domain.restaurant.Orders;
import christmas.util.ErrorMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InputView {

    public int readDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String input = Console.readLine();

        int visitDate = convertStringToInt(input);
        validateDateInRange(visitDate);

        return visitDate;
    }

    private void validateDateInRange(int visitDate) {
        if (visitDate < 1 || visitDate > 31) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_VISIT_DATE.getMessage());
        }
    }

    private int convertStringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    public Orders readOrders() {
        System.out.println("주문하실 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String input = Console.readLine();

        validateOrderFormat(input);
        List<Order> orders = convertStringToOrderList(input);

        return new Orders(orders);
    }

    private List<Order> convertStringToOrderList(String input) {
        List<Order> orders = new ArrayList<>();

        String[] eachOrders = input.split(",");
        for (String order : eachOrders) {
            String[] orderParts = order.split("-");

            if (orderParts.length != 2) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_ORDER_FORMAT.getMessage());
            }

            String menuName = orderParts[0];
            int quantity = convertStringToInt(orderParts[1]);

            orders.add(new Order(menuName, quantity));
        }

        return orders;
    }

    private void validateOrderFormat(String input) {
        boolean matchesOrderFormat = Pattern.matches("^([가-힣]+-\\d+)(,[가-힣]+-\\d+)*$", input);
        if (!matchesOrderFormat) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_ORDER_FORMAT.getMessage());
        }
    }
}
