package view.state;

import view.MainWindow;

public class ReadyState implements State {
    /**
     * Notifies the context class (MainWindow) and changes its state to ready.
     * @param context The context class (MainWindow)
     */
    @Override
    public void execute(final MainWindow context) {
        context.setModifyPlanData(true);
        context.setCurrentState(this);
        context.setSystemInfoText("Ready");
    }
}
