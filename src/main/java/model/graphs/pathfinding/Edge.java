package model.graphs.pathfinding;

import model.Segment;

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

    public Edge(String origin, String destination, List<Segment> segmentList, float duration) {
        this.origin = origin;
        this.destination = destination;
        this.segmentList = segmentList;
        this.duration = duration;
    }
}
