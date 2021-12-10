package model.graphs;

import model.*;

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
    String selectedStreetName = "";
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

    public Plan(Plan planCopy){
        this.intersectionMap = planCopy.getIntersectionMap();
        this.adjacentsMap = planCopy.getAdjacentsMap();
        this.segmentMap = planCopy.getSegmentMap();
        this.planningRequest = planCopy.getPlanningRequest();
        this.deliveryTour = planCopy.getDeliveryTour();
        this.maxLatitude = planCopy.maxLatitude;
        this.minLatitude = planCopy.minLatitude;
        this.maxLongitude = planCopy.maxLongitude;
        this.minLongitude = planCopy.minLongitude;
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

    public String getSelectedStreetName() {
        return selectedStreetName;
    }

    public void setSelectedStreetName(String selectedStreetName) {
        this.selectedStreetName = selectedStreetName;
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
                ", planningRequest=" + planningRequest +
                ", maxLatitude=" + maxLatitude +
                ", minLatitude=" + minLatitude +
                ", maxLongitude=" + maxLongitude +
                ", minLongitude=" + minLongitude +
                '}';
    }

    public void removeRequestAndChangeTour(Request request, Graph graph){
        String pickupId = request.getPickupId();
        String deliveryId = request.getDeliveryId();
        List<Request> requests = planningRequest.getRequests();

        if(requests.size()== 1){
            deliveryTour.setSegmentList(new ArrayList<>());
        }
        else {

            // Get all points of interests from the requests ( the depot start, each pickup and each delivery)
            List<String> pointsOfInterest = new ArrayList<>();
            pointsOfInterest.add(planningRequest.getStartId());
            for (Request req : requests) {
                pointsOfInterest.add(req.getPickupId());
                pointsOfInterest.add(req.getDeliveryId());
            }

            String beforePickupPoint = null;
            String afterPickupPoint = null;
            String beforeDeliveryPoint = null;
            String afterDeliveryPoint = null;

            int beforePickupIndex = -1;
            int afterPickupIndex = -1;
            int beforeDeliveryIndex = -1;
            int afterDeliveryIndex = -1;

            String before = null;
            int beforeIndex = -1;
            int pickupIndex = -1;
            int deliveryIndex = -1;


            List<Segment> segmentList = deliveryTour.getSegmentList();

            // Get before and after points of pickupPoint
            for (int i = 0; i < segmentList.size(); i++) {
                Segment segment = segmentList.get(i);
                String origin = segment.getOrigin();
                if (beforePickupPoint == null && pointsOfInterest.contains(origin) && !origin.equals(pickupId)) {
                    before = origin;
                    beforeIndex = i;
                }

                if (origin.equals(pickupId) && beforePickupPoint == null) {
                    beforePickupPoint = before;
                    beforePickupIndex = beforeIndex;
                    pickupIndex = i;
                    continue;
                }

                if (afterPickupPoint == null && beforePickupPoint != null && pointsOfInterest.contains(origin)) {
                    afterPickupPoint = origin;
                    afterPickupIndex = i;
                    break;
                }
            }

            // Get before and after points of deliveryPoint
            for (int i = 0; i < segmentList.size(); i++) {
                Segment segment = segmentList.get(i);
                String origin = segment.getOrigin();
                if (beforeDeliveryPoint == null && pointsOfInterest.contains(origin) && !origin.equals(deliveryId)) {
                    before = origin;
                    beforeIndex = i;
                }

                if (origin.equals(deliveryId) && beforeDeliveryPoint == null) {
                    beforeDeliveryPoint = before;
                    beforeDeliveryIndex = beforeIndex;
                    deliveryIndex = i;
                    continue;
                }

                if (afterDeliveryPoint == null && beforeDeliveryPoint != null && pointsOfInterest.contains(origin)) {
                    afterDeliveryPoint = origin;
                    afterDeliveryIndex = i;
                    break;
                }
            }

            // Case where there is no point of interest after the deliveryPoint
            if (afterDeliveryPoint == null) {
                afterDeliveryPoint = deliveryId;
                afterDeliveryIndex = deliveryIndex;
            }

            System.out.println("beforePickupPoint: " + beforePickupPoint + "  afterPickupPoint: " + afterPickupPoint + " pickupIndex: " + pickupIndex + " beforepickupIndex: " + beforePickupIndex + " afterpickupIndex: " + afterPickupIndex);
            System.out.println("beforeDeliveryPoint: " + beforeDeliveryPoint + "  afterDeliveryPoint: " + afterDeliveryPoint + " deliveryIndex: " + deliveryIndex + " beforeDeliveryIndex: " + beforeDeliveryIndex + " afterDeliveryIndex: " + afterDeliveryIndex);
            System.out.println("pointsOfInterests: " + pointsOfInterest);

            // Subtract from the list the segments between beforePickupIndex and afterPickupIndex and between beforeDeliveryIndex and afterDeliveryIndex
            List<Segment> finalSegmentList = segmentList.subList(0, beforePickupIndex);
            int indexWhereToAdd1 = finalSegmentList.size();
            finalSegmentList.addAll(segmentList.subList(afterPickupIndex, beforeDeliveryIndex));
            int indexWhereToAdd2 = finalSegmentList.size();
            finalSegmentList.addAll(segmentList.subList(afterDeliveryIndex + 1, segmentList.size()));

            // Get best route from beforePickupPoint and afterPickupPoint, add at the right position in the segmentList
            List<Segment> segmentsToAddPickup = graph.getEdge(beforePickupPoint, afterPickupPoint).getSegmentList();
            finalSegmentList.addAll(indexWhereToAdd1, segmentsToAddPickup);
            // Since we change finalSegment the index2 should be updated
            indexWhereToAdd2 += segmentsToAddPickup.size();

            // Get best route from beforeDeliveryPoint and afterDeliveryPoint, add at the right position in the segmentList
            List<Segment> segmentsToAddDelivery = graph.getEdge(beforeDeliveryPoint, afterDeliveryPoint).getSegmentList();
            finalSegmentList.addAll(indexWhereToAdd2, segmentsToAddDelivery);

            // Update the delivery tour with the new list of segments
            deliveryTour.setSegmentList(finalSegmentList);
        }

        // Remove the request
        planningRequest.removeRequest(request);

    }
}
