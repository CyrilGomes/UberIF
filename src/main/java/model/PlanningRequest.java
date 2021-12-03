package model;

import java.util.ArrayList;
import java.util.List;

/**
 * the class simulating the requests to compute in the tour.
 * @see Request
 */
public class PlanningRequest {
    private String startId;
    private String departureTime;
    private List<Request> requests;
    // Finish time calculated for the tour
    private String finishTime;
    /**
     * the constructor of the object PlanningRequest.
     * @param startId the id of the intersection of the deposit.
     * @param departureTime the time of departure from the deliveryman.
     */
    public PlanningRequest(String startId, String departureTime) {
        this.startId = startId;
        this.departureTime = departureTime;
        requests = new ArrayList<>();
    }

    /**
     * Add a request to the planning.
     * @param request the request added.
     */
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

    public void setRequests(List<Request> requests) {
        this.requests = requests;
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

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public void removeRequest(Request request){
        requests.remove(request);
    }
}
