package view;

import model.Request;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PointOfInterestPanel extends JPanel {

    /**
     * boolean to know if the point is a pickup.
     */
    private boolean isPickUp;

    /**
     * time passage on the point.
     */
    private String timePassage;

    /**
     * request associated to the point.
     */
    private Request request;

    /**
     * number on the list of points of interest.
     */
    private int i;

    /** Constructor
     * @param isPickUp true if the point is a pickup, false if it's a delivery
     * @param timePassage the time of passage of this point
     * @param request the request containing this point of interest
     * @param i number on the list of points of interest
     */
    public PointOfInterestPanel(boolean isPickUp, String timePassage, Request request,int i) {
        this.isPickUp = isPickUp;
        this.timePassage = timePassage;
        this.request = request;
        this.i = i;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel((isPickUp ? "Pickup":"Delivery")+" n°"+i);
        label1.setFont(new Font("Verdana",1,14));
        label1.setForeground(request.getColor());
        add(label1);
        JLabel label2 = new JLabel("Intersection id: "+(isPickUp? request.getPickupId() : request.getDeliveryId()));
        label2.setFont(new Font("Verdana",1,14));
        add(label2);
        JLabel label3  = new JLabel("Time of passage: "+timePassage);
        label3.setFont(new Font("Verdana",1,14));
        add(label3);

        add(Box.createVerticalStrut(15));
        EmptyBorder emptyBorder = new EmptyBorder(0,10,0,0);
        setBorder(emptyBorder);

    }

    public boolean isPickUp() {
        return isPickUp;
    }

    public Request getRequest() {
        return request;
    }
}
