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
public class SettingsPanel extends javax.swing.JPanel {
 private final ApplicationView JFRAME;
    /**
     * Creates new form SettingsPanel
     */
    public SettingsPanel(ApplicationView jFrame) {
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

        serverIpInput = new javax.swing.JTextField();
        ServerPortInput = new javax.swing.JSpinner();
        serverPortLabel = new javax.swing.JLabel();
        serverIpLabel = new javax.swing.JLabel();
        defaultPortServer = new javax.swing.JLabel();
        defaultIpServer = new javax.swing.JLabel();
        defaultLabel = new javax.swing.JLabel();
        StartServerButton = new javax.swing.JButton();
        SaveSettingsServer = new javax.swing.JButton();
        goHomeButton = new javax.swing.JButton();

        serverIpInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        serverIpInput.setText("127.0.0.1");
        serverIpInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverIpInputActionPerformed(evt);
            }
        });

        ServerPortInput.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ServerPortInput.setRequestFocusEnabled(false);

        serverPortLabel.setText("Server port:");

        serverIpLabel.setText("Server IP:");

        defaultPortServer.setText("9090");

        defaultIpServer.setText("127.0.0.1");

        defaultLabel.setText("Default:");

        StartServerButton.setText("Start Server");
        StartServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartServerButtonActionPerformed(evt);
            }
        });

        SaveSettingsServer.setText("Save Settings");
        SaveSettingsServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveSettingsServerActionPerformed(evt);
            }
        });

        goHomeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/home.png"))); // NOI18N
        goHomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goHomeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(goHomeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SaveSettingsServer)
                        .addGap(34, 34, 34)
                        .addComponent(StartServerButton)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(serverPortLabel)
                            .addComponent(serverIpLabel))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(ServerPortInput, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(defaultPortServer)
                                .addGap(42, 42, 42))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(serverIpInput, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(defaultLabel)
                                    .addComponent(defaultIpServer))
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(goHomeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(defaultLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(serverIpLabel)
                            .addComponent(serverIpInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(defaultIpServer))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(serverPortLabel)
                            .addComponent(ServerPortInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(defaultPortServer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SaveSettingsServer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StartServerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void serverIpInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverIpInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverIpInputActionPerformed

    private void StartServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartServerButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StartServerButtonActionPerformed

    private void SaveSettingsServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveSettingsServerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SaveSettingsServerActionPerformed

    private void goHomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goHomeButtonActionPerformed
        this.JFRAME.changePanel(new HomePanel(this.JFRAME));
    }//GEN-LAST:event_goHomeButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SaveSettingsServer;
    private javax.swing.JSpinner ServerPortInput;
    private javax.swing.JButton StartServerButton;
    private javax.swing.JLabel defaultIpServer;
    private javax.swing.JLabel defaultLabel;
    private javax.swing.JLabel defaultPortServer;
    private javax.swing.JButton goHomeButton;
    private javax.swing.JTextField serverIpInput;
    private javax.swing.JLabel serverIpLabel;
    private javax.swing.JLabel serverPortLabel;
    // End of variables declaration//GEN-END:variables
}
