package view.state;

import view.MainWindow;

public class CalculatingTimesState implements State {
    /**
     * Notifies the context class (MainWindow) and changes its state to a
     * system calculation of times.
     * @param context The context class (MainWindow)
     */
    @Override
    public void execute(final MainWindow context) {
        context.setModifyPlanData(false);
        context.setCurrentState(this);
        context.setSystemInfoText("Calculating delivery times...");
    }
}
