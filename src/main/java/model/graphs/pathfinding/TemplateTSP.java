package model.graphs.pathfinding;

import javafx.util.Pair;
import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Key;
import model.graphs.Plan;
import observer.Observable;

import java.sql.SQLOutput;
import java.util.*;

public abstract class TemplateTSP extends Observable implements TSP {
    protected String[] bestSol;
    protected Graph g;
    protected float bestSolCost;
    protected int timeLimit;
    protected long startTime;


    /**
     * Call the <code>computerSolution</code> of the specific algorithm
     * @param timeLimit
     * @param g
     * @param planningRequest
     */
    @Override
    public void searchSolution(int timeLimit, Graph g, PlanningRequest planningRequest) {
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new String[g.getNbVertices()];

        computeSolution(timeLimit,g,planningRequest);
        notifyObservers(getDeliveryTour());
    }

    /**
     * The specific implementation of the algorithm
     * @param timeLimit
     * @param g
     * @param planningRequest
     */
    protected abstract void computeSolution(int timeLimit, Graph g, PlanningRequest planningRequest);

    /**
     * @return a DeliveryTour object that contains, all the computed information
     */
    @Override
    public DeliveryTour getDeliveryTour() {

        List<Segment> segmentList = new ArrayList<>();

        int solutionSize = bestSol.length;
        for (int i = 1; i < solutionSize; i++) {
            Edge edge = g.getEdge(bestSol[i - 1], bestSol[i]);
            if(edge.segmentList != null)
                segmentList.addAll(edge.segmentList);
        }
        Edge edge = g.getEdge(bestSol[solutionSize - 1], bestSol[0]);
        if(edge.segmentList != null)
            segmentList.addAll(edge.segmentList);

        System.out.println(bestSolCost);
        System.out.println(Arrays.toString(bestSol));
        return new DeliveryTour(segmentList, bestSolCost, bestSol);
    }

    /**
     * @return the Array of string of the computed tour
     */
    @Override
    public String[] getSolution() {
        return bestSol;
    }

    /**
     * @return the cost of the computed tour
     */
    @Override
    public float getSolutionCost() {
        if (g != null) {
            return bestSolCost;
        }
        return -1;
    }


}