package view;

import javafx.util.Pair;
import model.*;
import model.graphs.Graph;
import model.graphs.Key;
import model.graphs.Plan;
import model.graphs.pathfinding.TSP;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The visualisation of the map. Updated when the data changes.
 */
public class PlanPanel extends JComponent {
	private Plan planData;
	private DeliveryTour deliveryTour;
	float maxLatitude;
	float minLatitude;
	float maxLongitude;
	float minLongitude;
	int width;
	int height;


	public PlanPanel() {
		setBackground(Color.BLUE);
		setVisible(true);

	}

	public void setPlanData(Plan planData) {
		this.planData = planData;
		this.repaint();
	}

	public void setPlanningRequest(PlanningRequest planningRequest){
		planData.setPlanningRequest(planningRequest);
		this.repaint();
	}

	public void setDeliveryTour(DeliveryTour deliveryTour){
		this.deliveryTour = deliveryTour;
		this.repaint();
	}

	public void drawSegment(Graphics g, Segment segment, Color color,Map<String, Intersection> intersectionMap, boolean isBestPath ){

		g.setColor(color);
		Intersection origine = intersectionMap.get(segment.getOrigin());
		Intersection destination = intersectionMap.get(segment.getDestination());

		int yOrigine = getCoordinateY(origine.getLatitude(),0,width,minLatitude,maxLatitude);
		int xOrigine = getCoordinate(origine.getLongitude(),0,height,minLongitude,maxLongitude);
		//g.drawRect(xOrigine,yOrigine,10,10);

		//System.out.println("xOrigine: " + xOrigine + "\tyOrigine: " + yOrigine+ " ");

		int yDestination = getCoordinateY(destination.getLatitude(),0,width,minLatitude,maxLatitude);
		int xDestination = getCoordinate(destination.getLongitude(),0,height,minLongitude,maxLongitude);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(isBestPath ? 3: 1));
		g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination, yDestination));

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Paint");

		if(planData != null){
			maxLatitude = planData.getMaxLatitude();
			minLatitude = planData.getMinLatitude();
			maxLongitude = planData.getMaxLongitude();
			minLongitude = planData.getMinLongitude();
			width = this.getWidth();
			height = this.getHeight();
			System.out.println("width: "+width+" height: "+height);


			Map<Key, Segment> segmentMap = planData.getSegmentMap();
			Map<String, Intersection> intersectionMap = planData.getIntersectionMap();
			segmentMap.forEach((key, segment) -> {
				drawSegment(g,segment,Color.BLACK, intersectionMap,false);
				/*
				Intersection origine = intersectionMap.get(segment.getOrigin());
				Intersection destination = intersectionMap.get(segment.getDestination());

				int yOrigine = getCoordinateY(origine.getLatitude(),0,width,minLatitude,maxLatitude);
				int xOrigine = getCoordinate(origine.getLongitude(),0,height,minLongitude,maxLongitude);
				//g.drawRect(xOrigine,yOrigine,10,10);

				//System.out.println("xOrigine: " + xOrigine + "\tyOrigine: " + yOrigine+ " ");

				int yDestination = getCoordinateY(destination.getLatitude(),0,width,minLatitude,maxLatitude);
				int xDestination = getCoordinate(destination.getLongitude(),0,height,minLongitude,maxLongitude);

				g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
				// g.drawString(segment.getName(),xOrigine,yOrigine);

				 */
			});

			if(planData.getPlanningRequest()!=null){
				PlanningRequest planningRequest = planData.getPlanningRequest();

				// Drawing the depot
				Intersection depot = intersectionMap.get(planningRequest.getStartId());
				int yDepot = getCoordinateY(depot.getLatitude(),0,width,minLatitude,maxLatitude);
				int xDepot = getCoordinate(depot.getLongitude(),0,height,minLongitude,maxLongitude);
				System.out.println("xDepot: "+xDepot+" yDepot: "+yDepot);
				g.setColor(Color.BLUE);
				g.fillOval(xDepot,yDepot,20,20);

				int allLength = planningRequest.getRequests().size();
				int i = 0;
				for(Request request: planningRequest.getRequests()){
					// Random color
					Color color = new Color((int)(Math.random() * 0x1000000));
					g.setColor(color);
					Intersection pickup = intersectionMap.get(request.getPickupId());
					Intersection delivery = intersectionMap.get(request.getDeliveryId());

					int yPickup = getCoordinateY(pickup.getLatitude(),0,width,minLatitude,maxLatitude);
					int xPickup = getCoordinate(pickup.getLongitude(),0,height,minLongitude,maxLongitude);
					int yDelivery = getCoordinateY(delivery.getLatitude(),0,width,minLatitude,maxLatitude);
					int xDelivery  = getCoordinate(delivery.getLongitude(),0,height,minLongitude,maxLongitude);

					// Draw pickup as square
					g.setColor(Color.getHSBColor((float)i/(float)allLength,1,1));
					g.fillRect(xPickup,yPickup,10,10);


					g.setColor(Color.getHSBColor((float)i/(float)allLength,0.5f,1));
					// Draw delivery as triangle
					g.fillRoundRect(xDelivery,yDelivery,10,10,5,5);
					i++;
				}
			}
			if(deliveryTour != null){
				List<Segment> segments = deliveryTour.getSegmentList();
				float fullLength = deliveryTour.getGlobalTime();
				float lengthCounter = 0;
				for (Segment segment:segments) {

					drawSegment(g,segment,Color.getHSBColor(0f,1,lengthCounter/fullLength), intersectionMap,true);
					lengthCounter+=segment.getLength();
					//g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
				}

			}
		}

	}

	private int getCoordinate (float x ,int a, int b, float min , float max){
		return (int)(((b-a)*(x-min)/(max-min)) + a);
	}

	private int getCoordinateY(float x,int a ,int b , float min,float max){
		return b - getCoordinate(x,a,b,min,max);
	}
}
