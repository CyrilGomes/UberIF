package view.plan;

import model.DeliveryTour;
import model.Intersection;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Key;
import model.graphs.Plan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;

/**
 * Utility class used to draw data from the plan or the request on PlanPanel.
 * @see PlanPanel
 */
public class PlanDrawing {
	/** Data of the current context. **/
	private final Plan planData;
	/** Graphical component to draw onto. **/
	private final PlanPanel planPanel;
	/** Data structure containing the segments of the map. **/
	private final Map<Key, Segment> segmentMap;
	/** Data structure containing the intersections of the map. **/
	private final Map<String, Intersection> intersectionMap;
	/** The graphical object used to draw on the panel. **/
	private final Graphics g;
	/** The name of the street that the user selected. **/
	private final String selectedStreetName;

	/**
	 * Creates a new instance of PlanDrawing (called on a new execution of
     * the pain function in PlanPanel).
	 * @see PlanPanel
	 * @param data The data of the plan in the current context
	 * @param panel The graphical component of the map
	 * @param graphics The graphical object used to draw on the panel
	 */
	public PlanDrawing(final Plan data, final PlanPanel panel,
                       final Graphics graphics) {
		this.planData = data;
		this.planPanel = panel;
		this.g = graphics;
		segmentMap = this.planData.getSegmentMap();
		intersectionMap = this.planData.getIntersectionMap();
		selectedStreetName = this.planData.getSelectedStreetName();
	}

	/**
	 * Draws the segments of the map in white.
	 * If the user has selected a street, every segment composing
     * it is drawn in blue.
	 */
	public void drawPlan() {
		segmentMap.forEach((key, segment) -> {
			if (!selectedStreetName.isEmpty() && segment.getName()
                    .equals(selectedStreetName)) {
				drawSegment(segment, Color.BLUE, 1);
			} else {
				drawSegment(segment, Color.WHITE, 1);
			}
		});
	}

	/**
	 * Draws every point of interest from the request of the current
     * context.
     * @param clickablePOI Data structure containing the information on a
     *                     clickable point of interest
	 */
	public void drawPOI(final ClickablePOI clickablePOI) {
		PlanningRequest planningRequest = planData.getPlanningRequest();

		// Drawing the depot
		Intersection depot = intersectionMap.get(planningRequest
                .getStartId());
		int yDepot = planPanel.scaleYCoordinateToPlan(depot
                .getLatitude());
		int xDepot = planPanel.scaleXCoordinateToPlan(depot
                .getLongitude());
		//System.out.println("xDepot: " + xDepot + " yDepot: " + yDepot);
		g.setColor(Color.BLACK);
        g.fillRect(xDepot, yDepot, 20, 20);

		// Drawing every other point of interest
		int allLength = planningRequest.getRequests().size();
		int i = 0;
		for (Request request: planningRequest.getRequests()) {
			Intersection pickup = intersectionMap.get(request
                    .getPickupId());
			Intersection delivery = intersectionMap.get(request
                    .getDeliveryId());

			int yPickup = planPanel
                    .scaleYCoordinateToPlan(pickup.getLatitude());
			int xPickup = planPanel
                    .scaleXCoordinateToPlan(pickup.getLongitude());
			int yDelivery = planPanel
                    .scaleYCoordinateToPlan(delivery.getLatitude());
			int xDelivery  = planPanel
                    .scaleXCoordinateToPlan(delivery.getLongitude());

			// Draw pickup as a map bullet point
			Color color = Color.getHSBColor((float) i++
                    / (float) allLength, 1, 1);
			if(request.getColor()==null){
				request.setColor(color);
			}
			drawPickupPoint(xPickup, yPickup, 25, 25, request.getColor());

			// Draw delivery as house icon
			drawDeliveryPoint(xDelivery, yDelivery, 30, 30, request.getColor());
			clickablePOI.updateTrack(pickup, 25 / 2, delivery,
                    30 / 2);
		}
	}

	/**
	 * Draws a delivery point.
	 * @param x x coordinate of the delivery point
	 * @param y y coordinate of the delivery point
	 * @param w width of the icon
	 * @param h height of the icon
	 * @param color color of the icon
	 */
	private void drawDeliveryPoint(final int x, final int y, final int w,
                                   final int h, final Color color) {
		Graphics2D ga = (Graphics2D) g;

		//Positioning the end of the house in the route coordinate
		//y = y-h;

		//chimney
		Shape square3 = new Rectangle2D.Double(x - w / 2.4, y - h / 1.7,
                w / 5, h / 4);
		ga.draw(square3);
		ga.setPaint(color);
		ga.fill(square3);

		//Triangle - roof
		Path2D path = new Path2D.Double();
		double firstX = x - (w / 1.5);
		double firstY = y * 1.0 - 5.0;
		path.moveTo(firstX, firstY);
		path.lineTo(firstX + 2 * w / 1.5, firstY);
		path.lineTo(x, firstY - h / 2);
		path.closePath();
		ga.setColor(color);
		ga.fill(path);


		Shape square = new Rectangle2D.Double(x - w / 3, y - h / 4,
                w / 1.5, h / 3);
		ga.draw(square);
		ga.setPaint(color);
		ga.fill(square);

		//Door
		Shape square2 = new Rectangle2D.Double(x - w / 10, y - h / 4,
                w / 5, h / 3);
		ga.draw(square2);
		ga.setPaint(Color.WHITE);
		ga.fill(square2);
	}

	/**
	 * Draws a pickup point.
	 * @param x x coordinate of the delivery point
	 * @param y y coordinate of the delivery point
	 * @param w width of the icon
	 * @param h height of the icon
	 * @param color color of the icon
	 */
	private void drawPickupPoint(final int x, int y, final int w,
                                 final int h, final Color color) {
		Graphics2D ga = (Graphics2D) g;

		//Positioning the end of the bullet in the route coordinate
		y = y - h;

		Path2D path = new Path2D.Double();
		double firstX = x - (w / 2.0);
		double firstY = y * 1.0;
		path.moveTo(firstX, firstY);
		path.lineTo(firstX + w, firstY);
		path.lineTo(x, firstY + h);
		path.closePath();
		ga.setColor(color);
		ga.fill(path);

		Shape semiCircle = new Arc2D.Double(x - w / 2, y - h / 2, w, h,
                0, 180, Arc2D.OPEN);
		ga.draw(semiCircle);
		ga.setPaint(color);
		ga.fill(semiCircle);


		Shape circle = new Ellipse2D.Float(x - w / 4, y - h / 4,
                w / 2, h / 2);
		ga.draw(circle);
		ga.setPaint(Color.WHITE);
		ga.fill(circle);
	}

	/**
	 * Draws the best route in the current context.
	 * @param deliveryTour Data on the best route
	 */
	public void drawRequestsRoute(final DeliveryTour deliveryTour) {
		drawRequestsRoute(deliveryTour, null);
	}

	/**
	 * Draws the best route in the current context.
	 * @param deliveryTour Data on the best route
	 * @param selectedPOI The POI that is currently selected in the route
	 */
	public void drawRequestsRoute(final DeliveryTour deliveryTour,
	                              final Intersection selectedPOI) {
		List<Segment> segments = deliveryTour.getSegmentList();
		float fullLength = deliveryTour.getGlobalTime();
		float lengthCounter = 0;

		boolean startHighlighting = false;

		for (Segment segment:segments) {
			if (!selectedStreetName.isEmpty()
                    && segment.getName().equals(selectedStreetName)) {
				drawSegment(segment, Color.BLUE, 3);
			} else {
				Color color = Color.getHSBColor(0f, 1,
                        lengthCounter / fullLength);
				if (selectedPOI != null) {
					if (selectedPOI == intersectionMap
                            .get(segment.getOrigin())) {
						startHighlighting = !startHighlighting;
					}
					if (startHighlighting) {
						color = Color.YELLOW;
					} else {
						color = Color.GRAY;
					}
				}
				drawSegment(segment, color, 3);
			}

			lengthCounter += segment.getLength();
		}
	}

	/**
	 * Draws a segment on the graphical component.
	 * @param segment Data on the segment
	 * @param color Color of the segment
	 * @param stroke Width of the segment (used to determine if it is part
     *               of a best route or not)
	 */
	private void drawSegment(final Segment segment, final Color color,
                             final int stroke) {
		g.setColor(color);
		Intersection origine = intersectionMap.get(segment.getOrigin());
		Intersection destination = intersectionMap.get(segment
                .getDestination());

		int yOrigine = planPanel.scaleYCoordinateToPlan(origine
                .getLatitude());
		int xOrigine = planPanel.scaleXCoordinateToPlan(origine
                .getLongitude());

		int yDestination = planPanel.scaleYCoordinateToPlan(destination
                .getLatitude());
		int xDestination = planPanel.scaleXCoordinateToPlan(destination
                .getLongitude());

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(stroke));
		g2.draw(new Line2D.Float(xOrigine, yOrigine, xDestination,
                yDestination));
	}
}
