package View;

import controller.ControllerMainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class ButtonListenerMainWindow implements ActionListener {
    private ControllerMainWindow controllerMainWindow;
    private MainWindow mainWindow;

    public ButtonListenerMainWindow(ControllerMainWindow controllerMainWindow, MainWindow mainWindow) {
        this.controllerMainWindow = controllerMainWindow;
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Import tour":
                System.out.println("Importer un tour");
                JFileChooser j = new JFileChooser();
                int returnVal = j.showOpenDialog(this.mainWindow);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    System.out.println("You chose to open this file: " +
                            j.getSelectedFile().getName());
                }

                break;
            case "Ajouter livraison":System.out.println("Ajouter livraison");break;
            default : System.out.println("Not Implemented");break;
        }
    }
}
