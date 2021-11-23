package model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
    private String id;
    private float latitude;
    private float longitude;
    private List<Segment> segments;

    public Intersection(String id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.segments = new ArrayList<>();
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    public String getId() {
        return id;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
