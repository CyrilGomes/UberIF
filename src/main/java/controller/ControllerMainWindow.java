package controller;


import model.*;
import model.graphs.pathfinding.*;
import observer.Observable;
import model.DeliveryTour;
import model.Intersection;
import model.PlanningRequest;
import model.Request;
import model.graphs.Graph;
import model.graphs.Plan;
import model.graphs.pathfinding.SimulatedAnnealing;
import model.graphs.pathfinding.TSP;
import util.XMLParser;
import view.MainWindow;
import view.state.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The Controller of the MainWindow view. Receive information from the view
 * and compute the response.
 */
public class ControllerMainWindow extends Observable {
    private MainWindow mainWindow;
    private Plan planData;
    private Graph graph;
    private TSP tsp;
    private History history;
    private DeliveryTour deliveryTour;

    /**
     * The constructor of the class.
     * @param mainWindow the window that it must control.
     */
    public ControllerMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        planData = null;
        history = new History();
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
        State loadingFileState = new LoadingFileState();
        loadingFileState.execute(mainWindow);
        XMLParser xmlParser = new XMLParser();
        PlanningRequest request;
        try{
            request = xmlParser.readRequests(xmlFile.getPath(),planData.getIntersectionMap());
            if(request!=null) {
                planData.setPlanningRequest(request);
                mainWindow.setPlanData(planData);
                State calculatingTourState = new CalculatingTourState();
                calculatingTourState.execute(mainWindow);
                tsp = new SimulatedAnnealing(mainWindow);
                this.graph = Graph.generateCompleteGraphFromPlan(planData);

                // Calling TSP to calculate the best tour
                PlanningRequest finalRequest = request;
                new Thread(() -> {
                    tsp.searchSolution(100000, graph, finalRequest);
                    history.registerCurrentState(planData, graph);
                    System.out.println(history);}).start();
                State readyState = new ReadyState();
                readyState.execute(mainWindow);

            }
        }
        catch(Exception e){
            String msg = "Error importing tour: "+e.getMessage();
            System.out.println(msg);
            showMessageDialog(mainWindow,msg);
            State readyState = new ReadyState();
            readyState.execute(mainWindow);
        }
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
            history.registerCurrentState(planData, graph);
            System.out.println(history);
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
                Graph newGraph = new Graph(graph);
                planData.setDeliveryTour(planData.getDeliveryTour().removeRequestAndChangeTour(request, newGraph));
            }
        }
        planningRequest.removeRequest(request);
        // Updates the map to not have icons of the removed request
        mainWindow.setPlanData(planData);
        // Recalculate times
        if(shouldChangeTour) {
            planningRequest.calculateTimes(planData.getDeliveryTour());
        }
        mainWindow.showDelivery(planData.getPlanningRequest());
        mainWindow.showSummary(planData.getPlanningRequest(),planData.getDeliveryTour());
        State readyState = new ReadyState();
        readyState.execute(mainWindow);
        history.registerCurrentState(planData,graph);
    }

    public Intersection getIntersectionFromId(String id){
        return planData.getIntersectionMap().get(id);
        
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
                DeliveryTour deliveryTour = planData.getDeliveryTour();
                String lastIntersectionId = deliveryTour.getLastIntersectionId();
                deliveryTour.addNextPoint(pickupId);
                deliveryTour.addNextPoint(deliveryId);
                Dijkstra dijkstra = new Dijkstra();
                Graph newGraph = new Graph();
                List<String> pointsOfInterests = new ArrayList<>();

                pointsOfInterests.add(pickupId);
                dijkstra.executeAlgorithm(planData,lastIntersectionId,newGraph,pointsOfInterests);

                pointsOfInterests.add(deliveryId);
                dijkstra.executeAlgorithm(planData,pickupId,newGraph,pointsOfInterests);

                String startId = planningRequest.getStartId();
                pointsOfInterests.add(startId);
                dijkstra.executeAlgorithm(planData,deliveryId,newGraph,pointsOfInterests);

                List<Edge> listEdges = new ArrayList<>();
                listEdges.add(newGraph.getEdge(lastIntersectionId,pickupId));
                listEdges.add(newGraph.getEdge(pickupId,deliveryId));
                listEdges.add(newGraph.getEdge(deliveryId,startId));

                deliveryTour.removeListSegment(lastIntersectionId);
                for (Edge edge: listEdges) {
                    deliveryTour.addListSegment(edge.getSegmentList());
                }

                planningRequest.calculateTimes(deliveryTour);
                planData.setDeliveryTour(deliveryTour);
                deliveryTour.addObserver(mainWindow);
                mainWindow.showSummary(planData.getPlanningRequest(),planData.getDeliveryTour());
                mainWindow.showDelivery(planData.getPlanningRequest());
                deliveryTour.notifyObservers(deliveryTour);
                history.registerCurrentState(planData, graph);
                System.out.println(history);

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
    public void undo(){
        Plan plan = history.undo();
        this.planData = plan;
        System.out.println(planData);
        System.out.println(plan);
        System.out.println(history);
        mainWindow.setPlanData(plan);
        mainWindow.showSummary(plan.getPlanningRequest(),plan.getDeliveryTour());
        mainWindow.showDelivery(planData.getPlanningRequest());
        State readyState = new ReadyState();
        readyState.execute(mainWindow);


    }
    public void redo(){
        Plan plan = history.redo();
        this.planData = plan;
        System.out.println(planData);
        System.out.println(plan);
        System.out.println(history);
        mainWindow.setPlanData(plan);
        mainWindow.showSummary(plan.getPlanningRequest(), plan.getDeliveryTour());
        mainWindow.showDelivery(plan.getPlanningRequest());
        State readyState = new ReadyState();
        readyState.execute(mainWindow);
        
    }
}
