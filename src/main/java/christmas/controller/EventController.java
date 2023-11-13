package christmas.controller;

import christmas.domain.customer.VisitDate;
import christmas.domain.restaurant.Orders;
import christmas.domain.service.EventService;
import christmas.dto.EventPreviewDto;
import christmas.util.ErrorMessage;
import christmas.view.InputView;
import christmas.view.OutputView;

public class EventController {

    private final InputView inputView;
    private final OutputView outputView;

    private final EventService eventService;

    public EventController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.eventService = new EventService();
    }

    public void startEventPlanner() {
        outputView.printWelcome();

        VisitDate visitDate = retryInputForValidVisitDate();
        Orders orders = retryInputForValidOrders();
        previewEventBenefit(visitDate, orders);
    }

    private void previewEventBenefit(VisitDate visitDate, Orders orders) {
        EventPreviewDto eventPreviewDto = eventService.getEventPreviewDto(visitDate, orders);

        outputView.printPreviewMessage(visitDate);
        outputView.printMenu(eventPreviewDto);
        outputView.printOrderAmount(eventPreviewDto);
        outputView.printGiftMenu(eventPreviewDto);
        outputView.printBenefitDetails(eventPreviewDto);
        outputView.printBenefitAmount(eventPreviewDto);
        outputView.printPaymentAmount(eventPreviewDto);
        outputView.printEventBadge(eventPreviewDto);
    }

    private Orders retryInputForValidOrders() {
        while (true) {
            try {
                return inputView.readOrders();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(ErrorMessage.INVALID_INPUT_ORDER);
            }
        }
    }

    private VisitDate retryInputForValidVisitDate() {
        while (true) {
            try {
                return inputView.readDate();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(ErrorMessage.INVALID_INPUT_VISIT_DATE);
            }
        }
    }
}
