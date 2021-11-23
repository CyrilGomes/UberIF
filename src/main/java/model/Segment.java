package model;

public class Segment {
    private String origin;
    private String destination;
    private float length;

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

    public float getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    protected String name;


}
