package view.plan;

import javafx.geometry.Point2D;
import model.*;
import model.graphs.Key;
import model.graphs.Plan;
import view.MouseListenerPlanPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static view.plan.PlanDrawing.getCoordinate;
import static view.plan.PlanDrawing.getCoordinateY;

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
	public OnPointClick onPointClick;

	public interface OnPointClick {
		void onPointClick(Request request, boolean isPickup);
	}


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
		for (Key value : segmentMap.keySet()) {
			Segment segment = segmentMap.get(value);
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
				planData.setSelectedStreetName(segment.getName());
				selectedStreetLabel.setText(segment.getName());
				break;
			}
		}
	}

	public void onMouseClicked(int xMouse, int yMouse){
		checkIfDeliveryPointClicked(xMouse, yMouse);
		identifyStreet(xMouse, yMouse);
		repaint();
	}

	public boolean intersectionIsClicked(Intersection intersection, int mouseX, int mouseY) {
		int y = getCoordinateY(intersection.getLatitude(),0,height,minLatitude,maxLatitude);
		int x = getCoordinate(intersection.getLongitude(),0,width,minLongitude,maxLongitude);

		return (Math.abs(x - mouseX) < 80 && Math.abs(y - mouseY) < 80);
	}

	public void checkIfDeliveryPointClicked(int xMouse, int yMouse) {

		for (Request request : planData.getPlanningRequest().getRequests()) {
			String id = request.getPickupId();
			Intersection pickup = intersectionMap.get(id);
			if (intersectionIsClicked(pickup, xMouse, yMouse))
			{

				System.out.println("Clicked on a pickup point");
				System.out.println("pickup duration : " + request.getPickupDuration());
				System.out.println("pickup Time passage" + request.getPickupTimePassage());
				planData.clickedRequest = request;
				planData.clickedRequestIsPickup = true;
				planData.onPointClick.onPointClick(request, true);
			}
			id = request.getDeliveryId();
			Intersection delivery = intersectionMap.get(id);
			if (intersectionIsClicked(delivery, xMouse, yMouse))
			{

				System.out.println("Clicked on a delivery point");
				System.out.println("delivery duration : " + request.getDeliveryDuration());
				System.out.println("delivery Time passage" + request.getDeliveryTimePassage());
				System.out.println("delivery Time passage" + request.getDeliveryTimePassage());
				planData.clickedRequest = request;
				planData.clickedRequestIsPickup = false;
				planData.onPointClick.onPointClick(request, false);

			}

		}


		String z1 = planData.getPlanningRequest().getRequests().get(0).getPickupId();
		String z2 = planData.getPlanningRequest().getRequests().get(0).getDeliveryId();

		//System.out.println(planData.getIntersectionMap().get(z1).getLatitude());
		//System.out.println(planData.getIntersectionMap().get(z2).getLatitude());

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
			maxLatitude = planData.getMaxLatitude();
			minLatitude = planData.getMinLatitude();
			maxLongitude = planData.getMaxLongitude();
			minLongitude = planData.getMinLongitude();

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
