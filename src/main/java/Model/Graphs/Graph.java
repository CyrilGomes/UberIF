package Model.Graphs;

import Model.Intersection;
import Model.Segment;
import javafx.util.Pair;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<String, Intersection> intersectionMap;
    Map<String, List<String>> adjacentsMap;
    Map<Key, Segment> segmentMap;

    public Graph(Map<String, Intersection> intersectionMap, Map<String, List<String>> adjacentsMap, Map<Key, Segment> segmentMap) {
        this.intersectionMap = intersectionMap;
        this.adjacentsMap = adjacentsMap;
        this.segmentMap = segmentMap;
    }

    public List<String> getAdjacentsNodes(String node){
        return adjacentsMap.get(node);

    }

    public float getSegmentWeight(String origin, String destination){
        Segment segment = segmentMap.get(new Key(origin, destination));
        return segment.getLength();
    }
}
