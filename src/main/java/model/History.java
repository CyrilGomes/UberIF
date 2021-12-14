package model;

import model.graphs.Graph;
import model.graphs.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the history of the user's actions.
 * @see Plan
 * @see Graph
 */
public class History {

    /**
     * list of the different versions of the plan before each user action.
     */
    private List<Plan> listPlan;

    /**
     * list of the different versions of the graph before each user action.
     */
    private List<Graph> listGraph;

    /**
     * position in the history.
     */
    private int position;

    /**
     * maximum length of the history.
     */
    private final int MAX_LENGTH = 10;


    /**
     * The constructor of the class.
     */
    public History() {
        this.listPlan = new ArrayList<>();
        this.listGraph = new ArrayList<>();
        this.position = -1;
    }

    /**
     * The getter of listPlan.
     */
    public List<Plan> getListPlan() {
        return listPlan;
    }

    /**
     * The getter of position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * The constructor of the class.
     *
     * @param planData  the plan to register.
     * @param graph     the graph to register.
     */
    public void registerCurrentState(Plan planData, Graph graph) {
        Plan planNew = new Plan(planData);
        Graph graphNew = null;
        if (graph != null) {
            graphNew = new Graph(graph);
        }
        for (int i = this.position + 1; i < this.listPlan.size(); i++) {
            this.listPlan.remove(i);
        }
        this.listPlan.add(planNew);
        this.listGraph.add(graphNew);
        if (this.listPlan.size() > MAX_LENGTH) {
            this.listPlan.remove(0);
        }
        this.position = this.listPlan.size() - 1;
    }

    /**
     * Return the plan from the previous registered action.
     */
    public Plan undo() {
        if (this.position > 0) {
            this.position--;
        }
        //System.out.println("get the element " + position + " : " + this.listPlan.get(position));
        return this.listPlan.get(this.position);
    }

    /**
     * Return the plan from the next registered action.
     */
    public Plan redo() {
        if (this.position < this.listPlan.size() - 1) {
            this.position++;
        }
        return this.listPlan.get(this.position);
    }

    /**
     * Convert the history data to a string.
     */
    @Override
    public String toString() {
        return "History{" +
                "position=" + position +
                "\nplanSelected=" + this.listPlan.get(position).getPlanningRequest() +
                "\nlength : " + this.listPlan.size() +
                "\nlist of plan=" + this.listPlan +
                '}';
    }
}
