package christmas.domain.customer;

public record VisitDate(int value) {

    public VisitDate {
        validateDateInRange(value);
    }

    private void validateDateInRange(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("[ERROR] 방문할 날짜는 1 이상 31 이하의 숫자만 가능합니다.");
        }
    }
}
