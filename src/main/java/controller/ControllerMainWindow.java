package controller;

import javafx.util.Pair;
import model.*;
import model.graphs.Graph;
import model.graphs.Plan;
import model.graphs.pathfinding.*;
import observer.Observable;
import util.XMLParser;
import view.MainWindow;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Controller of the MainWindow view. Receive information from the view
 * and compute the response.
 */
public class ControllerMainWindow extends Observable {
    private MainWindow mainWindow;
    private Plan planData;
    private Graph graph;
    private TSP tsp;

    /**
     * The constructor of the class.
     * @param mainWindow the window that it must control.
     */
    public ControllerMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        planData = null;
        graph = null;
        tsp = null;
        addObserver(mainWindow);
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


        mainWindow.setPlanData(planData);
        tsp = new SimulatedAnnealing(mainWindow);
        Graph graph = Graph.generateCompleteGraphFromPlan(planData);

        // Calling TSP to calculate the best tour
        new Thread(new Runnable() {
            @Override
            public void run() {
                tsp.searchSolution(100000,graph,request);
            }
        }).start();

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
        Plan plan = parser.readMap(file.getAbsolutePath());
        planData = plan;
        mainWindow.setPlanData(plan);
    }

    /**
     * Method called when we remove a request
     * @param request the request to delete
     */

    public void removeRequest(Request request){
        PlanningRequest planningRequest = planData.getPlanningRequest();
        planningRequest.removeRequest(request);
        // Updates the map to not have icons of the removed request
        mainWindow.setPlanData(planData);
        // Recalculate times
        planningRequest.calculateTimes(planData.getDeliveryTour());
        mainWindow.showSummary(planData.getPlanningRequest());
    }

    /**
     * Add a request to the planning request and update the delivery tour accordingly.
     * @param pickupId the pickup place of the request.
     * @param pickupDuration the duration of the pickup.
     * @param deliveryId the delivery place of the request.
     * @param deliveryDuration the duration of the delivery.
     */
    public void addNewRequest(String pickupId, String pickupDuration, String deliveryId, String deliveryDuration) {
        try{
            int deliveryDurationInt = Integer.parseInt(deliveryDuration);
            int pickupDurationInt = Integer.parseInt(pickupDuration);

            Intersection pickupPlace = planData.getIntersectionMap().get(pickupId);
            Intersection deliveryPlace = planData.getIntersectionMap().get(deliveryId);

            if (pickupPlace != null && deliveryPlace != null){
                Request newRequest = new Request(pickupId, deliveryId,pickupDurationInt,deliveryDurationInt);
                //TODO : create the planning request if it doesn't exist
                PlanningRequest planningRequest = planData.getPlanningRequest();
                planningRequest.addRequest(newRequest);
                planData.setPlanningRequest(planningRequest);
                System.out.println("Planning :"+planningRequest);
                DeliveryTour deliveryTour = tsp.getDeliveryTour();
                String lastIntersectionId = deliveryTour.getLastIntersectionId();
                deliveryTour.addNextPoint(pickupId);
                deliveryTour.addNextPoint(deliveryId);
                Dijkstra dijkstra = new Dijkstra();
                Graph newGraph = new Graph();
                List<String> pointsOfInterests = new ArrayList<>();
                pointsOfInterests.add(pickupId);



                dijkstra.executeAlgorithm(planData,lastIntersectionId,newGraph,pointsOfInterests);

                pointsOfInterests.remove(0);
                pointsOfInterests.add(deliveryId);
                dijkstra.executeAlgorithm(planData,pickupId,newGraph,pointsOfInterests);

                pointsOfInterests.remove(0);
                String startId = planningRequest.getStartId();
                pointsOfInterests.add(startId);
                dijkstra.executeAlgorithm(planData,deliveryId,newGraph,pointsOfInterests);

                List<Edge> listEdges = new ArrayList<>();
                listEdges.add(newGraph.getEdge(lastIntersectionId,pickupId));
                listEdges.add(newGraph.getEdge(pickupId,deliveryId));
                listEdges.add(newGraph.getEdge(deliveryId,startId));

                for (Edge edge: listEdges) {
                    deliveryTour.addListSegment(edge.getSegmentList());
                }

                planningRequest.calculateTimes(deliveryTour);
                notifyObservers(deliveryTour);

            }
            if(pickupPlace == null){
                System.out.println("Pickup place not defined");
            }
            if(deliveryPlace== null){
                System.out.println("Delivery place not defined");
            }

        }catch (Exception e){
            //TODO : manage different exceptions like conversion or not found id
            System.out.println(e);
        }
    }
}
