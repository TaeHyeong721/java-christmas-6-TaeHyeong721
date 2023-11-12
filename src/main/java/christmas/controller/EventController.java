package christmas.controller;

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
        int visitDate = repeatUntilValidVisitDate();
    }

    private int repeatUntilValidVisitDate() {
        while (true) {
            try {
                return inputView.readDate();
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }
}
