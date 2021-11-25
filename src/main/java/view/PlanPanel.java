package view;

import model.Intersection;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Key;
import model.graphs.Plan;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PlanPanel extends JComponent {
	private Plan planData;

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

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Paint");
		g.drawLine(0,0, 50, 50);
		if(planData != null){
			float maxLatitude = planData.getMaxLatitude();
			float minLatitude = planData.getMinLatitude();
			float maxLongitude = planData.getMaxLongitude();
			float minLongitude = planData.getMinLongitude();
			int width = this.getWidth();
			int height = this.getHeight();
			System.out.println("width: "+width+" height: "+height);

			g.setColor(Color.BLACK);

			Map<Key, Segment> segmentMap = planData.getSegmentMap();
			Map<String, Intersection> intersectionMap = planData.getIntersectionMap();
			segmentMap.forEach((key, segment) -> {
				Intersection origine = intersectionMap.get(segment.getOrigin());
				Intersection destination = intersectionMap.get(segment.getDestination());

				int yOrigine = getCoordinateY(origine.getLatitude(),0,width,minLatitude,maxLatitude);
				int xOrigine = getCoordinate(origine.getLongitude(),0,height,minLongitude,maxLongitude);

				System.out.println("xOrigine: " + xOrigine + "\tyOrigine: " + yOrigine+ " ");

				int yDestination = getCoordinateY(destination.getLatitude(),0,width,minLatitude,maxLatitude);
				int xDestination = getCoordinate(destination.getLongitude(),0,height,minLongitude,maxLongitude);

				g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
				// g.drawString(segment.getName(),xOrigine,yOrigine);
			});

			/*if(planData.getPlanningRequest()!=null){
				PlanningRequest planningRequest = planData.getPlanningRequest();

				// Drawing the depot
				Intersection depot = intersectionMap.get(planningRequest.getStartId());
				int xDepot = getCoordinate(depot.getLatitude(),differenceLatitude,width);
				int yDepot = getCoordinate(depot.getLongitude(),differenceLongitude,height);
				System.out.println("xDepot: "+xDepot+" yDepot: "+yDepot);
				g.setColor(Color.BLUE);
				g.fillOval(xDepot,yDepot,20,20);

				for(Request request: planningRequest.getRequests()){
					// Random color
					Color color = new Color((int)(Math.random() * 0x1000000));
					g.setColor(color);
					Intersection pickup = intersectionMap.get(request.getPickupId());
					Intersection delivery = intersectionMap.get(request.getDeliveryId());

					int xPickup = getCoordinate(pickup.getLatitude(),differenceLatitude,width);
					int yPickup = getCoordinate(pickup.getLongitude(),differenceLongitude,height);
					int xDelivery = getCoordinate(delivery.getLatitude(),differenceLatitude,width);
					int yDelivery  = getCoordinate(delivery.getLongitude(),differenceLongitude,height);

					// Draw pickup as square

					// Draw delivery as triangle
				}
			}*/
		}
	}

	private int getCoordinate (float x ,int a, int b, float min , float max){
		return (int)(((b-a)*(x-min)/(max-min)) + a);
	}

	private int getCoordinateY(float x,int a ,int b , float min,float max){
		return b - getCoordinate(x,a,b,min,max);
	}
}
