package christmas.view;

import christmas.util.ErrorMessage;

public class OutputView {

    public void printWelcome() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }
    public void printMenu() {
        System.out.println("<주문 메뉴>");
    }

    public void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }
}
