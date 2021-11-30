package controller;

import javafx.util.Pair;
import model.DeliveryTour;
import model.PlanningRequest;
import model.graphs.Graph;
import model.graphs.Plan;
import model.graphs.pathfinding.TSP;
import util.XMLParser;
import view.MainWindow;

import java.io.File;
import java.util.List;

/**
 * The Controller of the MainWindow view. Receive information from the view
 * and compute the response.
 */
public class ControllerMainWindow {
    private MainWindow mainWindow;
    private Plan planData;
    private DeliveryTour deliveryTour;

    /**
     * The constructor of the class.
     * @param mainWindow the window that it must control.
     */
    public ControllerMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        planData = null;
        deliveryTour = null;
    }

    /**
     * Method that take a file of requests and compute the optimal tour.
     * Update the Plan accordingly.
     * @param xmlFile the file containing the delivery points
     *
     */
    public void importTour(File xmlFile){
        System.out.println("read requests");
        XMLParser xmlParser = new XMLParser();
        PlanningRequest request = xmlParser.readRequests(xmlFile.getPath());
        planData.setPlanningRequest(request);

        // Calling TSP to calculate the best tour
        TSP tsp = new TSP();
        Graph graph = tsp.generateTsmCompleteGraph(planData);
        Pair<Float, List<String>> result = tsp.allTours(graph,request);
        DeliveryTour deliveryTour = tsp.generatedDeliveryTour(graph,result);
        this.deliveryTour = deliveryTour;
        mainWindow.setDeliveryTour(deliveryTour);
        // System.out.println(request);
        mainWindow.setPlanningRequest(request);
    }

    /**
     * Method that take a file representing a map, create the object
     * and finally ask the MainWindow to display it.
     * @param file the file read.
     */
    public void importMap(File file){
        XMLParser parser = new XMLParser();
        Plan plan = parser.readMap(file.getAbsolutePath());
        planData = plan;
        mainWindow.setPlanData(plan);
    }
}
