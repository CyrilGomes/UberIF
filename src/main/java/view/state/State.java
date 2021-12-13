package view.state;

import view.MainWindow;

public interface State {
    /**
     * Notifies the context class (MainWindow) and changes its state.
     * @param context The context class (MainWindow)
     */
    void execute(MainWindow context);
}
