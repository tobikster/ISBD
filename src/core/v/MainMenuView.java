/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.v;

import articles.v.ArticleListView;
import core.c.ViewManager;
import javax.swing.JOptionPane;

/**
 *
 * @author MRKACZOR
 */
public class MainMenuView extends javax.swing.JPanel
{
  /**
   * Creates new form MainMenuView
   */
  public MainMenuView()
  {
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

        bContractors = new javax.swing.JButton();
        bSale = new javax.swing.JButton();
        bDelivery = new javax.swing.JButton();
        bWarehouse = new javax.swing.JButton();
        bDocuments = new javax.swing.JButton();
        bSettings = new javax.swing.JButton();
        lTitle = new javax.swing.JLabel();

        bContractors.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128x128/Group-128.png"))); // NOI18N
        bContractors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bContractorsActionPerformed(evt);
            }
        });

        bSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128x128/Sale-128.png"))); // NOI18N
        bSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaleActionPerformed(evt);
            }
        });

        bDelivery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128x128/Caargo-128.png"))); // NOI18N
        bDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeliveryActionPerformed(evt);
            }
        });

        bWarehouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128x128/Stacked-Boxes-128.png"))); // NOI18N
        bWarehouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWarehouseActionPerformed(evt);
            }
        });

        bDocuments.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128x128/Documents-128.png"))); // NOI18N
        bDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDocumentsActionPerformed(evt);
            }
        });

        bSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128x128/Settings_128.png"))); // NOI18N
        bSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSettingsActionPerformed(evt);
            }
        });

        lTitle.setFont(new java.awt.Font("Calibri", 0, 80)); // NOI18N
        lTitle.setForeground(new java.awt.Color(153, 153, 153));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("MECHANIKER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bContractors)
                                .addComponent(bWarehouse))
                            .addGap(77, 77, 77)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bSale)
                                .addComponent(bDocuments))
                            .addGap(78, 78, 78)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bSettings)
                                .addComponent(bDelivery))))
                    .addContainerGap(81, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bDelivery)
                        .addComponent(bSale)
                        .addComponent(bContractors))
                    .addGap(60, 60, 60)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bWarehouse)
                        .addComponent(bDocuments)
                        .addComponent(bSettings))
                    .addGap(58, 58, 58)))
        );
    }// </editor-fold>//GEN-END:initComponents

  private void bContractorsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bContractorsActionPerformed
  {//GEN-HEADEREND:event_bContractorsActionPerformed
    JOptionPane.showMessageDialog(this, "Wybrana funkcjonalność nie jest dostępna w tej wersji aplikacji.");
  }//GEN-LAST:event_bContractorsActionPerformed

  private void bSaleActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSaleActionPerformed
  {//GEN-HEADEREND:event_bSaleActionPerformed
    JOptionPane.showMessageDialog(this, "Wybrana funkcjonalność nie jest dostępna w tej wersji aplikacji.");
  }//GEN-LAST:event_bSaleActionPerformed

  private void bDeliveryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bDeliveryActionPerformed
  {//GEN-HEADEREND:event_bDeliveryActionPerformed
    JOptionPane.showMessageDialog(this, "Wybrana funkcjonalność nie jest dostępna w tej wersji aplikacji.");
  }//GEN-LAST:event_bDeliveryActionPerformed

  private void bWarehouseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bWarehouseActionPerformed
  {//GEN-HEADEREND:event_bWarehouseActionPerformed
    ViewManager.getInstance().openView(new ArticleListView());
  }//GEN-LAST:event_bWarehouseActionPerformed

  private void bDocumentsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bDocumentsActionPerformed
  {//GEN-HEADEREND:event_bDocumentsActionPerformed
    JOptionPane.showMessageDialog(this, "Wybrana funkcjonalność nie jest dostępna w tej wersji aplikacji.");
  }//GEN-LAST:event_bDocumentsActionPerformed

  private void bSettingsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSettingsActionPerformed
  {//GEN-HEADEREND:event_bSettingsActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_bSettingsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bContractors;
    private javax.swing.JButton bDelivery;
    private javax.swing.JButton bDocuments;
    private javax.swing.JButton bSale;
    private javax.swing.JButton bSettings;
    private javax.swing.JButton bWarehouse;
    private javax.swing.JLabel lTitle;
    // End of variables declaration//GEN-END:variables
}
