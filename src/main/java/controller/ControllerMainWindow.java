package controller;

import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.graphs.Graph;
import model.graphs.Plan;
import model.graphs.pathfinding.BranchAndBound;
import model.graphs.pathfinding.SimulatedAnnealing;
import model.graphs.pathfinding.TSP;
import model.graphs.pathfinding.TwoOpt;
import util.XMLParser;
import view.MainWindow;

import java.io.File;
import java.sql.SQLOutput;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The Controller of the MainWindow view. Receive information from the view
 * and compute the response.
 */
public class ControllerMainWindow {
    private MainWindow mainWindow;
    private Plan planData;
    private DeliveryTour deliveryTour;
    private Graph graph;

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
        PlanningRequest request;
        try{
            request = xmlParser.readRequests(xmlFile.getPath());
        }
        catch(Exception e){
            request = null;
            String msg = "Error importing tour: "+e.getMessage();
            System.out.println(msg);
            showMessageDialog(mainWindow,msg);
            return;
        }


        if(request!=null) {
            try {
                planData.setPlanningRequest(request);
            } catch (Exception e) {
                String msg = "Error importing tour: "+e.getMessage();
                System.out.println(msg);
                showMessageDialog(mainWindow,msg);
                return;
            }




            mainWindow.setPlanData(planData);
            TSP tsp = new SimulatedAnnealing(mainWindow);
            this.graph = Graph.generateCompleteGraphFromPlan(planData);
            // Calling TSP to calculate the best tour
            PlanningRequest finalRequest = request;
            if(graph != null){
                // Calling TSP to calculate the best tour
                new Thread(() -> tsp.searchSolution(100000,graph,finalRequest)).start();
            }


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
    }

    /**
     * Method called when we remove a request
     * @param request the request to delete
     * @param shouldChangeTour if we need to change the tour or not
     */

    public void removeRequest(Request request, boolean shouldChangeTour){
        PlanningRequest planningRequest = planData.getPlanningRequest();
        if(!shouldChangeTour){
            planningRequest.removeRequest(request);
        }
        else{
            planData.removeRequestAndChangeTour(request,graph);
        }
        // Updates the map to not have icons of the removed request
        mainWindow.setPlanData(planData);
        // Recalculate times
        planningRequest.calculateTimes(planData.getDeliveryTour());
        mainWindow.showSummary(planData.getPlanningRequest());
    }
}
