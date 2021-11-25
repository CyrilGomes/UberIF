package model.graphs;

import model.Intersection;
import model.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Represent the map, without the requests.
 * @// TODO: 25/11/2021 fuse with the Plan model to avoid useless object and confusion. 
 * @see Plan anterior version of Plan copied during git management.
 */
public class Graph {
    Map<String, Intersection> intersectionMap;
    Map<String, List<String>> adjacentsMap;
    Map<Key, Segment> segmentMap;

    public Graph(Map<String, Intersection> intersectionMap, Map<String, List<String>> adjacentsMap, Map<Key, Segment> segmentMap) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
    }

    public Map<String, Intersection> getIntersectionMap() {
        return intersectionMap;
    }

    public List<String> getAdjacentsNodes(String node){
        return adjacentsMap.get(node);

    }

    public List<Segment> getSegmentsFromIntersection(String origin){
        List<Segment> segments = new ArrayList<>();
        for (String dest : adjacentsMap.get(origin)) {
            segments.add(segmentMap.get(new Key(origin, dest)));
        }

        return segments;
    }

    public float getSegmentWeight(String origin, String destination){
        Segment segment = segmentMap.get(new Key(origin, destination));
        return segment.getLength();
    }

    public Map<String, List<String>> getAdjacentsMap() {
        return adjacentsMap;
    }

    public Map<Key, Segment> getSegmentMap() {
        return segmentMap;
    }
}
