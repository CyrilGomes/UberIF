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

    private float globalTime;
    private List<String> pointsOfInterest;


    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(final List<Segment> segmentListInit) {
        this.segmentList = segmentListInit;
    }

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

    public List<String> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public DeliveryTour(final DeliveryTour deliverCopy) {
        this.segmentList = new ArrayList<>();
        for (Segment s : deliverCopy.getSegmentList()) {
            this.segmentList.add(new Segment(s));
        }
        this.globalTime = deliverCopy.globalTime;
        this.pointsOfInterest = new ArrayList<>(deliverCopy.pointsOfInterest);
        this.lastIntersectionId = deliverCopy.lastIntersectionId;
    }

    public String getLastIntersectionId() {
        return lastIntersectionId;
    }

    public String getFirstIntersectionId(){
        return pointsOfInterest.get(0);
    }

    public void addNextPoint(String idIntersection){
        this.pointsOfInterest.add(idIntersection);
        this.lastIntersectionId = idIntersection;
    }
    public void addListSegment(List<Segment> segmentListAdded){
        segmentList.addAll(segmentListAdded);
        
    }

    public void removeListSegment(String departID){
        String originSeg = "0";
        for(int i = segmentList.size()-1; i>=0 && !originSeg.equals(departID);i--){
            originSeg = segmentList.get(i).getOrigin();
            segmentList.remove(i);
        }
    }

    /**
     * Remove request and updates the tour
     * @param request the request to be removed
     * @param graph the already calculated graph
     */
    public DeliveryTour removeRequestAndChangeTour(Request request, Graph graph) {
        DeliveryTour newDeliveryTour = new DeliveryTour(this);
        String pickupId = request.getPickupId();
        String deliveryId = request.getDeliveryId();

        newDeliveryTour.pointsOfInterest.remove(pickupId);
        newDeliveryTour.pointsOfInterest.remove(deliveryId);

        List<Segment> segmentList = new ArrayList<>();

        int solutionSize = newDeliveryTour.pointsOfInterest.size();
        for (int i = 1; i < solutionSize; i++) {
            Edge edge = graph.getEdge(newDeliveryTour.pointsOfInterest.get(i - 1), newDeliveryTour.pointsOfInterest.get(i));
            segmentList.addAll(edge.getSegmentList());
        }
        Edge edge = graph.getEdge(newDeliveryTour.pointsOfInterest.get(solutionSize - 1), newDeliveryTour.pointsOfInterest.get(0));
        segmentList.addAll(edge.getSegmentList());

        this.segmentList = new ArrayList<>(segmentList);
        newDeliveryTour.segmentList =  new ArrayList<>(segmentList);

        return newDeliveryTour;
    }



}
