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

public abstract class TemplateTSP implements TSP {
    private String[] bestSol;
    protected Graph g;
    private float bestSolCost;
    private int timeLimit;
    private long startTime;

    @Override
    public void searchSolution(int timeLimit, Graph g, PlanningRequest planningRequest) {
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new String[g.getNbVertices()];

        String sourceVertex = planningRequest.getStartId();
        List<String> visited = new ArrayList<String>(g.getNbVertices());
        visited.add(sourceVertex);
        List<String> unvisited = new ArrayList<String>(g.getNbVertices() - 1);
        Set<String> vertices = g.getVertices();
        unvisited.addAll(vertices);
        unvisited.remove(sourceVertex);
        List<String> deliveryPoints = new ArrayList<>();
        List<String> pickupPoints = new ArrayList<>();
        List<Request> requests = planningRequest.getRequests();
        for (Request request : requests) {
            deliveryPoints.add(request.getDeliveryId());
            pickupPoints.add(request.getPickupId());
        }
        bestSolCost = Integer.MAX_VALUE;
        branchAndBound(sourceVertex, sourceVertex, unvisited, visited, pickupPoints, deliveryPoints, 0);
        System.out.println("Completed in :" + (System.currentTimeMillis() - startTime));
    }


    /**
     * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
     *
     * @param currentVertex the last visited vertex
     * @param unvisited     the set of vertex that have not yet been visited
     * @param visited       the sequence of vertices that have been already visited (including currentVertex)
     * @param currentCost   the cost of the path corresponding to <code>visited</code>
     */
    private void branchAndBound(String sourceVertex, String currentVertex, Collection<String> unvisited,
                                Collection<String> visited, List<String> pickupPoints,
                                List<String> deliveryPoints,
                                float currentCost) {
        if (System.currentTimeMillis() - startTime > timeLimit) return;
        if (unvisited.size() == 0) {
            if (g.isArc(currentVertex, sourceVertex)) {
                if (currentCost + g.getCost(currentVertex, sourceVertex) < bestSolCost) {
                    visited.toArray(bestSol);
                    bestSolCost = currentCost + g.getCost(currentVertex, sourceVertex);
                }
            }
        } else if (currentCost + bound(currentVertex, unvisited) < bestSolCost) {
            Iterator<String> it = iterator(currentVertex, unvisited, g);
            while (it.hasNext()) {
                String nextVertex = it.next();
                if (deliveryPoints.contains(nextVertex)) {
                    int id = deliveryPoints.indexOf(nextVertex);
                    if (!visited.contains(pickupPoints.get(id))) {
                        continue;
                    }
                }
                visited.add(nextVertex);
                unvisited.remove(nextVertex);
                branchAndBound(sourceVertex, nextVertex, unvisited, visited, pickupPoints, deliveryPoints,
                        currentCost + g.getCost(currentVertex, nextVertex));
                visited.remove(nextVertex);
                unvisited.add(nextVertex);
            }
        }
    }

    /**
     * Method that must be defined in TemplateTSP subclasses
     *
     * @param currentVertex
     * @param unvisited
     * @return a lower bound of the cost of paths in <code>g</code> starting from <code>currentVertex</code>, visiting
     * every vertex in <code>unvisited</code> exactly once, and returning back to vertex <code>0</code>.
     */
    protected abstract float bound(String currentVertex, Collection<String> unvisited);

    /**
     * Method that must be defined in TemplateTSP subclasses
     *
     * @param currentVertex
     * @param unvisited
     * @param g
     * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
     */
    protected abstract Iterator<String> iterator(String currentVertex, Collection<String> unvisited, Graph g);


    @Override
    public String[] getSolution() {

        return bestSol;
    }

    @Override
    public float getSolutionCost() {
        if (g != null) {
            return bestSolCost;
        }
        return -1;
    }

    public Graph generateTsmCompleteGraph(Plan plan) {
        Dijkstra dijkstra = new Dijkstra();
        List<String> pointsOfInterests = new ArrayList<>();

        PlanningRequest planningRequest = plan.getPlanningRequest();
        pointsOfInterests.add(planningRequest.getStartId());
        for (Request request : planningRequest.getRequests()) {
            pointsOfInterests.add(request.getDeliveryId());
            pointsOfInterests.add(request.getPickupId());
        }

        Graph newGraph = new Graph();


        for (String currentPoint : pointsOfInterests) {

            dijkstra.executeAlgorithm(plan, currentPoint, newGraph, pointsOfInterests);
        }


        return newGraph;

    }

    @Override
    public DeliveryTour getDeliveryTour() {
        List<Segment> segmentList = new ArrayList<>();

        int solutionSize = bestSol.length;
        for (int i = 1; i < solutionSize; i++) {
            Edge edge = g.getEdge(bestSol[i - 1], bestSol[i]);
            segmentList.addAll(edge.segmentList);
        }
        Edge edge = g.getEdge(bestSol[solutionSize - 1], bestSol[0]);
        segmentList.addAll(edge.segmentList);

        return new DeliveryTour(segmentList, bestSolCost,bestSol);
    }
}