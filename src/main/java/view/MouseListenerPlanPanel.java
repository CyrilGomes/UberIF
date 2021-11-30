package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListenerPlanPanel implements MouseListener, MouseWheelListener {

    private PlanPanel plan;
    public MouseListenerPlanPanel(PlanPanel planInit){
        this.plan = planInit;

    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        System.out.println("mouse wheel used");
        int notches = e.getWheelRotation();
        System.out.println("wheel moved "+notches+" bits!");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        final int x = e.getX();
        final int y = e.getY();

        plan.onMouseClicked(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e){
        System.out.println("Mouse pressed!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
