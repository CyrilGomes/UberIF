package view;

import java.awt.event.*;

public class MouseListenerPlanPanel implements MouseListener, MouseWheelListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        System.out.println("mouse wheel used");

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
