package model;

/**
 * Class representing a segment between two intersections.
 */
public class Segment {
    protected String origin;
    protected String destination;
    protected float length;
    protected String name;

    /**
     * Constructor of the Segment object.
     * @param origin the id from the origin intersection.
     * @param destination the id from the destination intersection.
     * @param length the length of the segment.
     * @param name the name of the segment.
     */
    public Segment(String origin,String destination, float length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
