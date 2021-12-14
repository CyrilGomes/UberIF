package view.state;

import view.MainWindow;

/**
 * Interface of State, used to implement the design pattern state.
 */
public interface State {
    void execute(MainWindow context);
}
