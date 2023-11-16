package christmas.view;

import christmas.domain.customer.VisitDate;
import christmas.dto.EventPreviewDto;
import christmas.util.ErrorMessage;

public class OutputView {

    public void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }

    public void printWelcome() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    public void printPreviewMessage(VisitDate visitDate) {
        String message = String.format("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", visitDate.value());
        System.out.println(message);
        System.out.println();
    }

    public void printMenu(EventPreviewDto eventPreview) {
        printSection("<주문 메뉴>", eventPreview.getOrderMenu());
    }

    public void printTotalOrderAmount(EventPreviewDto eventPreviewDto) {
        printSection("<할인 전 총주문 금액>", eventPreviewDto.getTotalOrderAmount());
    }

    public void printGiftMenu(EventPreviewDto eventPreviewDto) {
        printSection("<증정 메뉴>", eventPreviewDto.getGiftMenu());
    }

    public void printBenefitDetails(EventPreviewDto eventPreviewDto) {
        printSection("<혜택 내역>", eventPreviewDto.getBenefitDetails());
    }

    public void printBenefitAmount(EventPreviewDto eventPreviewDto) {
        printSection("<총혜택 금액>", eventPreviewDto.getBenefitAmount());
    }

    public void printPaymentAmount(EventPreviewDto eventPreviewDto) {
        printSection("<할인 후 예상 결제 금액>", eventPreviewDto.getPaymentAmount());
    }

    public void printEventBadge(EventPreviewDto eventPreviewDto) {
        printSection("<12월 이벤트 배지>", eventPreviewDto.getEventBadge());
    }

    private void printSection(String title, String content) {
        System.out.println(title);
        System.out.println(content);
        System.out.println();
    }
}
