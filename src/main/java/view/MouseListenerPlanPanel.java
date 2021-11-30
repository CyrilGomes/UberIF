package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseListenerPlanPanel extends MouseAdapter {

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        int notches = e.getWheelRotation();
        System.out.println("mouse wheel rotates "+notches+" bit!");

    }

    @Override
    public void mousePressed(MouseEvent e){
        System.out.println("Mouse clicked!");
    }
}
