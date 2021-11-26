package view;

import model.Intersection;
import model.Segment;
import model.graphs.Key;
import model.graphs.Plan;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The visualisation of the map. Updated when the data changes.
 */
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

				int xOrigine = (int)(origine.getLatitude()*width/differenceLatitude) %width;
				int yOrigine = (int)(origine.getLongitude()*height/differenceLongitude) % height;

				System.out.println("xOrigine: " + xOrigine + "\tyOrigine: " + yOrigine+ " ");

				int xDestination = (int)(destination.getLatitude()*width/differenceLatitude) %width;
				int yDestination = (int)(destination.getLongitude()*height/differenceLongitude) % height;

				g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
				// g.drawString(segment.getName(),xOrigine,yOrigine);
			});
		}
	}
}
