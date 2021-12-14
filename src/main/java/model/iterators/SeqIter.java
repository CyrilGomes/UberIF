package model.iterators;


import model.graphs.Graph;

import java.util.Collection;
import java.util.Iterator;

/**
 * Class of SeqIter, an iterator
 */
public class SeqIter implements Iterator<String> {
    /**
     * list of candidates.
     */
    private String[] candidates;
    /**
     * number of candidates.
     */
    private int nbCandidates;

    /**
     * Create an iterator to traverse the set of vertices in
     * <code>unvisited</code>
     * which are successors of <code>currentVertex</code> in <code>g</code>
     * Vertices are traversed in the same order as in <code>unvisited</code>.
     * @param unvisited not visited intersections
     * @param currentVertex current vertex
     * @param g graph
     */
    public SeqIter(Collection<String> unvisited, String currentVertex, Graph g){
        this.candidates = new String[unvisited.size()];
        for (String  s : unvisited){
            if (g.isArc(currentVertex, s))
                candidates[nbCandidates++] = s;
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidates > 0;
    }

    @Override
    public String next() {
        nbCandidates--;
        return candidates[nbCandidates];
    }

    @Override
    public void remove() {}

}
