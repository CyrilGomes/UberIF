package controller;

import javafx.util.Pair;
import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Plan;
import model.graphs.pathfinding.SimulatedAnnealing;
import model.graphs.pathfinding.TSP;
import model.graphs.pathfinding.TSP1;
import util.XMLParser;
import view.MainWindow;
import view.state.*;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The Controller of the MainWindow view. Receive information from the view
 * and compute the response.
 */
public class ControllerMainWindow {
    private MainWindow mainWindow;
    private Plan planData;
    private Graph graph;

    /**
     * The constructor of the class.
     * @param mainWindow the window that it must control.
     */
    public ControllerMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        planData = null;
    }

    /**
     * Method that take a file of requests and compute the optimal tour.
     * Update the Plan accordingly.
     * @param xmlFile the file containing the delivery points
     *
     */
    public void importTour(File xmlFile){
        State loadingFileState = new LoadingFileState();
        loadingFileState.execute(mainWindow);
        XMLParser xmlParser = new XMLParser();
        PlanningRequest request;
        try{
            request = xmlParser.readRequests(xmlFile.getPath(),planData.getIntersectionMap());
        }
        catch(Exception e){
            request = null;
            String msg = "Error importing tour: "+e.getMessage();
            System.out.println(msg);
            showMessageDialog(mainWindow,msg);
        }

        if(request!=null) {
            planData.setPlanningRequest(request);
            mainWindow.setPlanData(planData);
            State calculatingTourState = new CalculatingTourState();
            calculatingTourState.execute(mainWindow);
            TSP tsp = new SimulatedAnnealing(mainWindow);
            this.graph = Graph.generateCompleteGraphFromPlan(planData);

            // Calling TSP to calculate the best tour
            PlanningRequest finalRequest = request;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tsp.searchSolution(100000, graph, finalRequest);
                }
            }).start();
        }


        //DeliveryTour deliveryTour = tsp.getDeliveryTour();
        //this.deliveryTour = deliveryTour;
        //mainWindow.setDeliveryTour(deliveryTour);
        // System.out.println(request);




    }

    /**
     * Method that take a file representing a map, create the object
     * and finally ask the MainWindow to display it.
     * @param file the file read.
     */
    public void importMap(File file){
        State loadingFileState = new LoadingFileState();
        loadingFileState.execute(mainWindow);
        XMLParser parser = new XMLParser();
        try {
            Plan plan = parser.readMap(file.getAbsolutePath());
            planData = plan;
            mainWindow.setPlanData(plan);
            mainWindow.clearPanels();
        }
        // Case where we fail to read the map
        catch(Exception e){
            String msg = "Error importing map: "+e.getMessage();
            System.out.println(msg);
            showMessageDialog(mainWindow,msg);
        }
        State readyState = new ReadyState();
        readyState.execute(mainWindow);
    }

    /**
     * Method called when we remove a request
     * @param request the request to delete
     * @param shouldChangeTour if we need to change the tour or not
     */

    public void removeRequest(Request request, boolean shouldChangeTour){
        PlanningRequest planningRequest = planData.getPlanningRequest();
        if (shouldChangeTour) {
            if (planningRequest.getRequests().size() == 1) {
                planData.getDeliveryTour().setSegmentList(new ArrayList<>());
            } else {
                planData.getDeliveryTour().removeRequestAndChangeTour(request, graph);
            }
        }
        planningRequest.removeRequest(request);
        // Updates the map to not have icons of the removed request
        mainWindow.setPlanData(planData);
        // Recalculate times
        planningRequest.calculateTimes(planData.getDeliveryTour());
        mainWindow.showSummary(planData.getPlanningRequest());
        State readyState = new ReadyState();
        readyState.execute(mainWindow);
    }
}
