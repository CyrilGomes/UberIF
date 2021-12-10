package model;

import java.util.*;

/**
 * Class representing the computed Delivery tour. result of the TSP.
 * @see Segment
 */
public class DeliveryTour {
    List<Segment> segmentList;
    List<String> orderedIdIntersectionTour;
    float globalTime;
    String lastIntersectionId;

    
    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    public float getGlobalTime() {
        return globalTime;
    }

    /**
     * Constructor of the delivery tour.
     * @param segmentList the list of segments, in order, which compose the tour.
     * @param globalTime the estimed time for the tour.
     */
    public DeliveryTour(List<Segment> segmentList, float globalTime) {
        this.segmentList = segmentList;
        this.globalTime = globalTime;
        this.orderedIdIntersectionTour = new ArrayList<String>();
        this.lastIntersectionId = null;

    }
    public DeliveryTour(DeliveryTour deliverCopy){
        this.segmentList = new ArrayList<>();
        for(Segment s : deliverCopy.getSegmentList()){
            this.segmentList.add(new Segment(s));
        }
        this.globalTime = deliverCopy.globalTime;
        this.orderedIdIntersectionTour = new ArrayList<>(deliverCopy.orderedIdIntersectionTour);
        this.lastIntersectionId = deliverCopy.lastIntersectionId;
    }

    public String getLastIntersectionId() {
        return lastIntersectionId;
    }

    public String getFirstIntersectionId(){
        return orderedIdIntersectionTour.get(0);
    }

    public void addNextPoint(String idIntersection){
        this.orderedIdIntersectionTour.add(idIntersection);
        this.lastIntersectionId = idIntersection;
    }
    public void addListSegment(List<Segment> segmentListAdded){
        segmentList.addAll(segmentListAdded);
    }
}
