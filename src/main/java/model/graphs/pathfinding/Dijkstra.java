package model.graphs.pathfinding;

import model.Segment;
import model.graphs.Graph;
import model.graphs.Plan;

import java.util.*;


public class Dijkstra {

    /**
     * the map that save the best weight for each node
     */
    Map<String, Float> weights;


    /**
     * the map that the generated precedence of the Dijkstra algorithm
     */
    Map<String, String> precedence;


    /**
     * @param nodes the the unsettled nodes
     * @return the node with the minimum weight in the <code>weights</code> map
     */
    public String getLowestDistanceNode(Set<String> nodes){
        String lowestNode = null;
        float lowestWeight = Float.MAX_VALUE;
        for (String node: nodes ) {
            float weight = weights.get(node);
            if( weight < lowestWeight){
                lowestNode =  node;
                lowestWeight = weight;
            }
        }
        return lowestNode;
    }

    /**
     * relaxation, if the sum of weight of <code>currentNode</code> and <code>edgeWeight</code>
     * is less that the weight of <code>adjacentNode</code> then set it to the new best in <code>weights</code> and <code>precedence</code>
     * @param adjacentNode  the adjacent node
     * @param edgeWeight    the weight of the edge
     * @param currentNode   the current node
     */
    public void calculateMinimumDistance(String adjacentNode, float edgeWeight,String currentNode ){
        //System.out.println(adjacentNode + " " +edgeWeight + " "+ currentNode);
        if(!weights.containsKey(adjacentNode)){
            weights.put(adjacentNode,Float.MAX_VALUE);
        }
        if (!precedence.containsKey(currentNode) || weights.get(currentNode)+edgeWeight < weights.get(adjacentNode)){
            weights.put(adjacentNode, weights.get(currentNode)+edgeWeight);
            precedence.put(adjacentNode,currentNode);

        }
    }


    /**
     * @param plan         the plan
     * @param sourceNodeId  the departure node
     * @param newGraph the generated Graph of the point of interests, will become the complete graph
     * @param pointsOfInterests the list of all the delivery and pickup points as well as the departure node
     * @throws Exception if one of the <code>pointsOfInterests couldn't be reached</code>
     */
    public void executeAlgorithm(Plan plan, String sourceNodeId, Graph newGraph, List<String> pointsOfInterests ) throws Exception{
        if(plan == null){
            return;
        }
        weights = new HashMap<>();
        precedence = new HashMap<>();
        int globalSize = pointsOfInterests.size();

        Set<String> settledNodes = new HashSet<>();
        Set<String> unsettledNodes = new HashSet<>();

        unsettledNodes.add(sourceNodeId);
        weights.put(sourceNodeId,0.0f);


        while (unsettledNodes.size() != 0 && globalSize != 0) {
            String currentNodeId = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNodeId);
            for (String adjacent :  plan.getAdjacentsNodes(currentNodeId)) {
                float weight = plan.getSegmentWeight(currentNodeId, adjacent);
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
                    segments.add(0,plan.getSegment(tempPoint,currentPoint));
                    currentPoint = tempPoint;
                }
                Edge edge = new Edge(sourceNodeId,poi,segments, weights.get(poi));
                newGraph.addEdge(sourceNodeId, poi,edge);
            }else{
                throw new Exception("No route has been found from "+ sourceNodeId + " to "+ poi +" !");
            }
        }

    }

}
