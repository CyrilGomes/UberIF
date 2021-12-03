package view.plan;

import javafx.geometry.Point2D;
import model.DeliveryTour;
import model.Intersection;
import model.PlanningRequest;
import model.Segment;
import model.graphs.Key;
import model.graphs.Plan;
import view.MouseListenerPlanPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Map;

/**
 * The visualisation of the map. Updated when the data changes.
 */
public class PlanPanel extends JPanel {
	private final JLabel selectedStreetLabel;
	private Plan planData;
	private Map<Key, Segment> segmentMap;
	private Map<String, Intersection> intersectionMap;
	private DeliveryTour deliveryTour;
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

	public void setPlanningRequest(PlanningRequest planningRequest){
		planData.setPlanningRequest(planningRequest);
		this.repaint();
	}

	public void setDeliveryTour(DeliveryTour deliveryTour){
		this.deliveryTour = deliveryTour;
		this.repaint();
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
	//The bullet point icons for the pickups
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
	//The houses icons for the deliveries
	private void drawHouse(Graphics g, int x, int y, int w, int h, Color color){
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

	/*@Override
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
					drawHouse(g, xDelivery,yDelivery,30,30, Color.getHSBColor((float)i/(float)allLength,1,1));
					i++;
				}
			}

		}
	}*/

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

		if(planData != null){
			PlanDrawing planDrawing = new PlanDrawing(planData, this, g);
			planDrawing.drawPlan();
			if(deliveryTour != null){
				planDrawing.drawRequestsRoute(deliveryTour);
			}
			if(planData.getPlanningRequest() != null){
				planDrawing.drawPOI();
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
