package model;

import model.graphs.Graph;
import model.graphs.pathfinding.Edge;
import observer.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the computed Delivery tour. result of the TSP.
 * @see Segment
 */
public class DeliveryTour extends Observable {
    /**
     * the list of segments in order.
     */
    private List<Segment> segmentList;

    /**
     * the last intersection of the tour.
     */
    private String lastIntersectionId;
    /**
     * the global Time it takes for the delivery tour.
     */
    private float globalTime;
    /**
     * the list of point to visit in order.
     */
    private List<String> pointsOfInterest;

    /**
     * the getter of segmentList.
     * @return List<Segment> segmentList
     */
    public List<Segment> getSegmentList() {
        return segmentList;
    }

    /**
     * the setter of segmentList.
     * @param segmentListToSet
     */
    public void setSegmentList(final List<Segment> segmentListToSet) {
        this.segmentList = segmentListToSet;
    }

    /**
     * the getter of GlobalTime.
     * @return float globalTime
     */
    public float getGlobalTime() {
        return globalTime;
    }

    /**
     * Constructor of the delivery tour.
     *
     * @param globalTimeInit  the estimed time for the tour.
     * @param bestSol the best solution consisting of list of pointsOfInterest ids in order.
     */
    public DeliveryTour(final float globalTimeInit, final String[] bestSol) {
        this.segmentList = new ArrayList<>();
        this.globalTime = globalTimeInit;
        pointsOfInterest = new ArrayList<>();
        for(int i = 0; i < bestSol.length; i++) {
            pointsOfInterest.add(bestSol[i]);
        }
        this.lastIntersectionId = bestSol[bestSol.length -1];
    }

    /**
     * getter of pointsOfInterest.
     * @return List<String> pointsOfInterest
     */
    public List<String> getPointsOfInterest() {
        return pointsOfInterest;
    }

    /**
     * The copy constructor of the class.
     * @param deliverCopy the delivery tour to copy.
     */
    public DeliveryTour(final DeliveryTour deliverCopy) {
        this.segmentList = new ArrayList<>();
        for (Segment s : deliverCopy.getSegmentList()) {
            this.segmentList.add(new Segment(s));
        }
        this.globalTime = deliverCopy.globalTime;
        this.pointsOfInterest = new ArrayList<>(deliverCopy.pointsOfInterest);
        this.lastIntersectionId = deliverCopy.lastIntersectionId;
    }

    /**
     * getter of lastIntersectionId.
     * @return String lastIntersectionId
     */
    public String getLastIntersectionId() {
        return lastIntersectionId;
    }

    /**
     * add an intersection at the end of the tour.
     * @param idIntersection the id of the intersection to add.
     */
    public void addNextPoint(final String idIntersection) {
        this.pointsOfInterest.add(idIntersection);
        this.lastIntersectionId = idIntersection;
    }

    /**
     * Add a list of segment at the end of the tour.
     * @param segmentListAdded List<Segment> segment to add to the list.
     */
    public void addListSegment(final List<Segment> segmentListAdded) {
        segmentList.addAll(segmentListAdded);
        
    }

    /**
     * Remove the last Segments of the list until the intersection passed
     * in argument.
     * @param departID the intersection until which we want to remove the segments.
     */
    public void removeListSegment(final String departID) {
        String originSeg = "0";
        for (int i = segmentList.size()-1; i >= 0 && !originSeg.equals(departID); i--) {
            originSeg = segmentList.get(i).getOrigin();
            segmentList.remove(i);
        }
    }

    /**
     * Remove request and updates the tour
     * @param request the request to be removed
     * @param graph the already calculated graph
     */
    public DeliveryTour removeRequestAndChangeTour(final Request request, final Graph graph) {
        DeliveryTour newDeliveryTour = new DeliveryTour(this);
        String pickupId = request.getPickupId();
        String deliveryId = request.getDeliveryId();

        newDeliveryTour.pointsOfInterest.remove(pickupId);
        newDeliveryTour.pointsOfInterest.remove(deliveryId);

        List<Segment> segmentListNew = new ArrayList<>();

        int solutionSize = newDeliveryTour.pointsOfInterest.size();
        for (int i = 1; i < solutionSize; i++) {
            Edge edge = graph.getEdge(newDeliveryTour.pointsOfInterest.get(i - 1), newDeliveryTour.pointsOfInterest.get(i));
            segmentListNew.addAll(edge.getSegmentList());
        }
        Edge edge = graph.getEdge(newDeliveryTour.pointsOfInterest.get(solutionSize - 1), newDeliveryTour.pointsOfInterest.get(0));
        segmentListNew.addAll(edge.getSegmentList());

        this.segmentList = new ArrayList<>(segmentListNew);
        newDeliveryTour.segmentList =  new ArrayList<>(segmentListNew);

        return newDeliveryTour;
    }



}
