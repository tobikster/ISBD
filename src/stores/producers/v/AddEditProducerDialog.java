package stores.producers.v;

import core.c.ErrorHandler;
import core.c.Feedbackable;
import core.c.Reloadable;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;

/**
 *
 * @author MRKACZOR
 */
public class AddEditProducerDialog extends ApplicationDialog
{
  private Producer _producer;
  private Feedbackable _parent;
  private final boolean _editMode;

  public AddEditProducerDialog(boolean modal, Reloadable parent)
  {
    super(modal, parent);
    if(parent instanceof Feedbackable)
      _parent = (Feedbackable)parent;
    _editMode = false;
    _producer = new Producer();
    initComponents();
  }

  public AddEditProducerDialog(boolean modal, Reloadable parent, Producer producer)
  {
    super(modal, parent);
    if(parent instanceof Feedbackable)
      _parent = (Feedbackable)parent;
    _editMode = true;
    _producer = producer;
    initComponents();
	initTextFields();
  }
  
  private void initTextFields() {
	  initNameTextField();
  }
  
  private void initNameTextField() {
	  if(_producer != null) {
		  _nameTextField.setText(_producer.getName());
	  }
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        bSubmit = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        _nameTextField = new javax.swing.JTextField();
        _nameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText(_editMode?"Edycja producenta":"Nowy producent");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        bSubmit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bSubmit.setText("Zapisz");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        bCancel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bCancel.setText("Anuluj");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        _nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _nameTextFieldFocusLost(evt);
            }
        });

        _nameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _nameLabel.setText("Nazwa:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(_nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 68, Short.MAX_VALUE)
                        .addComponent(bSubmit)
                        .addGap(20, 20, 20)
                        .addComponent(bCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSubmit)
                    .addComponent(bCancel))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_nameLabel, _nameTextField});

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void bSubmitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSubmitActionPerformed
  {//GEN-HEADEREND:event_bSubmitActionPerformed
    try
    {
      if(_editMode)
        ProducersService.getInstance().updateProducer(_producer);
      else
        _producer = ProducersService.getInstance().addProducer(_producer);
      if(_parent!=null)
        _parent.sendFeedback(_producer);
      close(true);
    }
    catch(DatabaseException|SQLException ex)
    {
      ErrorHandler.getInstance().reportError(ex);
    }
  }//GEN-LAST:event_bSubmitActionPerformed

  private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
  {//GEN-HEADEREND:event_bCancelActionPerformed
    close(false);
  }//GEN-LAST:event_bCancelActionPerformed

  private void _nameTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__nameTextFieldFocusLost
  {//GEN-HEADEREND:event__nameTextFieldFocusLost
    _producer.setName(_nameTextField.getText());
  }//GEN-LAST:event__nameTextFieldFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
