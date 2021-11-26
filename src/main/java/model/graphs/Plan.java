package model.graphs;

import model.Intersection;
import model.PlanningRequest;
import model.Segment;

import java.util.List;
import java.util.Map;

/**
 * Class Plan. It represents the plan currently opened by the user.
 * @// TODO: 25/11/2021 Allow the deliveries and pickup points to be null and adjust later. Fusing it with graph.
 */
public class Plan {
    java.util.Map<String, Intersection> intersectionMap;
    java.util.Map<String, List<String>> adjacentsMap;
    java.util.Map<Key, Segment> segmentMap;
    List<String> deliveries;
    List<String> pickups;
    float differenceLatitude;
    float differenceLongitude;

    /**
     * the constructor of the class Plan.
     * @param intersectionMap where the string is the id of the intersection object.
     * @param adjacentsMap where the string is the id of an intersection object.
     *                     the list<String> is the list of all the id of the adjacent intersections.
     * @param segmentMap where the key is composed of the id of origin and the id of destination.
     *                   the segment object is representing the line between the two intersections.
     * @param deliveries the list of all the id of the deliveries point.
     * @param pickups the list of all the id of the pickups point.
     * @see Key
     * @see Intersection
     * @see Segment
     */
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
    
    /**
     * Add an intersection to the map.
     * @param id the id of the intersection.
     * @param intersection the intersection object representing it.
     * @see Intersection
     */
    public void addIntersection(String id, Intersection intersection){
        intersectionMap.put(id, intersection);
    }

    /**
     * get all the adjacent intersections from an intersection.
     * @param node the intersection.
     * @return a list of adjacent intersections.
     * @see Intersection
     */
    public List<String> getAdjacentsNodes(String node){
        return adjacentsMap.get(node);

    }

    /**
     * Get the length of a segment between two intersections.
     * @param origin the intersection at the beginning of the segment.
     * @param destination the intersection at the end of the segment.
     * @return the length of the segment
     * @see Segment
     */
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

}
