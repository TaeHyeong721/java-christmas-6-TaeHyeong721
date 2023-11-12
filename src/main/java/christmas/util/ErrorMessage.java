package christmas.util;

public enum ErrorMessage {
    INVALID_INPUT_VISIT_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_INPUT_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    INVALID_INPUT_ORDER_FORMAT("메뉴 입력 형식이 유효하지 않습니다."),
    INVALID_NUMBER_FORMAT("유효한 숫자 형식이 아닙니다."),
    NOT_FOUND_MENU_BY_NAME("해당 이름을 가진 메뉴는 존재하지 않습니다."),
    QUANTITY_BELOW_MINIMUM("수량은 최소 1개 이상이어야 합니다."),
    TOO_MANY_TOTAL_QUANTITY("메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다."),
    DUPLICATE_MENU("중복 메뉴가 존재합니다."),
    CANT_ORDERS_ONLY_BEVERAGE("음료만 주문 시, 주문할 수 없습니다.");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
