package christmas.controller;

import christmas.domain.restaurant.Orders;
import christmas.util.ErrorMessage;
import christmas.view.InputView;
import christmas.view.OutputView;

public class EventController {

    private final InputView inputView;
    private final OutputView outputView;

    public EventController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void startEventPlanner() {
        outputView.printWelcome();
        int visitDate = retryInputForValidVisitDate();
        Orders orders = retryInputForValidOrders();
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

    private int retryInputForValidVisitDate() {
        while (true) {
            try {
                return inputView.readDate();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(ErrorMessage.INVALID_INPUT_VISIT_DATE);
            }
        }
    }
}
