package reports.v;

import com.itextpdf.text.DocumentException;
import core.c.ErrorHandler;
import core.c.ViewManager;
import core.m.ApplicationException;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author MRKACZOR
 */
public class ReportsArticlesCountWindow extends ApplicationDialog
{
  /**
   * Creates new form ReportsOutcomeIncomeWindow
   */
  public ReportsArticlesCountWindow()
  {
    super(true, null);
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

        bGenerate = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        lTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jdDate = new com.toedter.calendar.JDateChooser(Calendar.getInstance().getTime());
        jLabel1 = new javax.swing.JLabel();
        cbNonZeroStatesOnly = new javax.swing.JCheckBox();
        cbGroupsSeparation = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        cbGroupsSeparateNumeration = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bGenerate.setText("Generuj raport");
        bGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGenerateActionPerformed(evt);
            }
        });

        bCancel.setText("Anuluj");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        lTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTitle.setText("RAPORT STANÓW MAGAZYNOWYCH");
        lTitle.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jdDate.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Stan magazynu na dzień:");

        cbNonZeroStatesOnly.setText("Uwzględnij tylko stany niezerowe");

        cbGroupsSeparation.setText("Uwzględnij podział na grupy towarowe");
        cbGroupsSeparation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGroupsSeparationActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Kategoria towarów:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Części", "Opony", "Wszystkie" }));
        jComboBox1.setSelectedItem("Opony");
        jComboBox1.setEnabled(false);

        cbGroupsSeparateNumeration.setText("Indywidualna numeracja wewnątrz każdej grupy towarowej");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bGenerate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bCancel))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdDate, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbNonZeroStatesOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(cbGroupsSeparateNumeration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cbGroupsSeparation, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jdDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cbNonZeroStatesOnly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGroupsSeparation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGroupsSeparateNumeration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancel)
                    .addComponent(bGenerate))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void bGenerateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bGenerateActionPerformed
  {//GEN-HEADEREND:event_bGenerateActionPerformed
    Date forDate=jdDate.getDate();
    try {
      if(!forDate.after(Calendar.getInstance().getTime())) {
        String pdfFile=ReportsArticlesCountView.getInstance().getTiresReport(forDate, cbNonZeroStatesOnly.isSelected(), cbGroupsSeparation.isSelected(), cbGroupsSeparateNumeration.isSelected());
        ViewManager.getInstance().openPDF(pdfFile);
      } else {
        throw new ApplicationException("Data raportu nie może być późniejsza od daty dzisiejszej!");
      }
    } catch(ApplicationException|DatabaseException|DocumentException|InterruptedException|IOException|ParseException|SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
  }//GEN-LAST:event_bGenerateActionPerformed

  private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
  {//GEN-HEADEREND:event_bCancelActionPerformed
    close(false);
  }//GEN-LAST:event_bCancelActionPerformed

  private void cbGroupsSeparationActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbGroupsSeparationActionPerformed
  {//GEN-HEADEREND:event_cbGroupsSeparationActionPerformed
    cbGroupsSeparateNumeration.setEnabled(cbGroupsSeparation.isSelected());
  }//GEN-LAST:event_cbGroupsSeparationActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bGenerate;
    private javax.swing.JCheckBox cbGroupsSeparateNumeration;
    private javax.swing.JCheckBox cbGroupsSeparation;
    private javax.swing.JCheckBox cbNonZeroStatesOnly;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdDate;
    private javax.swing.JLabel lTitle;
    // End of variables declaration//GEN-END:variables
}
