package model;

import model.graphs.Plan;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Plan> listPlan;
    private int position;
    private final int MAX_LENGTH =10;

    public List<Plan> getListPlan() {
        return listPlan;
    }

    public int getPosition() {
        return position;
    }

    public History() {
        this.listPlan = new ArrayList<>();
        this.position = -1;
    }

    public void registerCurrentState(Plan planData){
        Plan planNew = new Plan(planData);
        for(int i = this.position+1 ; i< this.listPlan.size() ; i++){
            this.listPlan.remove(i);
        }
        this.listPlan.add(planNew);
        if(this.listPlan.size()>MAX_LENGTH){
            this.listPlan.remove(0);
        }
        this.position = this.listPlan.size()-1;
    }
    public Plan undo(){
        if(this.position > 0){
            this.position--;
        }
        System.out.println("get the element "+position+" : "+this.listPlan.get(position));
        return this.listPlan.get(this.position);
    }
    public Plan redo(){
        if(this.position<this.listPlan.size() -1){
            this.position++;
        }
        return this.listPlan.get(this.position);
    }

    @Override
    public String toString() {
        return "History{" +
                "position=" + position +
                "\nplanSelected="+ this.listPlan.get(position).getPlanningRequest()+
                "\nlength : "+this.listPlan.size()+
                '}';
    }
}
