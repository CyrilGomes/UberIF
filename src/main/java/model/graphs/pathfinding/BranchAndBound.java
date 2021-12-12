package model.graphs.pathfinding;

import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Plan;
import model.iterators.SeqIter;
import observer.Observer;

import java.util.*;

public class BranchAndBound extends TemplateTSP{

    public BranchAndBound(Observer mainWindow) {
        addObserver(mainWindow);
    }
    public BranchAndBound() {
    }


    /**
     * @param startNode the departure vertex
     * @return the list sorted by cost
     */
    private List<String> greedyPermutation(String startNode){
        int size = g.getNbVertices();
        String[] permutation = new String[size];
        permutation[0] = startNode;
        Set<String> vertices = g.getVertices();

        for (int i = 1; i < size; i++) {
            vertices.remove(permutation[i-1]);
            permutation[i] = g.getNearestNodeFromNode(permutation[i-1],vertices);
        }
        return Arrays.asList(permutation);
    }




    @Override
    public void computeSolution(int timeLimit, Graph g, PlanningRequest planningRequest) {

        String sourceVertex = planningRequest.getStartId();
        List<String> visited = new ArrayList<String>(g.getNbVertices());
        visited.add(sourceVertex);
        List<String> unvisited = new ArrayList<String>(g.getNbVertices() - 1);
        List<String> vertices = greedyPermutation(sourceVertex);
        unvisited.addAll(vertices);
        unvisited.remove(0);
        List<String> deliveryPoints = new ArrayList<>();
        List<String> pickupPoints = new ArrayList<>();
        List<Request> requests = planningRequest.getRequests();
        for (Request request : requests) {
            deliveryPoints.add(request.getDeliveryId());
            pickupPoints.add(request.getPickupId());
        }
        bestSolCost = Float.MAX_VALUE;
        branchAndBound(sourceVertex, sourceVertex, unvisited, visited, pickupPoints, deliveryPoints, 0);
        //System.out.println("Completed in :" + (System.currentTimeMillis() - startTime));
    }




    /**
     * Execute the branch and bound algorithm
     * @param sourceVertex    the departure vertex
     * @param currentVertex   the last visited vertex
     * @param unvisited       the set of vertex that have not yet been visited
     * @param visited         the sequence of vertices that have been already visited (including currentVertex)
     * @param pickupPoints    the pickup points
     * @param deliveryPoints  the delivery points
     * @param currentCost     the cost of the path corresponding to <code>visited</code>
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

        } else if (currentCost + bound(sourceVertex,currentVertex, unvisited) < bestSolCost) { //Si la sousestimation du chemin est supérieur, on coupe
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
     * @param sourceVertex  the departure vertex
     * @param currentVertex the current vertex
     * @param unvisited     list of the unvisited vertices
     * @return a lower bound of the cost of paths in <code>g</code> starting from <code>currentVertex</code>, visiting
     * every vertex in <code>unvisited</code> exactly once, and returning back to vertex <code>0</code>.
     */
    protected float bound(String sourceVertex, String currentVertex, Collection<String> unvisited) {


        List<String> subGraph = new ArrayList<>(unvisited);
        subGraph.add(currentVertex);
        subGraph.add(sourceVertex);

        float min = g.getMinCost(subGraph);

        return (unvisited.size() + 1)*min;
    }

    /**
     *
     * @param currentVertex the current vertex
     * @param unvisited     the list of the unvisited vertices
     * @param g             the graph
     * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
     */
    protected Iterator<String> iterator(String currentVertex, Collection<String> unvisited, Graph g) {
        return new SeqIter(unvisited, currentVertex, g);
    }
}
