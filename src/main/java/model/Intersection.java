package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class simulating an intersection.
 */
public class Intersection {
    private String id;
    private float latitude;
    private float longitude;
    private List<Segment> segments;


    /**
     * The constructor of copy for the object Intersection.
     * @param intersection the copied intersection.
     */
    public Intersection(Intersection intersection){
        this.id = intersection.id;
        this.latitude = intersection.latitude;
        this.longitude = intersection.longitude;
        this.segments = intersection.segments;
    }

    /**
     * The constructor of the object Intersection.
     * @param id the id of the intersection.
     * @param latitude the latitude of the intersection.
     * @param longitude the longitude of the intersection.
     */
    public Intersection(String id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Add a segment linked to the intersection.
     * @param segment the segment added.
     */
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
