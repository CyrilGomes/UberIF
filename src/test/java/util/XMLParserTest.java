package util;

import model.Intersection;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class XMLParserTest {

    XMLParser xmlParser = new XMLParser();

    @Test
    public void readMap() {
        Graph graph = xmlParser.readMap("files/map.xml");

        Map<String, Intersection> map = graph.getIntersectionMap();
        assertEquals(2,map.size());
        Intersection intersection = map.get("1");
        assertEquals("1",intersection.getId());
        assertEquals(45.7,intersection.getLatitude(),0.1);
        assertEquals(4.8,intersection.getLongitude(),0.1);


        List<Segment> segments = graph.getSegmentsFromIntersection("1");
        assertEquals(1,segments.size());
        Segment segment = segments.get(0);
        assertEquals("2",segment.getDestination());
        assertEquals(72.60,segment.getLength(),0.1);
        assertEquals("Rue Saint-Victorien",segment.getName());

    }

    @Test
    public void readRequests(){
        PlanningRequest planningRequest = xmlParser.readRequests("files/requestsSmall1.xml");
        assertEquals("342873658",planningRequest.getStartId());
        assertEquals("8:0:0",planningRequest.getDepartureTime());

        List<Request> requests = planningRequest.getRequests();
        assertEquals(1,requests.size());

        Request request = requests.get(0);
        assertEquals("208769039",request.getPickupId());
        assertEquals("25173820",request.getDeliveryId());
        assertEquals(180,request.getPickupDuration());
        assertEquals(240,request.getDeliveryDuration());

    }

    @Test
    public void readLargeMap(){
        Graph graph = xmlParser.readMap("files/largeMap.xml");
        for (Map.Entry<String, Intersection> entry : graph.getIntersectionMap().entrySet()) {
            System.out.println(graph.getSegmentsFromIntersection(entry.getKey()));
        }
    }
}