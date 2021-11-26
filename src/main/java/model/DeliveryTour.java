package model;

import java.util.List;

public class DeliveryTour {
    List<Segment> segmentList;
    float globalTime;

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public float getGlobalTime() {
        return globalTime;
    }

    public DeliveryTour(List<Segment> segmentList, float globalTime) {
        this.segmentList = segmentList;
        this.globalTime = globalTime;
    }
}
