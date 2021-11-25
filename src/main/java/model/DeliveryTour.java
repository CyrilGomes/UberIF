package model;

import java.util.List;

public class DeliveryTour {
    List<Segment> segmentList;
    float globalTime;

    public DeliveryTour(List<Segment> segmentList, float globalTime) {
        this.segmentList = segmentList;
        this.globalTime = globalTime;
    }
}
