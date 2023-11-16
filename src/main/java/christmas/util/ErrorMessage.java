package christmas.util;

public enum ErrorMessage {
    INVALID_INPUT_VISIT_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_INPUT_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
