package model.graphs.pathfinding;

import model.graphs.Graph;
import model.iterators.SeqIter;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP{
    @Override
    protected float bound(String currentVertex, Collection<String> unvisited) {
        float min = Float.MAX_VALUE;
        for (String nextVertex: unvisited) {
            if(g.getCost(currentVertex,nextVertex) < min){
                min = g.getCost(currentVertex,nextVertex);
            }
        }

        return unvisited.size()*min;
    }

    @Override
    protected Iterator<String> iterator(String currentVertex, Collection<String> unvisited, Graph g) {
        return new SeqIter(unvisited, currentVertex, g);
    }
}
