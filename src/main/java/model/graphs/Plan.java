package model.graphs;

import model.DeliveryTour;
import model.Intersection;
import model.PlanningRequest;
import model.Segment;

import java.util.ArrayList;
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
    PlanningRequest planningRequest = null;
    private DeliveryTour deliveryTour = null;
    float maxLatitude;
    float minLatitude;
    float maxLongitude;
    float minLongitude;





     /**
     * the constructor of the class Plan.
     * @param intersectionMap where the string is the id of the intersection object.
     * @param adjacentsMap where the string is the id of an intersection object.
     *                     the list<String> is the list of all the id of the adjacent intersections.
     * @param segmentMap where the key is composed of the id of origin and the id of destination.
     *                   the segment object is representing the line between the two intersections.
     * @see Key
     * @see Intersection
     * @see Segment
     */
    public Plan(java.util.Map<String, Intersection> intersectionMap, java.util.Map<String, List<String>> adjacentsMap, java.util.Map<Key, Segment> segmentMap) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
    }

    public Map<String, List<String>> getAdjacentsMap() {
        return adjacentsMap;
    }

    public Plan(Map<String, Intersection> intersectionMap, Map<String, List<String>> adjacentsMap, Map<Key, Segment> segmentMap,float maxLatitude, float minLatitude, float maxLongitude, float minLongitude) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
        this.maxLatitude = maxLatitude;
        this.minLatitude = minLatitude;
        this.maxLongitude = maxLongitude;
        this.minLongitude = minLongitude;
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

    public float getMaxLatitude() {
        return maxLatitude;
    }

    public float getMinLatitude() {
        return minLatitude;
    }

    public float getMaxLongitude() {
        return maxLongitude;
    }

    public float getMinLongitude() {
        return minLongitude;
    }

    public PlanningRequest getPlanningRequest() {
        return planningRequest;
    }

    public void setPlanningRequest(PlanningRequest planningRequest) {
        this.planningRequest = planningRequest;
    }

    public Segment getSegment(String origin, String destination) {
        return segmentMap.get(new Key(origin,destination));
    }

    public DeliveryTour getDeliveryTour() {
        return deliveryTour;
    }

    public void setDeliveryTour(DeliveryTour deliveryTour) {
        this.deliveryTour = deliveryTour;
    }

    public List<Segment> getSegmentsFromIntersection(String origin) {
        List<String> adjacents = adjacentsMap.get(origin);
        List<Segment> segments = new ArrayList<>();
        for (String destination: adjacents) {
            Segment segment = segmentMap.get(new Key(origin,destination));
            segments.add(segment);
        }

        return segments;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "intersectionMap=" + intersectionMap +
                ", adjacentsMap=" + adjacentsMap +
                ", segmentMap=" + segmentMap +
                ", planningRequest=" + planningRequest +
                ", maxLatitude=" + maxLatitude +
                ", minLatitude=" + minLatitude +
                ", maxLongitude=" + maxLongitude +
                ", minLongitude=" + minLongitude +
                '}';
    }
}
