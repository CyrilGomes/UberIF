package model;

/**
 * Class representing a segment between two intersections.
 */
public class Segment {
    /**
     * The origin of the segment.
     */
    private String origin;
    /**
     * The destination of the segment.
     */
    private String destination;
    /**
     * The length of the segment.
     */
    private float length;
    /**
     * The name of the segment.
     */
    private String name;

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
    /**
     * Deep copy constructor of the Segment object.
     * @param segCopy the segment to copy.
     */
    public Segment(Segment segCopy) {
        this.origin = segCopy.origin;
        this.destination = segCopy.destination;
        this.length = segCopy.length;
        this.name = segCopy.name;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", length=" + length +
                ", name='" + name + '\'' +
                '}';
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
