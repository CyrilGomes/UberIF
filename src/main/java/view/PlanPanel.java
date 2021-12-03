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
import java.awt.geom.Line2D;
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
	private int currentScale = 1;
	private int xPosition = -1;
	private int yPosition = -1;
	private int xMovement;
	private int yMovement;
	private boolean zoom = false;
	private boolean move = false;
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
		this.addMouseMotionListener(mouseEvent);
		setVisible(true);
	}

	public void setPlanData(Plan planData) {
		this.planData = planData;
		segmentMap = planData.getSegmentMap();
		intersectionMap = planData.getIntersectionMap();
		this.repaint();
	}

	public Plan getPlanData() {
		return planData;
	}

	public void onMouseWheel(int notches){
		if((this.currentScale+notches)>=1){
			this.currentScale += notches;
			zoom = true;
			this.repaint();
		}else{
			this.xPosition = width/2;
			this.yPosition = height/2;
		}
	}

	public void onMousePressed(int xMove, int yMove ){
		
	}
	public void onMouseDragged(int xMove, int yMove ){
		if(xPosition<0 && yPosition<0){
			this.xPosition = xMove;
			this.yPosition = yMove;
		}else {
			this.xMovement = xMove - this.xPosition;
			this.yMovement = yMove - this.yPosition;
			this.xPosition += this.xMovement;
			this.yPosition += this.yMovement;
		}
		this.move = true;
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

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if(this.zoom){
			if(this.xPosition<0 && this.yPosition<0){
				g2.translate(width/2, height/2);
				g2.scale(this.currentScale, this.currentScale);
				g2.translate(-width/2, -height/2);
			}else{
				g2.translate(this.xPosition, this.yPosition);
				g2.scale(this.currentScale, this.currentScale);
				g2.translate(-this.xPosition, -this.yPosition);
			}
			this.zoom=false;

		}else if(this.move){
			g2.translate(this.xPosition, this.yPosition);
			g2.scale(this.currentScale, this.currentScale);
			g2.translate(-this.xPosition, -this.yPosition);
			this.move=false;
		}




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

			PlanningRequest planningRequest = planData.getPlanningRequest();
			if(planningRequest!=null){

				// Drawing the depot
				Intersection depot = intersectionMap.get(planningRequest.getStartId());
				int yDepot = getCoordinateY(depot.getLatitude(),0,height,minLatitude,maxLatitude);
				int xDepot = getCoordinate(depot.getLongitude(),0,width,minLongitude,maxLongitude);
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

					int yPickup = getCoordinateY(pickup.getLatitude(),0,height,minLatitude,maxLatitude);
					int xPickup = getCoordinate(pickup.getLongitude(),0,width,minLongitude,maxLongitude);
					int yDelivery = getCoordinateY(delivery.getLatitude(),0,height,minLatitude,maxLatitude);
					int xDelivery  = getCoordinate(delivery.getLongitude(),0,width,minLongitude,maxLongitude);

					// Draw pickup as square
					g.setColor(Color.getHSBColor((float)i/(float)allLength,1,1));
					g.fillRect(xPickup,yPickup,10,10);


					g.setColor(Color.getHSBColor((float)i/(float)allLength,0.5f,1));
					// Draw delivery as triangle
					g.fillRoundRect(xDelivery,yDelivery,10,10,5,5);
					i++;
				}
			}
			DeliveryTour deliveryTour = planData.getDeliveryTour();
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
