package model.graphs;

import model.graphs.pathfinding.Edge;

import java.util.*;

public class Graph {
    private Set<String> vertices;
    private HashMap<Key, Edge> edges;

    public Graph() {
        vertices = new HashSet<>();
        edges = new HashMap<>();
    }

    public Graph(Set<String> vertices, HashMap<Key, Edge> edges) {

        this.vertices = vertices;
        this.edges = edges;
    }
    public void addEdge(String origin, String destination, Edge edge){
        vertices.add(origin);
        vertices.add(destination);
        edges.put(new Key(origin, destination),edge);
    }
    public int getNbVertices(){
        return vertices.size();
    }

    public int getNbEdges(){
        return edges.size();
    }
    public  HashMap<Key, Edge> getEdges(){
        return edges;
    }
    public Set<String> getVertices(){
        return vertices;
    }

    public boolean isArc(String origin, String destination){
        return edges.containsKey(new Key(origin, destination));
    }


    public Edge getEdge(String origin, String destination){
        return edges.get(new Key(origin, destination));
    }

}
