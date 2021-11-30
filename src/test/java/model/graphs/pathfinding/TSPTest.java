package model.graphs.pathfinding;

import javafx.util.Pair;
import model.PlanningRequest;
import model.Request;
import model.graphs.Graph;
import model.graphs.Plan;
import org.junit.Before;
import org.junit.Test;
import util.XMLParser;

import java.util.ArrayList;
import java.util.List;

public class TSPTest {

    Graph g = new Graph();

    @Before
    public void setUp() throws Exception {
        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge("2","1",null,1.0f));
        edgeList.add(new Edge("7","6",null,6.0f));
        edgeList.add(new Edge("6","7",null,3.0f));
        edgeList.add(new Edge("1","2",null,3.0f));
        edgeList.add(new Edge("4","6",null,2.0f));
        edgeList.add(new Edge("2","4",null,2.0f));
        edgeList.add(new Edge("4","7",null,5.0f));
        edgeList.add(new Edge("1","4",null,3.5f));
        edgeList.add(new Edge("2","6",null,4.0f));
        edgeList.add( new Edge("2","7",null,7.0f));
        edgeList.add( new Edge("1","6",null,5.5f));
        edgeList.add( new Edge("1","7",null,8.5f));
        edgeList.add( new Edge("7","1",null,11.0f));
        edgeList.add( new Edge("6","1",null,5.0f));
        edgeList.add( new Edge("7","2",null,10.0f));
        edgeList.add( new Edge("6","2",null,4.0f));
        edgeList.add( new Edge("4","1",null,4.0f));
        edgeList.add( new Edge("7","4",null,7.0f));
        edgeList.add( new Edge("4","2",null,3.0f));
        edgeList.add( new Edge("6","4",null,1.0f));

        for (Edge e:edgeList ) {
            g.addEdge(e.origin, e.destination, e);
        }
    }

    @Test
    public void generateTsmCompleteGraph() {
    }


    @Test
    public void toursXml(){
        XMLParser xmlParser = new XMLParser();

        Plan plan = xmlParser.readMap("files/largeMap.xml");
        PlanningRequest planningRequest = xmlParser.readRequests("files/requestsMedium5.xml");
        plan.setPlanningRequest(planningRequest);

        TSP tsp = new TSP();
        Graph graph = tsp.generateTsmCompleteGraph(plan);
        Pair<Float, List<String>> result = tsp.allTours(graph,planningRequest);
        System.out.println(result);
        System.out.println(TSP.count);

    }

}