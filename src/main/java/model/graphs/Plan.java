package model.graphs;

import model.Intersection;
import model.PlanningRequest;
import model.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Plan {
    Map<String, Intersection> intersectionMap;
    Map<String, List<String>> adjacentsMap;
    Map<Key, Segment> segmentMap;
    List<String> deliveries;
    List<String> pickups;
    float differenceLatitude;
    float differenceLongitude;

    public Map<String, List<String>> getAdjacentsMap() {
        return adjacentsMap;
    }

    public Segment getSegment(String origin, String destination){
        Key key = new Key(origin, destination);
        return segmentMap.get(key);
    }

    public List<String> getDeliveries() {
        return deliveries;
    }

    public List<String> getPickups() {
        return pickups;
    }

    public Plan(java.util.Map<String, Intersection> intersectionMap, java.util.Map<String, List<String>> adjacentsMap, java.util.Map<Key, Segment> segmentMap, List<String> deliveries, List<String> pickups) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
        this.deliveries = deliveries;
        this.pickups = pickups;
    }

    public Plan(Map<String, Intersection> intersectionMap, Map<String, List<String>> adjacentsMap, Map<Key, Segment> segmentMap, List<String> deliveries, List<String> pickups, float differenceLatitude, float differenceLongitude) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
        this.deliveries = deliveries;
        this.pickups = pickups;
        this.differenceLatitude = differenceLatitude;
        this.differenceLongitude = differenceLongitude;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "intersectionMap=" + intersectionMap +
                ", adjacentsMap=" + adjacentsMap +
                ", segmentMap=" + segmentMap +
                '}';
    }
    public Map<String, Intersection> getIntersectionMap() {
        return intersectionMap;
    }

    public Map<Key, Segment> getSegmentMap() {
        return segmentMap;
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

    public float getDifferenceLatitude() {
        return differenceLatitude;
    }

    public float getDifferenceLongitude() {
        return differenceLongitude;
    }

    public List<Segment> getSegmentsFromIntersection(String origin) {
        List<String> adjactendsNodes = adjacentsMap.get(origin);
        List<Segment> segmentList = new ArrayList<>();
        for (String destination:adjactendsNodes) {
            Segment segment = segmentMap.get(new Key(origin, destination));
            segmentList.add(segment);
        }

        return segmentList;
    }
}
