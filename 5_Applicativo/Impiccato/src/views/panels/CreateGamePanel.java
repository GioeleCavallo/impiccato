/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.panels;


import views.ApplicationView;

/**
 *
 * @author gioele.cavallo
 */
public class CreateGamePanel extends javax.swing.JPanel {

    private final ApplicationView JFRAME;

    /**
     * Creates new form CreateGamePanel
     */
    public CreateGamePanel(ApplicationView jFrame) {
        this.JFRAME = jFrame;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundsCountSpinner = new javax.swing.JSpinner();
        timeDurationSpinner = new javax.swing.JSpinner();
        timeDurationLabel = new javax.swing.JLabel();
        CreateButton2 = new javax.swing.JButton();
        GoBackButton = new javax.swing.JButton();
        roundsCountLabel1 = new javax.swing.JLabel();

        roundsCountSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        roundsCountSpinner.setName(""); // NOI18N

        timeDurationSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        timeDurationLabel.setText("Time (sec. >= 30)");

        CreateButton2.setText("Create");
        CreateButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButton2ActionPerformed(evt);
            }
        });

        GoBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back_arrow.png"))); // NOI18N
        GoBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoBackButtonActionPerformed(evt);
            }
        });

        roundsCountLabel1.setText("Rounds (>0)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(GoBackButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(153, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(timeDurationLabel)
                            .addComponent(roundsCountLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roundsCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeDurationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CreateButton2)
                        .addGap(161, 161, 161))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeDurationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeDurationLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundsCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundsCountLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(CreateButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(GoBackButton)
                .addGap(17, 17, 17))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CreateButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButton2ActionPerformed
        int rounds = (Integer)roundsCountSpinner.getValue();
        int time = (Integer)timeDurationSpinner.getValue();
        boolean right = true;
        if(rounds <= 0){
            roundsCountSpinner.setValue(0);
            right = false;
        }
        if(time < 30){
            timeDurationSpinner.setValue(30);
            right = false;
        }
        
        if(right){ 
            this.JFRAME.client.sendPacket("create game");
            
            this.JFRAME.client.sendPacket("set time "+time);
            this.JFRAME.client.sendPacket("set rounds "+time);
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {}
            this.JFRAME.client.sendPacket("start game");
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {}
            this.JFRAME.changePanel(new GamePanel(this.JFRAME));
            
        }
        
        
        
        
        
    }//GEN-LAST:event_CreateButton2ActionPerformed

    private void GoBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoBackButtonActionPerformed
        this.JFRAME.changePanel(new HomePanel(this.JFRAME));
    }//GEN-LAST:event_GoBackButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreateButton2;
    private javax.swing.JButton GoBackButton;
    private javax.swing.JLabel roundsCountLabel1;
    private javax.swing.JSpinner roundsCountSpinner;
    private javax.swing.JLabel timeDurationLabel;
    private javax.swing.JSpinner timeDurationSpinner;
    // End of variables declaration//GEN-END:variables
}