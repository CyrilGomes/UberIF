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

import java.util.*;

public abstract class TemplateTSP extends Observable implements TSP {
    protected String[] bestSol;
    protected Graph g;
    protected float bestSolCost;
    protected int timeLimit;
    protected long startTime;


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

    protected abstract void computeSolution(int timeLimit, Graph g, PlanningRequest planningRequest);

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

        System.out.println(bestSolCost);
        return new DeliveryTour(segmentList, bestSolCost);
    }
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


}