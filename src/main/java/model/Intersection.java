package model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
    private String id;
    private float latitude;
    private float longitude;
    private List<Segment> segments;



    public Intersection(Intersection intersection){
        this.id = intersection.id;
        this.latitude = intersection.latitude;
        this.longitude = intersection.longitude;
        this.segments = intersection.segments;
    }
    public Intersection(String id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public float getLongitude() {
        return longitude;
    }


}
