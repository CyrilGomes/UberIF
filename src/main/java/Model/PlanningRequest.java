package Model;

import java.util.ArrayList;
import java.util.List;

public class PlanningRequest {
    protected String startId;
    protected String departureTime;
    protected List<Request> requests;

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
}
