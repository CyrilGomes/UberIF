package model.graphs.pathfinding;

import javafx.util.Pair;
import model.DeliveryTour;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Key;
import model.graphs.Plan;

import java.util.*;

public class TSP {
    public Graph generateTsmCompleteGraph(Plan graph, String sourceNode){
        Dijkstra dijkstra = new Dijkstra();
        List<String> pointsOfInterests = new ArrayList<>();

        pointsOfInterests.addAll(graph.getDeliveries());
        pointsOfInterests.addAll(graph.getPickups());
        pointsOfInterests.add(sourceNode);
        Graph newGraph = new Graph();
        /*
        dijkstra.executeAlgorithm(graph,"6",newGraph,pointsOfInterests );
        dijkstra.executeAlgorithm(graph,"4",newGraph,pointsOfInterests );
        dijkstra.executeAlgorithm(graph,"4",newGraph,pointsOfInterests );


        System.out.println(newGraph.keySet());      */


        for (String currentPoint:pointsOfInterests) {

            dijkstra.executeAlgorithm(graph,currentPoint,newGraph,pointsOfInterests );
        }


        return newGraph;

    }

    public Pair<Float, List<String>> allTours(Graph graph, String sourceVertex){
        List<String> visited = new ArrayList<String>(graph.getNbVertices());
        visited.add(sourceVertex);
        List<String> unvisited = new ArrayList<String>(graph.getNbVertices()-1);
        Set<String> vertices = graph.getVertices();
        unvisited.addAll(vertices);
        unvisited.remove(sourceVertex);


        return allTours(graph, sourceVertex,sourceVertex,visited,unvisited);
    }

    public DeliveryTour generatedDeliveryTour(Graph graph,Pair<Float, List<String>> bestRoute){
        List<Segment> segmentList = new ArrayList<>();
        List<String> tour = bestRoute.getValue();
        int listSize = tour.size();
        for (int i = 1; i < listSize; i++){
            Edge edge = graph.getEdge(tour.get(i-1),tour.get(i));
            segmentList.addAll(edge.segmentList);
        }
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



    public Pair<Float, List<String>> allTours(Graph g,
                                              String sourceVertex,
                                              String currentVertex,
                                              List<String> visited,
                                              List<String> unvisited
                                             ){

        Pair<Float, List<String>> bestRoute = null;

        if(unvisited.size() == 0){
            if(g.isArc(currentVertex,sourceVertex)){
                float length = calculateRouteLength(g,visited);
                bestRoute = new Pair<>(length, visited);

            }
        } else{


            for (String nextVertex : unvisited) {



                if(g.isArc(currentVertex,nextVertex)){
                    Pair<Float, List<String>> currentRoute;
                    List<String> newVisited = new ArrayList<>(visited);
                    List<String> newUnVisited = new ArrayList<>(unvisited);

                    newVisited.add(nextVertex);
                    newUnVisited.remove(nextVertex);
                    if(bestRoute == null ){
                        bestRoute = allTours(g,sourceVertex, nextVertex ,newVisited,newUnVisited);
                    }

                    currentRoute = allTours(g,sourceVertex, nextVertex ,newVisited,newUnVisited);
                    if(currentRoute.getKey() > bestRoute.getKey()){
                        bestRoute = currentRoute;
                    }


                }

            }
        }

        return bestRoute;
    }

}
