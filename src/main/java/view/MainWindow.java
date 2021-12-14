/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.ControllerMainWindow;
import model.DeliveryTour;
import model.Intersection;
import model.PlanningRequest;
import model.Request;
import model.graphs.Plan;
import model.graphs.pathfinding.TSP;
import observer.Observable;
import observer.Observer;
import view.plan.PlanPanel;
import view.state.CalculatingTimesState;
import view.state.ReadyState;
import view.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main class, displaying the HMI and starting the application.
 *
 * @author Thibaud Martin
 * @author Aurelia Inard
 */
public class MainWindow extends javax.swing.JFrame implements Observer {

    /**
     * the panel where the map is displayed.
     */
    private final PlanPanel planPanel;

    /**
     * the controller of the window.
     */
    private final ControllerMainWindow controller;

    /**
     * the current state of the application.
     */
    private State currentState;
    /**
     * The currently highlighted on the summary panel
     */
    private PointOfInterestPanel highlighted;
    /**
     * the panel managing the point of interest display.
     */
    private Map<String, PointOfInterestPanel> pointOfInterestsPanelMap;

    /**
     * Creates new form MainWindow.
     */
    public MainWindow() {
        controller = new ControllerMainWindow(this);
        buttonListenerMainWindow = new ButtonListenerMainWindow(controller, this);
        initComponents();
        planPanel = new PlanPanel(this);
        pointOfInterestsPanelMap = new HashMap<>();

        jPanel1.add(planPanel);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfPickupID = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfPickupTime = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfDeliveryID = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfDeliveryTime = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btAddRequest = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        infoLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        btImportMap = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btImportTour = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1395, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel6);

        jTabbedPane2.addTab("Summary", jScrollPane1);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1380, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(jPanel7);

        jPanel2.add(jScrollPane3);

        jPanel4.setLayout(new java.awt.GridLayout(5, 2));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Pickup ID");
        jPanel4.add(jLabel1);
        jPanel4.add(tfPickupID);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel9);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Pickup Duration");
        jPanel4.add(jLabel3);

        tfPickupTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPickupTimeActionPerformed(evt);
            }
        });
        jPanel4.add(tfPickupTime);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Delivery ID");
        jPanel4.add(jLabel2);

        tfDeliveryID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDeliveryIDActionPerformed(evt);
            }
        });
        jPanel4.add(tfDeliveryID);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel8);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Delivery Duration");
        jPanel4.add(jLabel4);

        tfDeliveryTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDeliveryTimeActionPerformed(evt);
            }
        });
        jPanel4.add(tfDeliveryTime);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel11);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel10);

        btAddRequest.setText("Add request");
        btAddRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddRequestActionPerformed(evt);
            }
        });
        jPanel4.add(btAddRequest);

        jPanel2.add(jPanel4);

        jTabbedPane2.addTab("Deliveries", null, jPanel2, "");

        jPanel1.add(jTabbedPane2);
        jTabbedPane2.getAccessibleContext().setAccessibleName("Deliveries");

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setMinimumSize(new java.awt.Dimension(0, 20));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(infoLabel);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jMenu3.setText("File");

        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Edit");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("Undo");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Redo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        jMenu1.setText("Map");

        btImportMap.setText("Import map");
        btImportMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(btImportMap);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Delivery");

        btImportTour.setText("Import tour");
        btImportTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(btImportTour);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * the Event listener, detect when a button is clicked and send it to the buttonListener Class.
     *
     * @param evt the event caught.
     * @see ButtonListenerMainWindow
     */
    private void jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActionPerformed
        buttonListenerMainWindow.actionPerformed(evt);

    }//GEN-LAST:event_jMenuItemActionPerformed

    private void tfDeliveryTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDeliveryTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDeliveryTimeActionPerformed

    private void tfPickupTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPickupTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPickupTimeActionPerformed

    private void tfDeliveryIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDeliveryIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDeliveryIDActionPerformed

    private void buttonAddRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddRequestActionPerformed
        // TODO add your handling code here:
        buttonListenerMainWindow.actionPerformed(evt);
    }//GEN-LAST:event_buttonAddRequestActionPerformed

    /**
     * Modify the access to the planData.
     * @param state the state of the planData.
     */
    public void setModifyPlanData(final boolean state) {
        btImportMap.setEnabled(state);
        btImportTour.setEnabled(state);
    }

    /**
     * set the info label.
     * @param text
     */
    public void setSystemInfoText(final String text) {
        infoLabel.setText(text);
    }

    /**
     * set the sate of the window.
     * @param currentState
     */
    public void setCurrentState(final State currentStateInit) {
        this.currentState = currentStateInit;
    }

    @Override
    public void update(final Observable o, final Object arg){
        if(o instanceof TSP || o instanceof DeliveryTour){
            DeliveryTour deliveryTour = (DeliveryTour) arg;
            PlanningRequest planningRequest = planPanel.getPlanData().getPlanningRequest();
            planPanel.getPlanData().setDeliveryTour(deliveryTour);
            State calculatingTimesState = new CalculatingTimesState();
            calculatingTimesState.execute(this);
            planningRequest.calculateTimes(deliveryTour);
            State readyState = new ReadyState();
            readyState.execute(this);
            planPanel.repaint();
            showDelivery(planningRequest);
            showSummary(planningRequest, deliveryTour);
        }
    }

    /**
     * update the plan.
     * @param planData the plan to update.
     */
    public void setPlanData(final Plan planData) {
        planPanel.setPlanData(planData);
    }

    /**
     * Display delivery information about the requests on the delivery panel
     * @param planningRequest contains the requests
     */
    public void showDelivery(final PlanningRequest planningRequest) {
        // Add information to jPanel
        JPanel container = jPanel7;
        container.removeAll();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        if (planningRequest != null) {
            String startTime = planningRequest.getDepartureTime();
            String finishTime = planningRequest.getFinishTime();

            JLabel startLabel = new JLabel("Start time: " + startTime);
            startLabel.setFont(new Font("Verdana",1,20));
            startLabel.setForeground(new Color(20,100,10));
            container.add(startLabel);
            // Adding space between components
            container.add(Box.createVerticalStrut(10));


            MouseListener ml = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    for (int j = 0; j<container.getComponentCount(); j++) {
                        Component c = container.getComponent(j);
                        if (c instanceof JPanel) {
                            for (int k=0; k<((JPanel) c).getComponentCount(); k++) {
                                Component child = ((JPanel) c).getComponent(k);
                                if (child.getName() != null && child.getName().startsWith("btnDelete")) {
                                    child.setVisible(false);
                                }
                            }
                            c.setBackground(container.getBackground());
                        }
                    }

                    JPanel clickedPanel = (JPanel) e.getSource();
                    clickedPanel.setBackground(Color.LIGHT_GRAY);

                    for (int j = 0; j < clickedPanel.getComponentCount(); j++) {
                        Component c = clickedPanel.getComponent(j);
                        if (c.getName() != null && c.getName().startsWith("btnDelete")) {
                            c.setVisible(true);
                        }
                        if (c instanceof JPanel) {
                            c.setBackground(Color.LIGHT_GRAY);
                        }
                    }
                }
            };




        // For each request we get the time of passage of the pickup and the delivery
            int i = 1;
            for (Request request : planningRequest.getRequests()) {
                String pickUpTimePassage = request.getPickupTimePassage();
                String deliveryTimePassage = request.getDeliveryTimePassage();



                JPanel linePanel = new JPanel();
                linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.Y_AXIS));

                JLabel requestLabel = new JLabel("Request number " + i + ":");
                requestLabel.setFont(new Font("Verdana",1,16));
                requestLabel.setForeground(request.getColor());
                linePanel.add(requestLabel);
                linePanel.add(Box.createVerticalStrut(10));

                JLabel timeLabel = new JLabel("PickupTime: " + pickUpTimePassage + "\t DeliveryTime: "+deliveryTimePassage);
                timeLabel.setFont(new Font("Verdana",1,12));
                linePanel.add(timeLabel);
                linePanel.add(Box.createVerticalStrut(5));

                JButton deleteButton = new JButton("Remove request");
                deleteButton.setName("btnDelete" + i);
                deleteButton.setVisible(false);
                linePanel.add(deleteButton);
                deleteButton.addActionListener(new DeleteButtonListener(controller, request, false));
                linePanel.add(Box.createVerticalStrut(5));

                JButton deleteAndChangeTourButton = new JButton("Remove request and change the tour");
                linePanel.add(deleteAndChangeTourButton);
                deleteAndChangeTourButton.setName("btnDeleteTour" + i);
                deleteAndChangeTourButton.setVisible(false);
                deleteAndChangeTourButton.addActionListener(new DeleteButtonListener(controller, request, true));
                linePanel.add(Box.createVerticalStrut(20));

                linePanel.addMouseListener(ml);
                container.add(linePanel);

                i++;
            }
            container.add(Box.createVerticalStrut(10));

            JLabel finishLabel = new JLabel("Finish time: " + finishTime);
            finishLabel.setFont(new Font("Verdana",1,20));
            finishLabel.setForeground(Color.BLUE);
            container.add(finishLabel);
        }
            container.revalidate();
            container.repaint();
    }

    /**
     * Display summary information about each point of interest
     * @param planningRequest contains the requests
     * @param deliveryTour contains the points of interests
     */
    public void showSummary(final PlanningRequest planningRequest, final DeliveryTour deliveryTour) {
        // Add information to jPanel
        JPanel container = jPanel6;
        container.removeAll();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        if (planningRequest != null && deliveryTour != null) {
            String startTime = planningRequest.getDepartureTime();
            String finishTime = planningRequest.getFinishTime();

            JLabel startLabel = new JLabel("Start time: " + startTime);
            startLabel.setFont(new Font("Verdana",1,20));
            startLabel.setForeground(new Color(20,100,10));
            container.add(startLabel);
            // Adding space between components
            container.add(Box.createVerticalStrut(10));

            List<Request> requests = planningRequest.getRequests();
            int i =0;

            MouseListener ml = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    PointOfInterestPanel poiPanel = (PointOfInterestPanel) e.getSource();
                    String id = poiPanel.isPickUp() ? poiPanel.getRequest().getPickupId() : poiPanel.getRequest().getDeliveryId();
                    Intersection intersection  = controller.getIntersectionFromId(id);
                    if (highlighted != null) {
                        highlighted.setBackground(jPanel6.getBackground());
                    }
                    poiPanel.setBackground(Color.LIGHT_GRAY);
                    highlighted = poiPanel;
                    planPanel.setSelectedPOI(intersection);

                }
            };
            for (String pointOfInterest : deliveryTour.getPointsOfInterest()) {
                for (Request request: requests) {
                    if (request.getPickupId().equals(pointOfInterest)) {
                        PointOfInterestPanel poiPanel = new PointOfInterestPanel(true, request.getPickupTimePassage(),request,i);
                        poiPanel.addMouseListener(ml);
                        container.add(poiPanel);
                        break;
                    }
                    if (request.getDeliveryId().equals(pointOfInterest)) {
                        PointOfInterestPanel poiPanel = new PointOfInterestPanel(false, request.getDeliveryTimePassage(),request,i);
                        poiPanel.addMouseListener(ml);
                        container.add(poiPanel);
                        break;
                    }
                }
                i++;
            }
            container.add(Box.createVerticalStrut(10));

            JLabel finishLabel = new JLabel("Finish time: " + finishTime);
            finishLabel.setFont(new Font("Verdana",1,20));
            finishLabel.setForeground(Color.BLUE);
            container.add(finishLabel);
        }
        container.revalidate();
        container.repaint();

    }

    /**
     * Method called to highlight a point on interest, called when we click on a point on the map
     * @param pointOfInterestId the id of the pointOfInterest to highlight
     */
    public void setHighlighted(final String pointOfInterestId) {
        PointOfInterestPanel toHighlight = pointOfInterestsPanelMap.get(pointOfInterestId);
        if (highlighted != null) {
            highlighted.setBackground(jPanel6.getBackground());
        }
        highlighted = toHighlight;
        highlighted.setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Clear the summary and delivery panel
     */
    public void clearPanels() {
        jPanel6.removeAll();
        jPanel7.removeAll();
        jPanel6.revalidate();
        jPanel6.repaint();
        jPanel7.revalidate();
        jPanel7.repaint();
    }






    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FlatLightLaf.setup();
                new MainWindow().setVisible(true);
                //System.out.println("Hello world");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btImportMap;
    private javax.swing.JMenuItem btImportTour;
    private javax.swing.JButton btAddRequest;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;

    public JTextField getTfDeliveryID() {
        return tfDeliveryID;
    }

    public JTextField getTfDeliveryTime() {
        return tfDeliveryTime;
    }

    public JTextField getTfPickupID() {
        return tfPickupID;
    }

    public JTextField getTfPickupTime() {
        return tfPickupTime;
    }

    private javax.swing.JTextField tfDeliveryID;
    private javax.swing.JTextField tfDeliveryTime;
    private javax.swing.JTextField tfPickupID;
    private javax.swing.JTextField tfPickupTime;
    // End of variables declaration//GEN-END:variables

    private ButtonListenerMainWindow buttonListenerMainWindow;
}
