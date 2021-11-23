package View;

import controller.ControllerMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerMainWindow implements ActionListener {
    private ControllerMainWindow controllerMainWindow;
    private MainWindow mainWindow;
    private JFileChooser fileChooser;

    public ButtonListenerMainWindow(ControllerMainWindow controllerMainWindow, MainWindow mainWindow) {
        this.controllerMainWindow = controllerMainWindow;
        this.mainWindow = mainWindow;
        fileChooser = new JFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal;
        switch(e.getActionCommand()){
            case "Import tour":
                System.out.println("Importer un tour");
                returnVal = fileChooser.showOpenDialog(this.mainWindow);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    System.out.println("You chose to open this file: " +
                            fileChooser.getSelectedFile().getName());

                    controllerMainWindow.importTour();
                }
                break;
            case "Import map":
                returnVal = fileChooser.showOpenDialog(this.mainWindow);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    controllerMainWindow.importMap(fileChooser.getSelectedFile());
                }
                break;
            case "Ajouter livraison":System.out.println("Ajouter livraison");break;
            case "Exit": System.exit(0);
            default : System.out.println("Not Implemented");break;
        }
    }
}
