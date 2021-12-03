package controller;

import javafx.util.Pair;
import model.*;
import model.graphs.Graph;
import model.graphs.Plan;
import model.graphs.pathfinding.Dijkstra;
import model.graphs.pathfinding.TSP;
import model.graphs.pathfinding.TSP1;
import util.XMLParser;
import view.MainWindow;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        graph = null;
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
        TSP tsp = new TSP1();
        graph = Graph.generateCompleteGraphFromPlan(planData);
        tsp.searchSolution(20000,graph,request);
        DeliveryTour deliveryTour = tsp.getDeliveryTour();
        this.deliveryTour = deliveryTour;
        mainWindow.setDeliveryTour(deliveryTour);
        // System.out.println(request);
        mainWindow.setPlanningRequest(request);

        calculateTimes();
        mainWindow.showSummary(planData.getPlanningRequest());
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
     * Method that calculate for each request(pickup and delivery) when it will pass
     *
     */

    public void calculateTimes(){
        PlanningRequest planningRequest = planData.getPlanningRequest();
        List<Segment> segmentList = deliveryTour.getSegmentList();
        String departureTime = planningRequest.getDepartureTime();
        LocalTime currentTime = LocalTime.parse(departureTime);
        // Speed of the cyclist in m/s
        float speed = (float)(15/3.6) ;

        List<Request> requests = planningRequest.getRequests();

        for (Segment segment: segmentList){
            String origin = segment.getOrigin();

            // The origin of the segment is a pickup
            Request requestPick = requests.stream().filter(request->request.getPickupId().equals(origin)).findFirst().orElse(null);
            if(requestPick!=null){
                requestPick.setPickupTimePassage(currentTime.toString());
                currentTime = currentTime.plusSeconds(requestPick.getPickupDuration());
            }

            // The origin of the segment is a delivery
            Request requestDelivery = requests.stream().filter(request->request.getDeliveryId().equals(origin)).findFirst().orElse(null);
            if(requestDelivery!=null){
                requestDelivery.setDeliveryTimePassage(currentTime.toString());
                currentTime = currentTime.plusSeconds(requestDelivery.getDeliveryDuration());
            }


            // Time needed in seconds to go through the segment
            int segmentDuration = (int) (segment.getLength()/speed);
            currentTime = currentTime.plusSeconds(segmentDuration);
        }

        String finishTime = currentTime.toString();
        planningRequest.setFinishTime(finishTime);
    }

    public void addNewRequest(String pickupId, String pickupDuration, String deliveryId, String deliveryDuration) {
        try{
            int deliveryDurationInt = Integer.parseInt(deliveryDuration);
            int pickupDurationInt = Integer.parseInt(pickupDuration);


            Intersection pickupPlace = planData.getIntersectionMap().get(pickupId);
            System.out.println(pickupPlace);
            Intersection deliveryPlace = planData.getIntersectionMap().get(deliveryId);
            System.out.println(deliveryPlace);
            if (pickupPlace != null && deliveryPlace != null){
                Request newRequest = new Request(pickupId, deliveryId,pickupDurationInt,deliveryDurationInt);
                //TODO : create the planning request if it doesn't exist
                planData.getPlanningRequest().addRequest(newRequest);
                System.out.println(planData.getPlanningRequest());
                mainWindow.setPlanningRequest(planData.getPlanningRequest());
                mainWindow.showSummary(planData.getPlanningRequest());
                String lastIntersectionId = deliveryTour.getLastIntersectionId();



            }
            if(pickupPlace == null){
                System.out.println("Yooo renseigne la pickup place man");
            }
            if(deliveryPlace== null){
                System.out.println("Yooo renseigne la delivery place man");
            }

        }catch (Exception e){
            //TODO : manage different exceptions like conversion or not found id
            System.out.println(e);
        }
    }
}
