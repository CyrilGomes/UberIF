package util;

import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.Intersection;
import model.graphs.Graph;
import model.graphs.Key;

import model.graphs.Plan;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser {
    private Document parseXMLFile(String filePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(filePath));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Read a map xml file composed of intersections and segments
    public Plan readMap(String filePath) {
        Document doc = parseXMLFile(filePath);
        Map<String, Intersection> intersectionMap = new HashMap<>();
        Map<String, List<String>> adjacentsMap = new HashMap<>();
        Map<Key,Segment> segmentMap = new HashMap<>();

        float maxLatitude = Integer.MIN_VALUE;
        float minLatitude = Integer.MAX_VALUE;
        float maxLongitude = Integer.MIN_VALUE;
        float minLongitude = Integer.MAX_VALUE;

        // Get all intersections
        NodeList intersections = doc.getElementsByTagName("intersection");
        for (int i = 0; i < intersections.getLength(); i++) {
            Node node = intersections.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                float latitude = Float.parseFloat(element.getAttribute("latitude"));
                float longitude = Float.parseFloat(element.getAttribute("longitude"));

                maxLatitude = Math.max(maxLatitude,latitude);
                minLatitude = Math.min(minLatitude,latitude);
                maxLongitude= Math.max(maxLongitude,longitude);
                minLongitude= Math.min(minLongitude,longitude);

                Intersection intersection = new Intersection(id, latitude, longitude);
                intersectionMap.put(id, intersection);
                adjacentsMap.put(id,new ArrayList<>());
            }
        }

        // Get all segments
        NodeList segments = doc.getElementsByTagName("segment");
        for (int i = 0; i < segments.getLength(); i++) {
            Node node = segments.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String origin = element.getAttribute("origin");
                String destination = element.getAttribute("destination");
                float length = Float.parseFloat(element.getAttribute("length"));
                String name = element.getAttribute("name");

                Segment segment = new Segment(origin,destination, length, name);
                adjacentsMap.get(origin).add(destination);

                Key key = new Key(origin,destination);
                segmentMap.put(key,segment);
            }

        }

        float differenceLatitude = maxLatitude-minLatitude;
        float differenceLongitude = maxLongitude - minLongitude;
        Plan plan = new Plan(intersectionMap,adjacentsMap,segmentMap,null,null,differenceLatitude,differenceLongitude);

        return plan;
    }

    // Read a requests file
    public PlanningRequest readRequests (String filePath){
        Document doc = parseXMLFile(filePath);

        // Get depot
        Element depot = (Element) doc.getElementsByTagName("depot").item(0);
        String startId = depot.getAttribute("address");
        String departureTime = depot.getAttribute("departureTime");

        PlanningRequest planningRequest = new PlanningRequest(startId,departureTime);

        // Get all requests
        NodeList requests = doc.getElementsByTagName("request");
        for (int i = 0; i < requests.getLength(); i++) {
            Node node = requests.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String pickupId = element.getAttribute("pickupAddress");
                String deliveryId = element.getAttribute("deliveryAddress");
                int pickupDuration = Integer.parseInt(element.getAttribute("pickupDuration"));
                int deliveryDuration = Integer.parseInt(element.getAttribute("deliveryDuration"));

                Request request = new Request(pickupId,deliveryId,pickupDuration,deliveryDuration);
                planningRequest.addRequest(request);

            }
        }

        return planningRequest;
    }


}
