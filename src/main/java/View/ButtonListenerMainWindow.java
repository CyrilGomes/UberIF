package View;

import controller.ControllerMainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ButtonListenerMainWindow implements ActionListener {
    private ControllerMainWindow controllerMainWindow;

    public ButtonListenerMainWindow(ControllerMainWindow controllerMainWindow) {
        this.controllerMainWindow = controllerMainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Import tour":
                System.out.println("Importer un tour");
                Desktop desktop = null;
                File file = new File(System.getenv("programfiles"));
                try {
                    if (Desktop.isDesktopSupported()) {
                        desktop = Desktop.getDesktop();
                        desktop.open(file);
                    } else {
                        System.out.println("desktop is not supported");
                    }
                }catch (IOException error){

                }

                break;
            case "Ajouter livraison":System.out.println("Ajouter livraison");break;
            default : System.out.println("Not Implemented");break;
        }
    }
}
