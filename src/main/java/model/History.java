package model;

import model.graphs.Plan;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Plan> listPlan;
    private int position;

    public History() {
        this.listPlan = new ArrayList<>();
        this.position = -1;
    }

    public void registerCurrentState(Plan planData){
        for(int i = this.position+1 ; i< this.listPlan.size() ; i++){
            this.listPlan.remove(i);
        }
        this.listPlan.add(planData);
        this.position = this.listPlan.size()-1;
    }
    public Plan undo(){
        if(this.position > 0){
            this.position--;
        }
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
                '}';
    }
}
