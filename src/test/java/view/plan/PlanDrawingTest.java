package view.plan;

import junit.framework.TestCase;

public class PlanDrawingTest extends TestCase {
	public void testGetCoordinate() {
		int[] initialRange1 = {0,100};
		int[] newRange1 = {100,200};
		assertEquals(150, PlanDrawing.getCoordinate(50, newRange1[0], newRange1[1], initialRange1[0], initialRange1[1]));

		int[] initialRange2 = {0,10};
		int[] newRange2 = {0,100};
		assertEquals(50, PlanDrawing.getCoordinate(5, newRange2[0], newRange2[1], initialRange2[0], initialRange2[1]));

		int[] initialRange3 = {0,10};
		int[] newRange3 = {20, 40};
		assertEquals(30, PlanDrawing.getCoordinate(5, newRange3[0], newRange3[1], initialRange3[0], initialRange3[1]));
	}

	public void testGetCoordinateY() {
	}
}