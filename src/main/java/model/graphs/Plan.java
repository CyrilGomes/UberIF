package model.graphs;

import model.Intersection;
import model.Segment;
import java.util.List;
import java.util.Map;

public class Plan {
    java.util.Map<String, Intersection> intersectionMap;
    java.util.Map<String, List<String>> adjacentsMap;
    java.util.Map<Key, Segment> segmentMap;
    List<String> deliveries;
    List<String> pickups;

    public Plan(java.util.Map<String, Intersection> intersectionMap, java.util.Map<String, List<String>> adjacentsMap, java.util.Map<Key, Segment> segmentMap, List<String> deliveries, List<String> pickups) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
        this.deliveries = deliveries;
        this.pickups = pickups;
    }




    public void addIntersection(String id, Intersection intersection){
        intersectionMap.put(id, intersection);
    }

    public List<String> getAdjacentsNodes(String node){
        return adjacentsMap.get(node);

    }

    public float getSegmentWeight(String origin, String destination){
        Segment segment = segmentMap.get(new Key(origin, destination));
        return segment.getLength();
    }
}
