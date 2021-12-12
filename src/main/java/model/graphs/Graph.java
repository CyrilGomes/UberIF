package model.graphs;

import model.PlanningRequest;
import model.Request;
import model.graphs.pathfinding.Dijkstra;
import model.graphs.pathfinding.Edge;

import java.util.*;

public class Graph {
    private Set<String> vertices;
    private HashMap<Key, Edge> edges;
    private float minCost;

    public static Graph generateCompleteGraphFromPlan(Plan plan){
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

            try{
                dijkstra.executeAlgorithm(plan,currentPoint,newGraph,pointsOfInterests );

            }catch (Exception e){
                System.err.println("Erreur lors de la création du graphe complet :"+ e.getMessage());
                return null;
            }
        }

        newGraph.calculateMinCost();
        return newGraph;
    }

    public float calculateMinCost(){
        minCost = Float.MAX_VALUE;
        for (Map.Entry<Key, Edge> entry : edges.entrySet()) {
            float duration = entry.getValue().getDuration();
            minCost = Math.min(minCost,duration);
        }
        return minCost;
    }
    public Graph() {
        vertices = new HashSet<>();
        edges = new HashMap<>();
    }
    public Graph(Graph graphCopy){
        if(graphCopy.vertices != null){
            vertices = new HashSet<>(graphCopy.vertices);
        }
        if(graphCopy.edges != null){
            edges = new HashMap<>();
            graphCopy.edges.forEach(((key, edge) -> {
                this.edges.put(new Key(key), new Edge(edge));
            }));
        }

    }

    public float getCost(String origin, String destination){
        Edge edge = edges.get(new Key(origin,destination));
        if(edge == null) {
            System.out.println(origin + " " + destination);

            return 0;
        }
        return edge.getDuration();
    }

    public float getMinCost(){
        return minCost;
    }

    public float getMinCost(List<String> subGraph){
        float subGraphMinCost = Float.MAX_VALUE;
        for (Map.Entry<Key, Edge> entry : edges.entrySet()) {
            String x = entry.getKey().getX();
            String y = entry.getKey().getY();
            if(subGraph.contains(x) || subGraph.contains(y)){
                float duration = entry.getValue().getDuration();
                subGraphMinCost = Math.min(subGraphMinCost,duration);
            }
        }
        return subGraphMinCost;
    }
    public Graph(Set<String> vertices, HashMap<Key, Edge> edges) {

        this.vertices = vertices;
        this.edges = edges;
    }
    public void addEdge(String origin, String destination, Edge edge){
        vertices.add(origin);
        vertices.add(destination);
        edges.put(new Key(origin, destination),edge);
    }
    public int getNbVertices(){
        return vertices.size();
    }

    public int getNbEdges(){
        return edges.size();
    }
    public  HashMap<Key, Edge> getEdges(){
        return edges;
    }
    public Set<String> getVertices(){
        return vertices;
    }

    public boolean isArc(String origin, String destination){
        return edges.containsKey(new Key(origin, destination));
    }


    public Edge getEdge(String origin, String destination){
        return edges.get(new Key(origin, destination));
    }

}
