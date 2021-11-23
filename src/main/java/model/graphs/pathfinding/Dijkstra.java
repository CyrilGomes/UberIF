package model.graphs.pathfinding;

import model.graphs.Plan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
        if (!precedence.containsKey(currentNode) || poids.get(currentNode)+edgeWeight < poids.get(adjacentNode)){
            poids.put(adjacentNode,poids.get(currentNode)+edgeWeight);
            precedence.put(adjacentNode,currentNode);

        }
    }

    public Dijkstra() {
        poids = new HashMap<>();
        precedence = new HashMap<>();
    }

    public Plan executeAlgorithm(Plan graph, String sourceNodeId){


        Set<String> settledNodes = new HashSet<>();
        Set<String> unsettledNodes = new HashSet<>();

        unsettledNodes.add(sourceNodeId);
        poids.put(sourceNodeId,0.0f);


        while (unsettledNodes.size() != 0) {
            String currentNodeId = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNodeId);
            for (String adjacent :  graph.getAdjacentsNodes(currentNodeId)) {
                float weight = graph.getSegmentWeight(currentNodeId, adjacent);
                if (!settledNodes.contains(adjacent)) {
                    calculateMinimumDistance(adjacent, weight, currentNodeId);
                    unsettledNodes.add(adjacent);
                }
            }
            settledNodes.add(currentNodeId);
        }
        return graph;
    }





}
