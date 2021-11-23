package Util;

import Model.Intersection;
import Model.PlanningRequest;
import Model.Request;
import Model.Segment;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class XMLParserTest {

    XMLParser xmlParser = new XMLParser();

    @Test
    public void readMap() {
        Map<String, Intersection> map = xmlParser.readMap("files/map.xml");
        assertEquals(2,map.size());
        Intersection intersection = map.get("1");
        assertEquals("1",intersection.getId());
        assertEquals(45.7,intersection.getLatitude(),0.1);
        assertEquals(4.8,intersection.getLongitude(),0.1);

        List<Segment> segments = intersection.getSegments();
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
}