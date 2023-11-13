package christmas.view;

import christmas.dto.EventPreviewDto;
import christmas.util.ErrorMessage;

public class OutputView {

    public void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }

    public void printWelcome() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    public void printPreviewMessage(int visitDate) {
        System.out.println(String.format("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n", visitDate));
    }

    public void printMenu(EventPreviewDto eventPreview) {
        System.out.println("<주문 메뉴>");
        System.out.println(eventPreview.getOrderMenu());
    }

    public void printOrderAmount(EventPreviewDto eventPreviewDto) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(eventPreviewDto.getOrderAmount());
    }

    public void printGiftMenu(EventPreviewDto eventPreviewDto) {
        System.out.println("<증정 메뉴>");
        System.out.println(eventPreviewDto.getGiftMenu());
    }

    public void printBenefitDetails(EventPreviewDto eventPreviewDto) {
        System.out.println("<혜택 내역>");
        System.out.println(eventPreviewDto.getBenefitDetails());
    }

    public void printBenefitAmount(EventPreviewDto eventPreviewDto) {
        System.out.println("<총혜택 금액>");
        System.out.println(eventPreviewDto.getBenefitAmount());
    }

    public void printPaymentAmount(EventPreviewDto eventPreviewDto) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(eventPreviewDto.getPaymentAmount());
    }

    public void printEventBadge(EventPreviewDto eventPreviewDto) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(eventPreviewDto.getEventBadge());
    }
}
