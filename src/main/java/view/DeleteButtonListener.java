package view;

import controller.ControllerMainWindow;
import model.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener {
    private ControllerMainWindow controllerMainWindow;
    private Request request;

    public DeleteButtonListener(ControllerMainWindow controllerMainWindow, Request request) {
        this.controllerMainWindow = controllerMainWindow;
        this.request = request;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Removing request...");
        controllerMainWindow.removeRequest(request);
    }
}
