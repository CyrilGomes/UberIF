package view.plan;

import javafx.geometry.Point2D;
import model.DeliveryTour;
import model.Intersection;
import model.Segment;
import model.graphs.Key;
import model.graphs.Plan;
import view.MouseListenerPlanPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * The visualisation of the map. Updated when the data changes.
 */
public class PlanPanel extends JPanel {
	private final JLabel selectedStreetLabel;
	private Plan planData;
	private Map<Key, Segment> segmentMap;
	private Map<String, Intersection> intersectionMap;
	private ArrayList<Segment> selectedStreetSegments;
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

	/**
	 * Creates a new instance of the graphical component and initializes its listeners
	 * @param infoLabel Label of the system info text
	 */
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

	/**
	 * Sets a new context to the visualization of the map
	 * @param planData The data of the new context
	 */
	public void setPlanData(Plan planData) {
		this.planData = planData;
		segmentMap = planData.getSegmentMap();
		intersectionMap = planData.getIntersectionMap();
		maxLatitude = planData.getMaxLatitude();
		minLatitude = planData.getMinLatitude();
		maxLongitude = planData.getMaxLongitude();
		minLongitude = planData.getMinLongitude();
		this.repaint();
	}

	public Plan getPlanData() {
		return planData;
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	/**
	 * Detects if the user is zooming on the map
	 * @param notches Amount of zoom applied
	 */
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

	public void onMousePressed(int xMove, int yMove ){}

	/**
	 * Detects if the user is moving a zoomed map
	 * @param xMove x coordinate of the mouse after the drag
	 * @param yMove y coordinate of the mouse after the drag
	 */
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

	/**
	 * Detects if the user has selected something on the map
	 * @param xMouse x coordinate of the mouse
	 * @param yMouse y coordinate of the mouse
	 */
	public void onMouseClicked(int xMouse, int yMouse){
		identifyStreet(xMouse, yMouse);
		repaint();
	}

	/**
	 * Scales an x coordinate to the current size of the graphical component
	 * @param x Coordinate to scale
	 * @return the scaled x coordinate
	 */
	public int scaleXCoordinateToPlan(float x){
		if(x < minLongitude || x > maxLongitude){
			return -1;
		}
		return (int)Math.floor((width*(x-minLongitude)/(maxLongitude-minLongitude)));
	}

	/**
	 * Scales and flips the y coordinate to fit the current size and orientation of the graphical component
	 * @param y Coordinate to convert
	 * @return the converted y coordinate
	 */
	public int scaleYCoordinateToPlan(float y){
		if(y < minLatitude || y > maxLatitude){
			return -1;
		}
		return flipYAxis((int)Math.floor((height*(y-minLatitude)/(maxLatitude-minLatitude))));
	}

	/**
	 * Flips a y coordinate to fit the current orientation of the graphical component
	 * @param y The coordinate to flip
	 * @return the flipped coordinate
	 */
	private int flipYAxis(int y){
		return height - y;
	}

	/**
	 * Identifies a street selected by the user.
	 * Checks, for all segments of the map, whether the x and y coordinates of the mouse are inside of one, which sets the name of the street.
	 * @param xMouse x coordinate of the mouse
	 * @param yMouse y coordinate of the mouse
	 */
	public void identifyStreet(int xMouse, int yMouse){
		for (Key value : segmentMap.keySet()) {
			Segment segment = segmentMap.get(value);
			Intersection origine = intersectionMap.get(segment.getOrigin());
			Intersection destination = intersectionMap.get(segment.getDestination());

			int yOrigine = scaleYCoordinateToPlan(origine.getLatitude());
			int xOrigine = scaleXCoordinateToPlan(origine.getLongitude());
			Point2D pointOrigine = new Point2D(xOrigine, yOrigine);

			int yDestination = scaleYCoordinateToPlan(destination.getLatitude());
			int xDestination = scaleXCoordinateToPlan(destination.getLongitude());
			Point2D pointDestination = new Point2D(xDestination, yDestination);

			Point2D pointMouse = new Point2D(xMouse, yMouse);

			if((int)(pointOrigine.distance(pointMouse))
					+ (int)(pointDestination.distance(pointMouse))
					== (int)(pointDestination.distance(pointOrigine))
					&& !selectedStreetLabel.getText().equals(segment.getName())
			){
				planData.setSelectedStreetName(segment.getName());
				selectedStreetLabel.setText(segment.getName());
				break;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		width = this.getWidth();
		height = this.getHeight();

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
			// Sélection et ordonnancement des logiques de dessin
			PlanDrawing planDrawing = new PlanDrawing(planData, this, g);
			planDrawing.drawPlan();
			DeliveryTour deliveryTour = planData.getDeliveryTour();
			if(deliveryTour != null) {
				planDrawing.drawRequestsRoute(deliveryTour);
			}
			if(planData.getPlanningRequest() != null){
				planDrawing.drawPOI();
			}
		}
	}
}
