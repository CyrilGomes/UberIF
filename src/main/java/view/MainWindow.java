/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.ControllerMainWindow;
import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.graphs.Plan;
import model.graphs.pathfinding.TSP;
import observer.Observable;
import observer.Observer;
import view.plan.PlanPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The main class, displaying the HMI and starting the application.
 *
 * @author Thibaud Martin
 * @author Aurelia Inard
 */
public class MainWindow extends javax.swing.JFrame implements Observer, KeyListener {
    private final PlanPanel planPanel;
    private final ControllerMainWindow controller;

    /**
     * Creates new form MainWindow.
     */
    public MainWindow() {
        controller = new ControllerMainWindow(this);
        buttonListenerMainWindow = new ButtonListenerMainWindow(controller, this);
        addKeyListener(this);
        initComponents();
        planPanel = new PlanPanel(infoLabel);

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
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panelAddRequest = new javax.swing.JPanel();
        btAddRequest = new javax.swing.JButton();
        tfDeliveryID = new javax.swing.JTextField();
        tfDeliveryTime = new javax.swing.JTextField();
        tfPickupID = new javax.swing.JTextField();
        tfPickupTime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        infoLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Summary", jPanel6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btAddRequest.setText("Add request");
        btAddRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddRequestActionPerformed(evt);
            }
        });

        tfPickupTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPickupTimeActionPerformed(evt);
            }
        });

        jLabel1.setText("Pickup intersection ID");

        jLabel2.setText("Delivery time");

        jLabel3.setText("Pickup time");

        jLabel4.setText("Delivery intersection ID");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel5.setText("Add a new request");

        javax.swing.GroupLayout panelAddRequestLayout = new javax.swing.GroupLayout(panelAddRequest);
        panelAddRequest.setLayout(panelAddRequestLayout);
        panelAddRequestLayout.setHorizontalGroup(
            panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddRequestLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAddRequest)
                .addGap(270, 270, 270))
            .addGroup(panelAddRequestLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddRequestLayout.createSequentialGroup()
                        .addComponent(tfPickupTime, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(panelAddRequestLayout.createSequentialGroup()
                        .addComponent(tfPickupID, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addGap(12, 12, 12)
                .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfDeliveryTime, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDeliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
            .addGroup(panelAddRequestLayout.createSequentialGroup()
                .addGap(238, 238, 238)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAddRequestLayout.setVerticalGroup(
            panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAddRequestLayout.createSequentialGroup()
                        .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfDeliveryID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfDeliveryTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(panelAddRequestLayout.createSequentialGroup()
                        .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfPickupID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAddRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfPickupTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGap(18, 18, 18)
                .addComponent(btAddRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAddRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAddRequest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane2.addTab("Deliveries", jPanel2);

        jPanel1.add(jTabbedPane2);

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

        jMenu1.setText("Map");

        jMenuItem3.setText("Import map");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Delivery");

        jMenuItem1.setText("Import tour");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Ajouter livraison");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

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
        // TODO add your handling code here:
        System.out.println(evt.getActionCommand());
        buttonListenerMainWindow.actionPerformed(evt);

    }//GEN-LAST:event_jMenuItemActionPerformed

    private void btAddRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddRequestActionPerformed
        // TODO add your handling code here:
        System.out.println(evt.getActionCommand());
        buttonListenerMainWindow.actionPerformed(evt);
        
    }//GEN-LAST:event_btAddRequestActionPerformed

    private void tfPickupTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPickupTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPickupTimeActionPerformed



    @Override
    public void update(Observable o, Object arg){
        if(o instanceof TSP || o instanceof ControllerMainWindow){
            DeliveryTour deliveryTour = (DeliveryTour) arg;
            PlanningRequest planningRequest = planPanel.getPlanData().getPlanningRequest();
            planPanel.getPlanData().setDeliveryTour(deliveryTour);
            planningRequest.calculateTimes(deliveryTour);
            planPanel.repaint();
            showSummary(planningRequest);
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
    public void showSummary(PlanningRequest planningRequest){
            String startTime = planningRequest.getDepartureTime();
            String finishTime = planningRequest.getFinishTime();

            // Add information to jPanel
            JPanel container = jPanel6;
            container.removeAll();
            container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
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
                container.add(requestLabel);

                JButton deleteButton = new JButton("Remove request");
                container.add(deleteButton);
                deleteButton.addActionListener(new DeleteButtonListener(controller,request,false));

                JButton deleteAndChangeTourButton = new JButton("Remove request and change the tour");
                container.add(deleteAndChangeTourButton);
                deleteAndChangeTourButton.addActionListener(new DeleteButtonListener(controller,request,true));

                JLabel timeLabel = new JLabel("PickupTime: "+pickUpTimePassage+"\t DeliveryTime: "+deliveryTimePassage);
                timeLabel.setFont(new Font("Verdana",1,12));
                container.add(timeLabel);
                container.add(Box.createVerticalStrut(5));
                i++;
            }

            container.add(Box.createVerticalStrut(10));

            JLabel finishLabel = new JLabel("Finish time: "+finishTime);
            finishLabel.setFont(new Font("Verdana",1,20));
            finishLabel.setForeground(Color.BLUE);
            container.add(finishLabel);

            container.revalidate();
            container.repaint();
    }

    /**
     * Clear the summary panel
     */
    public void clearPanels(){
        jPanel6.removeAll();
        jPanel6.revalidate();
        jPanel6.repaint();
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
    private javax.swing.JButton btAddRequest;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel panelAddRequest;
    private javax.swing.JTextField tfDeliveryID;
    private javax.swing.JTextField tfDeliveryTime;
    private javax.swing.JTextField tfPickupID;

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

    private javax.swing.JTextField tfPickupTime;
    // End of variables declaration//GEN-END:variables

    private ButtonListenerMainWindow buttonListenerMainWindow;
    private KeyboardListenerMainWindow keyboardListenerMainWindow;

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("bouh");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("bouh");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("bouh");
    }
}
