package view;

import controller.ControllerMainWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListenerMainWindow implements KeyListener {
    private ControllerMainWindow controller;

    public KeyboardListenerMainWindow(ControllerMainWindow controllerMainWindow){
        this.controller = controllerMainWindow;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            System.out.println("Go back !");
            controller.undo();

        }else if((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            System.out.println("Go forward !");
            controller.redo();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
