package model.graphs.pathfinding;

import javafx.util.Pair;
import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Key;
import model.graphs.Plan;

import java.util.*;

public class TSP {
    public Graph generateTsmCompleteGraph(Plan plan){
        Dijkstra dijkstra = new Dijkstra();
        List<String> pointsOfInterests = new ArrayList<>();

        PlanningRequest planningRequest = plan.getPlanningRequest();
        pointsOfInterests.add(planningRequest.getStartId());
        for (Request request: planningRequest.getRequests()) {
            pointsOfInterests.add(request.getDeliveryId());
            pointsOfInterests.add(request.getPickupId());
        }

        Graph newGraph = new Graph();


        for (String currentPoint:pointsOfInterests) {

            dijkstra.executeAlgorithm(plan,currentPoint,newGraph,pointsOfInterests );
        }


        return newGraph;

    }

    public Pair<Float, List<String>> allTours(Graph graph,PlanningRequest planningRequest){

        String sourceVertex = planningRequest.getStartId();
        List<String> visited = new ArrayList<String>(graph.getNbVertices());
        visited.add(sourceVertex);
        List<String> unvisited = new ArrayList<String>(graph.getNbVertices()-1);
        Set<String> vertices = graph.getVertices();
        unvisited.addAll(vertices);
        unvisited.remove(sourceVertex);
        List<String> deliveryPoints = new ArrayList<>();
        List<String> pickupPoints = new ArrayList<>();
        List<Request> requests = planningRequest.getRequests();
        for (Request request:requests) {
            deliveryPoints.add(request.getDeliveryId());
            pickupPoints.add(request.getPickupId());
        }


        return allTours(graph, sourceVertex,sourceVertex,visited,unvisited,pickupPoints,deliveryPoints);
    }

    public DeliveryTour generatedDeliveryTour(Graph graph,Pair<Float, List<String>> bestRoute){
        List<Segment> segmentList = new ArrayList<>();
        List<String> tour = bestRoute.getValue();
        int listSize = tour.size();
        for (int i = 1; i < listSize; i++){
            Edge edge = graph.getEdge(tour.get(i-1),tour.get(i));
            segmentList.addAll(edge.segmentList);
        }
        Edge edge = graph.getEdge(tour.get(listSize-1),tour.get(0));
        segmentList.addAll(edge.segmentList);

        return new DeliveryTour(segmentList,bestRoute.getKey());
    }
    public float calculateRouteLength(Graph graph, List<String> route){
        int listSize = route.size();
        float counter = 0;
        for (int i = 1; i < listSize; i++){
            Edge edge = graph.getEdge(route.get(i-1),route.get(i));
            counter += edge.duration;
        }
        graph.getEdge(route.get(listSize-1),route.get(0));
        counter += graph.getEdge(route.get(listSize-1),route.get(0)).duration;
        return counter;
    }



    static int count = 0;
    public Pair<Float, List<String>> allTours(Graph g,
                                              String sourceVertex,
                                              String currentVertex,
                                              List<String> visited,
                                              List<String> unvisited,
                                              List<String> pickupPoints,
                                              List<String> deliveryPoints
                                             ){

        count++;
        //System.out.println((count/5040.0) * 100.0);

        Pair<Float, List<String>> bestRoute = null;

        if(unvisited.size() == 0){
            if(g.isArc(currentVertex,sourceVertex)){
                float length = calculateRouteLength(g,visited);
                bestRoute = new Pair<>(length, visited);

            }
        } else{


            for (String nextVertex : unvisited) {
                if(deliveryPoints.contains(nextVertex)){
                    int id = deliveryPoints.indexOf(nextVertex);
                    if(!visited.contains(pickupPoints.get(id))){
                        continue;
                    }
                }

                if(g.isArc(currentVertex,nextVertex)){
                    Pair<Float, List<String>> currentRoute;
                    List<String> newVisited = new ArrayList<>(visited);
                    List<String> newUnVisited = new ArrayList<>(unvisited);

                    newVisited.add(nextVertex);
                    newUnVisited.remove(nextVertex);
                    if(bestRoute == null ){
                        bestRoute = allTours(g,sourceVertex, nextVertex ,newVisited,newUnVisited, pickupPoints,deliveryPoints);
                    }

                    currentRoute = allTours(g,sourceVertex, nextVertex ,newVisited,newUnVisited,pickupPoints,deliveryPoints);
                    if(currentRoute.getKey() > bestRoute.getKey()){
                        bestRoute = currentRoute;
                    }
                }
            }
        }

        return bestRoute;
    }

}
