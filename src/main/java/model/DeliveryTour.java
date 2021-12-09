package model;

import java.util.List;

/**
 * Class representing the computed Delivery tour. result of the TSP.
 * @see Segment
 */
public class DeliveryTour {
    List<Segment> segmentList;
    float globalTime;

    public List<Segment> getSegmentList() {
        return segmentList;
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
    }
}
