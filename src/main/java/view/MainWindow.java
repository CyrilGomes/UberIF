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

/**
 * The main class, displaying the HMI and starting the application.
 *
 * @author Thibaud Martin
 * @author Aurelia Inard
 */
public class MainWindow extends javax.swing.JFrame implements Observer {
    private final PlanPanel planPanel;
    private final ControllerMainWindow controller;

    /**
     * Creates new form MainWindow.
     */
    public MainWindow() {
        controller = new ControllerMainWindow(this);
        buttonListenerMainWindow = new ButtonListenerMainWindow(controller, this);
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
        setPreferredSize(new java.awt.Dimension(720, 576));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
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



    @Override
    public void update(Observable o, Object arg){
        if(o instanceof TSP){
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
    private javax.swing.JLabel infoLabel;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables

    private ButtonListenerMainWindow buttonListenerMainWindow;
}
