package model.graphs.pathfinding;

import model.Segment;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    String origin;
    String destination;
    List<Segment> segmentList;
    float duration;

    @Override
    public String toString() {
        return "Edge{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", segmentList=" + segmentList +
                ", duration=" + duration +
                '}';
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

    public Edge(String origin, String destination, List<Segment> segmentList, float duration) {
        this.origin = origin;
        this.destination = destination;
        this.segmentList = segmentList;
        this.duration = duration;
    }

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
