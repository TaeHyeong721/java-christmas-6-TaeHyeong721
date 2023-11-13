package christmas.domain.customer;

import christmas.util.ErrorMessage;

public record VisitDate(int value) {

    public VisitDate {
        validateDateInRange(value);
    }

    private void validateDateInRange(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_VISIT_DATE.getMessage());
        }
    }
}
