package Model.Graphs;

import Model.Intersection;

public class Node extends Intersection {
    private float weight;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Node(Intersection intersection) {
        super(intersection);
    }
}
