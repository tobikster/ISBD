/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workers.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import workers.c.WorkersService;
import workers.m.Worker;

/**
 *
 * @author tobikster
 */
public class RemoveWorkerConfirmDialog extends ApplicationDialog
{
  /**
   * Creates new form RemoveWorkerConfirmDialog
   */
  public RemoveWorkerConfirmDialog(boolean modal, Reloadable reloadableParent, Worker worker)
  {
    super(modal, reloadableParent);
    _worker=worker;
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _cancelButton = new javax.swing.JButton();
        _okButton = new javax.swing.JButton();
        _titleLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _cancelButton.setText("Anuluj");
        _cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _cancelButtonActionPerformed(evt);
            }
        });

        _okButton.setText("OK");
        _okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _okButtonActionPerformed(evt);
            }
        });

        _titleLabel.setText("Usunięcie pracownika");

        jLabel1.setText("Czy na pewno chcesz usunąć pracownika " + _worker.getFullName() + "?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(_titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 274, Short.MAX_VALUE)
                        .addComponent(_okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_cancelButton)
                    .addComponent(_okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__okButtonActionPerformed
      try
      {
        WorkersService.getInstance().removeWorker(_worker);
        super.close(true);
      }
      catch(SQLException ex)
      {
        ErrorHandler.getInstance().reportError(ex);
      }
    }//GEN-LAST:event__okButtonActionPerformed

    private void _cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__cancelButtonActionPerformed
      super.close(false);
    }//GEN-LAST:event__cancelButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _cancelButton;
    private javax.swing.JButton _okButton;
    private javax.swing.JLabel _titleLabel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
  private Worker _worker;
}
