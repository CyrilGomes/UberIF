package view.plan;

import model.*;
import model.graphs.Key;
import model.graphs.Plan;

import java.awt.*;
import java.awt.geom.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PlanDrawing {
	private final Plan planData;
	private final Map<Key, Segment> segmentMap;
	private final Map<String, Intersection> intersectionMap;
	private final Graphics g;

	private final float maxLatitude;
	private final float minLatitude;
	private final float maxLongitude;
	private final float minLongitude;
	private final int width;
	private final int height;

	public PlanDrawing(Plan planData, PlanPanel panel, Graphics g){
		this.planData = planData;
		this.g = g;
		segmentMap = this.planData.getSegmentMap();
		intersectionMap = this.planData.getIntersectionMap();

		maxLatitude = this.planData.getMaxLatitude();
		minLatitude = this.planData.getMinLatitude();
		maxLongitude = this.planData.getMaxLongitude();
		minLongitude = this.planData.getMinLongitude();
		width = panel.getWidth();
		height = panel.getHeight();
	}

	public void drawPlan(){
		segmentMap.forEach((key, segment) -> {
			drawSegment(segment, Color.WHITE, 1);
		});
	}

	public void drawPOI(){
		PlanningRequest planningRequest = planData.getPlanningRequest();

		// Drawing the depot
		Intersection depot = intersectionMap.get(planningRequest.getStartId());
		int yDepot = getCoordinateY(depot.getLatitude(),0,height,minLatitude,maxLatitude);
		int xDepot = getCoordinate(depot.getLongitude(),0,width,minLongitude,maxLongitude);
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

			int yPickup = getCoordinateY(pickup.getLatitude(),0,height,minLatitude,maxLatitude);
			int xPickup = getCoordinate(pickup.getLongitude(),0,width,minLongitude,maxLongitude);
			int yDelivery = getCoordinateY(delivery.getLatitude(),0,height,minLatitude,maxLatitude);
			int xDelivery  = getCoordinate(delivery.getLongitude(),0,width,minLongitude,maxLongitude);

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

			drawSegment(segment,Color.getHSBColor(0f,1,lengthCounter/fullLength),3);
			lengthCounter+=segment.getLength();
		}
	}

	public void highlightStreet(Collection<Segment> streetSegments){
		streetSegments.forEach(segment ->  {
			drawSegment(segment, Color.BLUE, 1);
		});
	}

	private void drawSegment(Segment segment, Color color, int stroke){
		g.setColor(color);
		Intersection origine = intersectionMap.get(segment.getOrigin());
		Intersection destination = intersectionMap.get(segment.getDestination());

		int yOrigine = getCoordinateY(origine.getLatitude(),0,height,minLatitude,maxLatitude);
		int xOrigine = getCoordinate(origine.getLongitude(),0,width,minLongitude,maxLongitude);

		int yDestination = getCoordinateY(destination.getLatitude(),0,height,minLatitude,maxLatitude);
		int xDestination = getCoordinate(destination.getLongitude(),0,width,minLongitude,maxLongitude);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination, yDestination));
	}

	private int getCoordinate (float x ,int a, int b, float min , float max){
		return (int)(((b-a)*(x-min)/(max-min)) + a);
	}

	private int getCoordinateY(float x,int a ,int b , float min,float max){
		return b - getCoordinate(x,a,b,min,max);
	}
}
