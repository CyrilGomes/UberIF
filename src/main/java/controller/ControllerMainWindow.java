package controller;

import model.PlanningRequest;
import model.graphs.Graph;
import model.graphs.Plan;
import util.XMLParser;
import view.MainWindow;

import java.io.File;

/**
 * The Controller of the MainWindow view. Receive information from the view
 * and compute the response.
 */
public class ControllerMainWindow {
    private MainWindow mainWindow;

    /**
     * The constructor of the class.
     * @param mainWindow the window that it must control.
     */
    public ControllerMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    /**
     * Method that take a file of requests and compute the optimal tour.
     * Update the Plan accordingly.
     * @param xmlFile the file containing the delivery points
     *
     */
    public void importTour(File xmlFile){
        System.out.println("read requests");
        /*call the algorithms for calculation the tour*/
        XMLParser xmlParser = new XMLParser();
        PlanningRequest request = xmlParser.readRequests(xmlFile.getPath());
        System.out.println(request);
    }

    /**
     * Method that take a file representing a map, create the object
     * and finally ask the MainWindow to display it.
     * @param file the file read.
     */
    public void importMap(File file){
        XMLParser parser = new XMLParser();
        Graph graph = parser.readMap(file.getAbsolutePath());
        Plan planData = new Plan(graph.getIntersectionMap(), graph.getAdjacentsMap(), graph.getSegmentMap(), null,null);
        mainWindow.setPlanData(planData);
    }
}
