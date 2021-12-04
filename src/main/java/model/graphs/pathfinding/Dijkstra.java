package model.graphs.pathfinding;

import model.Intersection;
import model.Segment;
import model.graphs.Graph;
import model.graphs.Key;
import model.graphs.Plan;

import java.util.*;


public class Dijkstra {

    Map<String, Float> poids;
    Map<String, String> precedence;


    public String getLowestDistanceNode(Set<String> nodes){
        String lowestNode = null;
        float lowestWeight = Float.MAX_VALUE;
        for (String node: nodes ) {
            float weight = poids.get(node);
            if( weight < lowestWeight){
                lowestNode =  node;
                lowestWeight = weight;
            }
        }
        return lowestNode;
    }

    public void calculateMinimumDistance(String adjacentNode, float edgeWeight,String currentNode ){
        //System.out.println(adjacentNode + " " +edgeWeight + " "+ currentNode);
        if(!poids.containsKey(adjacentNode)){
            poids.put(adjacentNode,Float.MAX_VALUE);
        }
        if (!precedence.containsKey(currentNode) || poids.get(currentNode)+edgeWeight < poids.get(adjacentNode)){
            poids.put(adjacentNode,poids.get(currentNode)+edgeWeight);
            precedence.put(adjacentNode,currentNode);

        }
    }



    public void executeAlgorithm(Plan graph, String sourceNodeId, Graph newGraph, List<String> pointsOfInterests ){
        if(graph == null){
            System.out.println("BOOOBOO");
        }
        poids = new HashMap<>();
        precedence = new HashMap<>();
        int globalSize = pointsOfInterests.size();

        Set<String> settledNodes = new HashSet<>();
        Set<String> unsettledNodes = new HashSet<>();

        unsettledNodes.add(sourceNodeId);
        poids.put(sourceNodeId,0.0f);


        while (unsettledNodes.size() != 0 && globalSize != 0) {
            String currentNodeId = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNodeId);
            for (String adjacent :  graph.getAdjacentsNodes(currentNodeId)) {
                float weight = graph.getSegmentWeight(currentNodeId, adjacent);
                if (!settledNodes.contains(adjacent)) {
                    calculateMinimumDistance(adjacent, weight, currentNodeId);
                    unsettledNodes.add(adjacent);
                }
            }
            if(pointsOfInterests.contains(currentNodeId)){
                globalSize--;
            }
            settledNodes.add(currentNodeId);
        }
        for (String poi: pointsOfInterests) {
            if(poi.equals(sourceNodeId))
                continue;
            String currentPoint = poi;
            if(precedence.containsKey(poi)){
                List<Segment> segments = new ArrayList<>();
                while(!currentPoint.equals(sourceNodeId)){
                    String tempPoint = precedence.get(currentPoint);
                    segments.add(0,graph.getSegment(tempPoint,currentPoint));
                    currentPoint = tempPoint;
                }
                Edge edge = new Edge(sourceNodeId,poi,segments,poids.get(poi));
                newGraph.addEdge(sourceNodeId, poi,edge);
            }
        }

    }

}
