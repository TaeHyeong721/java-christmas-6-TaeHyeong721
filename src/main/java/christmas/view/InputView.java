package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.util.ErrorMessage;

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
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_VISIT_DATE.getMessageWithPrefix());
        }
    }

    private int convertStringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_VISIT_DATE.getMessageWithPrefix());
        }
    }
}
