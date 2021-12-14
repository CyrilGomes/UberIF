package view.state;

import view.MainWindow;
/**
 * State CalculatingTour.
 */
public class CalculatingTourState implements State {
    @Override
    public void execute(MainWindow context) {
        context.setModifyPlanData(false);
        context.setCurrentState(this);
        context.setSystemInfoText("Calculating best route from requests...");
    }
}
