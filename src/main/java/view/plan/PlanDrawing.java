package view.plan;

import model.*;
import model.graphs.Key;
import model.graphs.Plan;

import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Map;

public class PlanDrawing {
	private final Plan planData;
	private final PlanPanel planPanel;
	private final Map<Key, Segment> segmentMap;
	private final Map<String, Intersection> intersectionMap;
	private final Graphics g;
	private final String selectedStreetName;

	public PlanDrawing(Plan planData, PlanPanel panel, Graphics g){
		this.planData = planData;
		this.planPanel = panel;
		this.g = g;
		segmentMap = this.planData.getSegmentMap();
		intersectionMap = this.planData.getIntersectionMap();
		selectedStreetName = this.planData.getSelectedStreetName();
	}

	public void drawPlan(){
		segmentMap.forEach((key, segment) -> {
			if(!selectedStreetName.isEmpty() && segment.getName().equals(selectedStreetName)){
				drawSegment(segment, Color.BLUE, 1);
			}
			else{
				drawSegment(segment, Color.WHITE, 1);
			}
		});
	}

	public void drawPOI(){
		PlanningRequest planningRequest = planData.getPlanningRequest();

		// Drawing the depot
		Intersection depot = intersectionMap.get(planningRequest.getStartId());
		int yDepot = planPanel.scaleYCoordinateToPlan(depot.getLatitude());
		int xDepot = planPanel.scaleXCoordinateToPlan(depot.getLongitude());
		System.out.println("xDepot: "+xDepot+" yDepot: "+yDepot);
		g.setColor(Color.BLACK);
		g.fillRect(xDepot,yDepot,20,20);

		// Drawing every other point of interest
		int allLength = planningRequest.getRequests().size();
		int i = 0;
		for(Request request: planningRequest.getRequests()){
			// Random color
			Color color = new Color((int)(Math.random() * 0x1000000));
			g.setColor(color);
			Intersection pickup = intersectionMap.get(request.getPickupId());
			Intersection delivery = intersectionMap.get(request.getDeliveryId());

			int yPickup = planPanel.scaleYCoordinateToPlan(pickup.getLatitude());
			int xPickup = planPanel.scaleXCoordinateToPlan(pickup.getLongitude());
			int yDelivery = planPanel.scaleYCoordinateToPlan(delivery.getLatitude());
			int xDelivery  = planPanel.scaleXCoordinateToPlan(delivery.getLongitude());

			// Draw pickup as a map bullet point
			g.setColor(Color.getHSBColor((float)i/(float)allLength,1,1));
			drawPickupPoint(xPickup, yPickup, 25, 25,  Color.getHSBColor((float)i/(float)allLength,1,1));

			// Draw delivery as house icon
			g.setColor(Color.getHSBColor((float)i/(float)allLength,0.5f,1));
			drawDeliveryPoint(xDelivery,yDelivery,30,30, Color.getHSBColor((float)i/(float)allLength,1,1));
			i++;
		}
	}

	private void drawDeliveryPoint(int x, int y, int w, int h, Color color){
		Graphics2D ga = (Graphics2D) g;

		//Positioning the end of the house in the route coordinate
		//y = y-h;

		//chimney
		Shape square3 = new Rectangle2D.Double( x-w/2.4, y-h/1.7, w/5, h/4);
		ga.draw(square3);
		ga.setPaint(color);
		ga.fill(square3);

		//Triangle - roof
		Path2D path = new Path2D.Double();
		Double firstX = x - (w/1.5);
		Double firstY = y * 1.0 - 5.0;
		path.moveTo(firstX,firstY);
		path.lineTo(firstX + 2*w/1.5, firstY);
		path.lineTo(x, firstY - h/2);
		path.closePath();
		ga.setColor(color);
		ga.fill(path);


		Shape square = new Rectangle2D.Double( x-w/3, y-h/4, w/1.5, h/3);
		ga.draw(square);
		ga.setPaint(color);
		ga.fill(square);

		//Door
		Shape square2 = new Rectangle2D.Double( x-w/10, y-h/4, w/5, h/3);
		ga.draw(square2);
		ga.setPaint(Color.WHITE);
		ga.fill(square2);
	}

	private void drawPickupPoint(int x, int y, int w, int h, Color color){
		Graphics2D ga = (Graphics2D) g;

		//Positioning the end of the bullet in the route coordinate
		y = y-h;

		Path2D path = new Path2D.Double();
		Double firstX = x - (w/2.0);
		Double firstY = y * 1.0;
		path.moveTo(firstX,firstY);
		path.lineTo(firstX + w, firstY);
		path.lineTo(x, firstY + h);
		path.closePath();
		ga.setColor(color);
		ga.fill(path);

		Shape semiCircle = new Arc2D.Double( x-w/2, y-h/2, w, h, 0, 180, Arc2D.OPEN);
		ga.draw(semiCircle);
		ga.setPaint(color);
		ga.fill(semiCircle);


		Shape circle = new Ellipse2D.Float( x-w/4, y-h/4, w/2, h/2);
		ga.draw(circle);
		ga.setPaint(Color.WHITE);
		ga.fill(circle);
	}

	public void drawRequestsRoute(DeliveryTour deliveryTour){
		List<Segment> segments = deliveryTour.getSegmentList();
		float fullLength = deliveryTour.getGlobalTime();
		float lengthCounter = 0;
		for (Segment segment:segments) {
			if(!selectedStreetName.isEmpty() && segment.getName().equals(selectedStreetName)){
				drawSegment(segment,Color.BLUE,3);
			}
			else{
				drawSegment(segment,Color.getHSBColor(0f,1,lengthCounter/fullLength),3);
			}

			lengthCounter+=segment.getLength();
		}
	}

	private void drawSegment(Segment segment, Color color, int stroke){
		g.setColor(color);
		Intersection origine = intersectionMap.get(segment.getOrigin());
		Intersection destination = intersectionMap.get(segment.getDestination());

		int yOrigine = planPanel.scaleYCoordinateToPlan(origine.getLatitude());
		int xOrigine = planPanel.scaleXCoordinateToPlan(origine.getLongitude());

		int yDestination = planPanel.scaleYCoordinateToPlan(destination.getLatitude());
		int xDestination = planPanel.scaleXCoordinateToPlan(destination.getLongitude());

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination, yDestination));
	}
}
