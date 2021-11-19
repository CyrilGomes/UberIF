package Model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
    String id;
    float latitude;
    float longitude;
    List<Segment> segments;

    public Intersection(String id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.segments = new ArrayList<Segment>();
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }

}
