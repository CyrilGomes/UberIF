package view;

import model.Intersection;
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

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Paint");
		g.drawLine(0,0, 50, 50);
		if(planData != null){
			g.setColor(Color.BLACK);
			Map<Key, Segment> segmentMap = planData.getSegmentMap();
			Map<String, Intersection> intersectionMap = planData.getIntersectionMap();
			segmentMap.forEach((key, segment) -> {
				Intersection origine = intersectionMap.get(segment.getOrigin());
				Intersection destination = intersectionMap.get(segment.getDestination());

				int xOrigine = (int)(((origine.getLongitude() + 180) * this.getWidth()) / 360);
				int yOrigine = (int)(((origine.getLatitude() + 360) * this.getHeight()) / 180);

				System.out.println("xOrigine: " + xOrigine + "\tyOrigine: " + yOrigine+ " ");

				int xDestination = (int)((destination.getLatitude() * this.getWidth()) / 360);
				int yDestination = (int)((destination.getLongitude() * this.getHeight()) / 180);

				g.drawLine(xOrigine, yOrigine, xDestination, yDestination);
			});
		}
	}
}
