package model;

import java.util.ArrayList;
import java.util.List;

public class PlanningRequest {
    private String startId;
    private String departureTime;
    private List<Request> requests;

    public PlanningRequest(String startId, String departureTime) {
        this.startId = startId;
        this.departureTime = departureTime;
        requests = new ArrayList<>();
    }

    public void addRequest(Request request){
        requests.add(request);
    }

    public String getStartId() {
        return startId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public String toString(){
        String print = "departure time: "+this.departureTime+"\n"+
                "id of the deposit: "+this.startId+
                "\nrequests : [\n";
        int i = 0;
        for(Request req : requests){
            i++;
            print +="   request "+i+": "+ req.toString()+"\n";
        }
        print +="]";
        return print;
    }
}
