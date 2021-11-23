package view;

import controller.ControllerMainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerMainWindow implements ActionListener {
    private ControllerMainWindow controllerMainWindow;
    private MainWindow mainWindow;
    private JFileChooser chooser;

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
                chooser = new JFileChooser("files");
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("xml files (.xml)", "xml"));
                int returnVal = chooser.showOpenDialog(this.mainWindow);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    System.out.println("You chose to open this file: "+chooser.getSelectedFile());
                    controllerMainWindow.importTour(chooser.getSelectedFile());
                }

                    controllerMainWindow.importTour();
                }
                break;
            case "Import map":
                returnVal = fileChooser.showOpenDialog(this.mainWindow);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    controllerMainWindow.importMap(fileChooser.getSelectedFile());
                }
                break;
            case "Ajouter livraison":System.out.println("Ajouter livraison : not implemented");break;
            default : System.out.println("Not Implemented");break;
        }
    }
}
