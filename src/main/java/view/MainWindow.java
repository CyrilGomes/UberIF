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
import java.util.List;

/**
 * The main class, displaying the HMI and starting the application.
 *
 * @author Thibaud Martin
 * @author Aurelia Inard
 */
public class MainWindow extends javax.swing.JFrame implements Observer {
    private final PlanPanel planPanel;
    private final ControllerMainWindow controller;
    private State currentState;
    // The currently highlighted on the summary panel
    private PointOfInterestPanel highlighted;

    /**
     * Creates new form MainWindow.
     */
    public MainWindow() {
        controller = new ControllerMainWindow(this);
        buttonListenerMainWindow = new ButtonListenerMainWindow(controller, this);
        initComponents();
        planPanel = new PlanPanel(this);

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
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        tPickupId = new javax.swing.JTextField();
        tPickupTime = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tPickupId1 = new javax.swing.JTextField();
        tPickupTime1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
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
        btAddDelivery = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1102, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel6);

        jTabbedPane2.addTab("Summary", jScrollPane1);

        jLabel1.setText("add a request");

        jButton1.setText("add request");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tPickupId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPickupIdActionPerformed(evt);
            }
        });

        tPickupTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPickupTimeActionPerformed(evt);
            }
        });

        jLabel2.setText("pickup ID");

        jLabel3.setText("pickup duration");

        jLabel4.setText("delivery duration");

        jLabel5.setText("delivery ID");

        tPickupId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPickupId1ActionPerformed(evt);
            }
        });

        tPickupTime1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPickupTime1ActionPerformed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tPickupTime, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(tPickupId))
                .addGap(62, 62, 62)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tPickupTime1)
                    .addComponent(tPickupId1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(251, 251, 251)
                        .addComponent(jButton1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tPickupId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tPickupTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(tPickupId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tPickupTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

        btAddDelivery.setText("Ajouter livraison");
        btAddDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(btAddDelivery);

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
        System.out.println(evt.getActionCommand());
        buttonListenerMainWindow.actionPerformed(evt);

    }//GEN-LAST:event_jMenuItemActionPerformed

    private void tPickupTime1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPickupTime1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPickupTime1ActionPerformed

    private void tPickupId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPickupId1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPickupId1ActionPerformed

    private void tPickupTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPickupTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPickupTimeActionPerformed

    private void tPickupIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPickupIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tPickupIdActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setModifyPlanData(final boolean state) {
        btAddDelivery.setEnabled(state);
        btImportMap.setEnabled(state);
        btImportTour.setEnabled(state);
    }

    public void setSystemInfoText(final String text) {
        infoLabel.setText(text);
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public void update(Observable o, Object arg){
        if(o instanceof TSP){
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
            showSummary(planningRequest,deliveryTour);
        }
    }

    /**
     * update the plan.
     * @param planData the plan to update.
     */
    public void setPlanData(Plan planData) {
        planPanel.setPlanData(planData);
    }

    public Plan getPlanData(Plan planData){
        return planPanel.getPlanData();
    }

    /**
     * Display summary information about the requests
     * @param planningRequest
     */
    public void showDelivery(PlanningRequest planningRequest){
        // Add information to jPanel
        JPanel container = jPanel7;
        container.removeAll();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        if(planningRequest != null){
            String startTime = planningRequest.getDepartureTime();
            String finishTime = planningRequest.getFinishTime();

            JLabel startLabel = new JLabel("Start time: "+startTime);
            startLabel.setFont(new Font("Verdana",1,20));
            startLabel.setForeground(new Color(20,100,10));
            container.add(startLabel);
            // Adding space between components
            container.add(Box.createVerticalStrut(10));


            // For each request we get the time of passage of the pickup and the delivery
            int i = 1 ;
            for(Request request : planningRequest.getRequests()){
                String pickUpTimePassage = request.getPickupTimePassage();
                String deliveryTimePassage = request.getDeliveryTimePassage();

                JLabel requestLabel = new JLabel("Request number "+i+":");
                requestLabel.setFont(new Font("Verdana",1,16));
                requestLabel.setForeground(request.getColor());
                container.add(requestLabel);
                container.add(Box.createVerticalStrut(10));



                JLabel timeLabel = new JLabel("PickupTime: "+pickUpTimePassage+"\t DeliveryTime: "+deliveryTimePassage);
                timeLabel.setFont(new Font("Verdana",1,12));
                container.add(timeLabel);
                container.add(Box.createVerticalStrut(5));

                JButton deleteButton = new JButton("Remove request");
                container.add(deleteButton);
                deleteButton.addActionListener(new DeleteButtonListener(controller,request,false));
                container.add(Box.createVerticalStrut(5));

                JButton deleteAndChangeTourButton = new JButton("Remove request and change the tour");
                container.add(deleteAndChangeTourButton);
                deleteAndChangeTourButton.addActionListener(new DeleteButtonListener(controller,request,true));
                container.add(Box.createVerticalStrut(20));

                i++;
            }
            container.add(Box.createVerticalStrut(10));

            JLabel finishLabel = new JLabel("Finish time: "+finishTime);
            finishLabel.setFont(new Font("Verdana",1,20));
            finishLabel.setForeground(Color.BLUE);
            container.add(finishLabel);
        }
            container.revalidate();
            container.repaint();
    }

    public void showSummary(PlanningRequest planningRequest, DeliveryTour deliveryTour){
        // Add information to jPanel
        JPanel container = jPanel6;
        container.removeAll();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        if(planningRequest != null && deliveryTour != null){
            String startTime = planningRequest.getDepartureTime();
            String finishTime = planningRequest.getFinishTime();

            JLabel startLabel = new JLabel("Start time: "+startTime);
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
                    if(highlighted !=null){
                        highlighted.setBackground(jPanel6.getBackground());
                    }
                    poiPanel.setBackground(Color.LIGHT_GRAY);
                    highlighted = poiPanel;
                    planPanel.setSelectedPOI(intersection);

                }
            };
            for(String pointOfInterest : deliveryTour.getPointsOfInterest()){
                for(Request request: requests){
                    if(request.getPickupId().equals(pointOfInterest)){
                        PointOfInterestPanel poiPanel = new PointOfInterestPanel(true,request.getPickupTimePassage(),request,i);
                        poiPanel.addMouseListener(ml);
                        container.add(poiPanel);
                        break;
                    }
                    if(request.getDeliveryId().equals(pointOfInterest)){
                        PointOfInterestPanel poiPanel = new PointOfInterestPanel(false,request.getDeliveryTimePassage(),request,i);
                        poiPanel.addMouseListener(ml);
                        container.add(poiPanel);
                        break;
                    }
                }
                i++;
            }
            container.add(Box.createVerticalStrut(10));

            JLabel finishLabel = new JLabel("Finish time: "+finishTime);
            finishLabel.setFont(new Font("Verdana",1,20));
            finishLabel.setForeground(Color.BLUE);
            container.add(finishLabel);
        }
        container.revalidate();
        container.repaint();

    }

    /**
     * Clear the summary panel
     */
    public void clearPanels(){
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
                System.out.println("Hello world");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btAddDelivery;
    private javax.swing.JMenuItem btImportMap;
    private javax.swing.JMenuItem btImportTour;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField tPickupId;
    private javax.swing.JTextField tPickupId1;
    private javax.swing.JTextField tPickupTime;
    private javax.swing.JTextField tPickupTime1;
    // End of variables declaration//GEN-END:variables

    private ButtonListenerMainWindow buttonListenerMainWindow;
}
