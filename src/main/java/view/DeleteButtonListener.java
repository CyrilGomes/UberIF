package view;

import controller.ControllerMainWindow;
import model.Request;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener {
    /**
     * Controller.
     */
    private ControllerMainWindow controllerMainWindow;
    /**
     * Request associated to the button.
     */
    private Request request;
    /**
     * boolean to know if the tour has to change.
     */
    private boolean shouldChangeTour;

    /** Constructor
     * @param controllerMainWindow the main controller
     * @param request the request corresponding to this button
     * @param shouldChangeTour if it's a button that also changes tour or not
     */
    public DeleteButtonListener(ControllerMainWindow controllerMainWindow, Request request, boolean shouldChangeTour) {
        this.controllerMainWindow = controllerMainWindow;
        this.request = request;
        this.shouldChangeTour = shouldChangeTour;
    }

    /** Override of the actionPerformed
     * @param e event captured by the button
     */
    @Override
    public void actionPerformed(ActionEvent e){
        // Remove request without changing tour
        if(!shouldChangeTour) {
            System.out.println("Removing request without changing tour...");
            controllerMainWindow.removeRequest(request,false);
        }
        else{
            System.out.println("Removing request with changing the tour...");
            controllerMainWindow.removeRequest(request,true);
        }
    }
}
