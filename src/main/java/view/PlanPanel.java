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
			float differenceLatitude  = planData.getDifferenceLatitude();
			float differenceLongitude = planData.getDifferenceLongitude();
			int width = this.getWidth();
			int height = this.getHeight();
			System.out.println("width: "+width+" height: "+height);

			System.out.println("differenceLatitude: "+differenceLatitude+" differenceLongitude: "+differenceLongitude);
			g.setColor(Color.BLACK);

			Map<Key, Segment> segmentMap = planData.getSegmentMap();
			Map<String, Intersection> intersectionMap = planData.getIntersectionMap();
			segmentMap.forEach((key, segment) -> {
				Intersection origine = intersectionMap.get(segment.getOrigin());
				Intersection destination = intersectionMap.get(segment.getDestination());

				int xOrigine = getCoordinate(origine.getLatitude(),differenceLatitude,width);
				int yOrigine = getCoordinate(origine.getLongitude(),differenceLongitude,height);

				System.out.println("xOrigine: " + xOrigine + "\tyOrigine: " + yOrigine+ " ");

				int xDestination = getCoordinate(destination.getLatitude(),differenceLatitude,width);
				int yDestination = getCoordinate(destination.getLongitude(),differenceLongitude,height);

				g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
				g.drawString(segment.getName(),xOrigine,yOrigine);
			});

			if(planData.getPlanningRequest()!=null){
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
			}
		}
	}

	private int getCoordinate (float latitude,float difference, int width){
		return (int)(latitude*width/difference)%width;
	}
}
