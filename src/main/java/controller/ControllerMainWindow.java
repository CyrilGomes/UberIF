package controller;

import model.PlanningRequest;
import util.XMLParser;
import view.MainWindow;

import java.io.File;

public class ControllerMainWindow {
    private MainWindow mainWindow;
    public ControllerMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    /**
     * Method that take a delivery points file and compute the optimal tour.
     *
     * @param xmlFile the file containing the delivery points
     * @return a list of the point to visit in an optimal order. (yet to be implemented)
     */
    public void importTour(File xmlFile){
        System.out.println("read requests");
        /*call the algorithms for calculation the tour*/
        XMLParser xmlParser = new XMLParser();
        PlanningRequest request = xmlParser.readRequests(xmlFile.getPath());
        System.out.println(request);
    }
}
