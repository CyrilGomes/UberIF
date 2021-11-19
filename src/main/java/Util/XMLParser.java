package Util;

import Model.Request;
import Model.Segment;
import Model.Intersection;

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
    public Map<String, Intersection> readMap(String filePath) {
        Document doc = parseXMLFile(filePath);
        Map<String, Intersection> map = new HashMap<>();

        // Get all intersections
        NodeList intersections = doc.getElementsByTagName("intersection");
        for (int i = 0; i < intersections.getLength(); i++) {
            Node node = intersections.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                float latitude = Float.parseFloat(element.getAttribute("latitude"));
                float longitude = Float.parseFloat(element.getAttribute("longitude"));

                Intersection intersection = new Intersection(id, latitude, longitude);
                map.put(id, intersection);
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
                map.get(origin).addSegment(segment);
            }

        }

        return map;
    }

    // Read a requests file



}
