package controller;

import javafx.util.Pair;
import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Plan;
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
        TSP tsp = new TSP1();
        Graph graph = Graph.generateCompleteGraphFromPlan(planData);
        tsp.searchSolution(20000,graph,request);
        DeliveryTour deliveryTour = tsp.getDeliveryTour();
        this.deliveryTour = deliveryTour;
        mainWindow.setDeliveryTour(deliveryTour);
        // System.out.println(request);
        mainWindow.setPlanningRequest(request);

        calculateTimes();
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
        LocalTime currentTime = LocalTime.parse(formatDepartureTime(departureTime));
        // Speed of the cyclist in m/s
        float speed = (float)(15/3.6) ;

        List<Request> requests = planningRequest.getRequests();

        for (Segment segment: segmentList){
            String origin = segment.getOrigin();

            // The origin of the segment is a pickup
            Request requestPick = requests.stream().filter(request->request.getPickupId().equals(origin)).findFirst().orElse(null);
            if(requestPick!=null){
                requestPick.setPickupTime(currentTime.toString());
                currentTime = currentTime.plusSeconds(requestPick.getPickupDuration());
            }

            // The origin of the segment is a delivery
            Request requestDelivery = requests.stream().filter(request->request.getDeliveryId().equals(origin)).findFirst().orElse(null);
            if(requestDelivery!=null){
                requestDelivery.setDeliveryTime(currentTime.toString());
                currentTime = currentTime.plusSeconds(requestDelivery.getDeliveryDuration());
            }


            // Time needed in seconds to go through the segment
            int segmentDuration = (int) (segment.getLength()/speed);
            currentTime = currentTime.plusSeconds(segmentDuration);
        }

        String finishTime = currentTime.toString();
        planningRequest.setFinishTime(finishTime);
        System.out.println("<<<<<<< Showing times of each request");
        for(Request request: planningRequest.getRequests()){
            System.out.println("PickupTime: "+request.getPickupTime()+"  DeliveryTime: "+request.getDeliveryTime());
        }
        System.out.println("<<<Final time of return to depot: "+finishTime);
    }

    public String formatDepartureTime(String departureTime){
        return "0" + departureTime.replaceAll(":",":0");
    }
}
