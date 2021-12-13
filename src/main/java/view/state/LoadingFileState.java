package view.state;

import view.MainWindow;

public class LoadingFileState implements State {
    /**
     * Notifies the context class (MainWindow) and changes its state to a
     * system loading of a file.
     * @param context The context class (MainWindow)
     */
    @Override
    public void execute(final MainWindow context) {
        context.setModifyPlanData(false);
        context.setCurrentState(this);
        context.setSystemInfoText("Loading file...");
    }
}
