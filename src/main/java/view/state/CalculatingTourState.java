package view.state;

import view.MainWindow;

public class CalculatingTourState implements State {
    /**
     * Notifies the context class (MainWindow) and changes its state to a
     * system calculation of a tour.
     * @param context The context class (MainWindow)
     */
    @Override
    public void execute(final MainWindow context) {
        context.setModifyPlanData(false);
        context.setCurrentState(this);
        context.setSystemInfoText("Calculating best route from requests...");
    }
}
