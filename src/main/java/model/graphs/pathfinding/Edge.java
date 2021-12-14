package model.graphs.pathfinding;

import model.Segment;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Edge. represent an edge of the complete graph
 */
public class Edge {
    /**
     * the origin of the edge.
     */
    private final String origin;
    /**
     * the destination of the edge.
     */
    private final String destination;
    /**
     * the segments nested in the edge.
     */
    private final List<Segment> segmentList;
    /**
     * the total travel time bewteen <code>origin</code> and
     * <code>destination</code>.
     */
    private final float duration;

    @Override
    public String toString() {
        return  "Edge{"
                + "origin='" + origin + '\''
                + ", destination='" + destination + '\''
                + ", segmentList=" + segmentList
                + ", duration=" + duration
                + '}';
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public float getDuration() {
        return duration;
    }

    /** Constructor of the Edge object
     * @param origin        the edge origin
     * @param destination   the edge destination
     * @param segmentList   the segments nested in the edge
     * @param duration      the total travel time bewteen <code>origin</code> and
     *                      <code>destination</code>
     */
    public Edge(final String origin,
                final String destination,
                final List<Segment> segmentList,
                final float duration) {
        this.origin = origin;
        this.destination = destination;
        this.segmentList = segmentList;
        this.duration = duration;
    }

    /** Deep copy constructor of the Edge object
     * @param edgeCopy  the Edge to copy
     */
    public Edge(Edge edgeCopy){
        this.origin = edgeCopy.origin;
        this.destination = edgeCopy.destination;
        this.segmentList = new ArrayList<>();
        if(edgeCopy.segmentList != null){
            edgeCopy.segmentList.forEach(segment -> {
                this.segmentList.add(new Segment(segment));
            });
        }

        this.duration = edgeCopy.duration;

    }
}
