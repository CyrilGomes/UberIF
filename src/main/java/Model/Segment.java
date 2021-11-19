package Model;

public class Segment {
    protected String origin;
    protected String destination;
    protected float length;

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
