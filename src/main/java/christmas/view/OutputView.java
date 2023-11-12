package christmas.view;

public class OutputView {

    public void printWelcome() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }
    public void printMenu() {
        System.out.println("<주문 메뉴>");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
