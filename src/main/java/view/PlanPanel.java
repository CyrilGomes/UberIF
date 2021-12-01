package view;

import javafx.geometry.Point2D;
import model.*;
import model.graphs.Key;
import model.graphs.Plan;
import model.graphs.pathfinding.TSP;
import model.graphs.pathfinding.TSP1;
import model.graphs.pathfinding.TemplateTSP;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The visualisation of the map. Updated when the data changes.
 */
public class PlanPanel extends JPanel {
	private final JLabel selectedStreetLabel;
	private Plan planData;
	private Map<Key, Segment> segmentMap;
	private Map<String, Intersection> intersectionMap;
	private DeliveryTour deliveryTour;
	float maxLatitude;
	float minLatitude;
	float maxLongitude;
	float minLongitude;
	int width;
	int height;


	public PlanPanel(JLabel infoLabel) {
		super();
		this.selectedStreetLabel = infoLabel;
		this.setBackground(Color.LIGHT_GRAY);
		MouseListenerPlanPanel mouseEvent = new MouseListenerPlanPanel(this);
		this.addMouseListener(mouseEvent);
		this.addMouseWheelListener(mouseEvent);
		setVisible(true);
	}

	public void setPlanData(Plan planData) {
		this.planData = planData;
		segmentMap = planData.getSegmentMap();
		intersectionMap = planData.getIntersectionMap();
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

	private void identifyStreet(int xMouse, int yMouse){
		segmentMap.forEach((key, segment) -> {
			Intersection origine = intersectionMap.get(segment.getOrigin());
			Intersection destination = intersectionMap.get(segment.getDestination());

			int yOrigine = getCoordinateY(origine.getLatitude(),0,height,minLatitude,maxLatitude);
			int xOrigine = getCoordinate(origine.getLongitude(),0,width,minLongitude,maxLongitude);
			Point2D pointOrigine = new Point2D(xOrigine, yOrigine);

			int yDestination = getCoordinateY(destination.getLatitude(),0,height,minLatitude,maxLatitude);
			int xDestination = getCoordinate(destination.getLongitude(),0,width,minLongitude,maxLongitude);
			Point2D pointDestination = new Point2D(xDestination, yDestination);

			Point2D pointMouse = new Point2D(xMouse, yMouse);

			if((int)(pointOrigine.distance(pointMouse))
					+ (int)(pointDestination.distance(pointMouse))
					== (int)(pointDestination.distance(pointOrigine))
					&& !selectedStreetLabel.getText().equals(segment.getName())
			){
				selectedStreetLabel.setText(segment.getName());
				repaint();
			}
		});
	}

	public void onMouseClicked(int xMouse, int yMouse){
		identifyStreet(xMouse, yMouse);
	}

	public void drawSegment(Graphics g, Segment segment, Color color,Map<String, Intersection> intersectionMap, boolean isBestPath ){
		g.setColor(color);
		Intersection origine = intersectionMap.get(segment.getOrigin());
		Intersection destination = intersectionMap.get(segment.getDestination());

		int yOrigine = getCoordinateY(origine.getLatitude(),0,height,minLatitude,maxLatitude);
		int xOrigine = getCoordinate(origine.getLongitude(),0,width,minLongitude,maxLongitude);

		int yDestination = getCoordinateY(destination.getLatitude(),0,height,minLatitude,maxLatitude);
		int xDestination = getCoordinate(destination.getLongitude(),0,width,minLongitude,maxLongitude);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(isBestPath ? 3: 1));
		g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination, yDestination));
	}
	private void drawBullet(Graphics g, int x, int y, int w, int h, Color color){
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

			segmentMap.forEach((key, segment) -> {
				if(!segment.getName().isEmpty() && Objects.equals(segment.getName(), selectedStreetLabel.getText())){
					drawSegment(g,segment,Color.BLUE, intersectionMap,false);
				}
				else{
					drawSegment(g,segment,Color.WHITE, intersectionMap,false);
				}
			});

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

			if(planData.getPlanningRequest()!=null){
				PlanningRequest planningRequest = planData.getPlanningRequest();

				// Drawing the depot
				Intersection depot = intersectionMap.get(planningRequest.getStartId());
				int yDepot = getCoordinateY(depot.getLatitude(),0,height,minLatitude,maxLatitude);
				int xDepot = getCoordinate(depot.getLongitude(),0,width,minLongitude,maxLongitude);
				System.out.println("xDepot: "+xDepot+" yDepot: "+yDepot);
				g.setColor(Color.BLACK);
				//g.fillOval(xDepot,yDepot,20,20);
				//drawBullet(g, xDepot, yDepot, 40, 40,  Color.BLACK);
				g.fillRect(xDepot,yDepot,20,20);



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

					// Draw pickup as square
					g.setColor(Color.getHSBColor((float)i/(float)allLength,1,1));
					//g.fillRect(xPickup,yPickup,10,10);
					drawBullet(g, xPickup, yPickup, 25, 25,  Color.getHSBColor((float)i/(float)allLength,1,1));

					g.setColor(Color.getHSBColor((float)i/(float)allLength,0.5f,1));
					// Draw delivery as triangle
					//g.fillRoundRect(xDelivery,yDelivery,10,10,5,5);
					drawBullet(g, xDelivery,yDelivery,25,25, Color.getHSBColor((float)i/(float)allLength,1,1));
					i++;
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
