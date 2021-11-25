package model.graphs;

import model.Intersection;
import model.PlanningRequest;
import model.Segment;

import java.util.List;
import java.util.Map;

public class Plan {
    java.util.Map<String, Intersection> intersectionMap;
    java.util.Map<String, List<String>> adjacentsMap;
    java.util.Map<Key, Segment> segmentMap;
    List<String> deliveries;
    List<String> pickups;
    float differenceLatitude;
    float differenceLongitude;
    PlanningRequest planningRequest = null;

    public Plan(java.util.Map<String, Intersection> intersectionMap, java.util.Map<String, List<String>> adjacentsMap, java.util.Map<Key, Segment> segmentMap, List<String> deliveries, List<String> pickups) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
        this.deliveries = deliveries;
        this.pickups = pickups;
    }

    public Plan(java.util.Map<String, Intersection> intersectionMap, java.util.Map<String, List<String>> adjacentsMap, java.util.Map<Key, Segment> segmentMap, List<String> deliveries, List<String> pickups, float differenceLatitude, float differenceLongitude) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
        this.deliveries = deliveries;
        this.pickups = pickups;
        this.differenceLatitude = differenceLatitude;
        this.differenceLongitude = differenceLongitude;
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

    public PlanningRequest getPlanningRequest() {
        return planningRequest;
    }

    public void setPlanningRequest(PlanningRequest planningRequest) {
        this.planningRequest = planningRequest;
    }
}
