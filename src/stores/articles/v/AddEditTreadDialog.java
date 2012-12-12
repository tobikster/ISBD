/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.articles.v;

import core.c.ErrorHandler;
import core.c.Feedbackable;
import core.c.Reloadable;
import core.c.ViewManager;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import stores.articles.c.TiresService;
import stores.articles.m.Tread;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;
import stores.producers.v.AddEditProducerDialog;

/**
 *
 * @author MRKACZOR
 */
public class AddEditTreadDialog extends ApplicationDialog implements Reloadable, Feedbackable
{
  private Tread _tread;
  private Feedbackable _parent;
  private final boolean _editMode;

  public AddEditTreadDialog(boolean modal, Reloadable parent, Producer producer)
  {
    super(modal, parent);
    if(parent instanceof Feedbackable)
      _parent = (Feedbackable)parent;
    _editMode = false;
    _tread = new Tread();
      _tread.setProducer(producer);
    initComponents();
    loadProducersList();
    reload();
    loadEmptyEntity();
  }

  private void loadEmptyEntity() {
    _tread.setProducer((Producer)_producerComboBox.getSelectedItem());
  }

  public AddEditTreadDialog(boolean modal, Reloadable parent, Tread tread)
  {
    super(modal, parent);
    if(parent instanceof Feedbackable)
      _parent = (Feedbackable)parent;
    _editMode = true;
    _tread = tread;
    initComponents();
    loadProducersList();
    reload();
  }

  private void loadProducersList() {
    DefaultComboBoxModel<Producer> producers = new DefaultComboBoxModel<>();
    try {
      for(Producer producer : ProducersService.getInstance().getProducers()) {
        producers.addElement(producer);
      }
      producers.addElement(new Producer(0, "Dodaj..."));
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    _producerComboBox.setModel(producers);
  }

  @Override
  public final void reload() {
    if(_tread.getProducer()!=null) {
      _producerComboBox.setSelectedIndex(findIndexForItem(_tread.getProducer()));
    }
    if(_tread.getName()!=null) {
      _nameTextField.setText(_tread.getName());
    }
  }

  @Override
  public void sendFeedback(Object feedback) {
    if(feedback != null && feedback instanceof Producer) {
      _tread.setProducer((Producer)feedback);
      //reload();
    }
  }

  private int findIndexForItem(Producer producer) {
    for(int i=0;i<_producerComboBox.getItemCount();i++) {
      if(((Producer)_producerComboBox.getItemAt(i)).getId()==producer.getId())
        return i;
    }
    return 0;
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        _nameTextField = new javax.swing.JTextField();
        bSubmit = new javax.swing.JButton();
        _producerLabel = new javax.swing.JLabel();
        _nameLabel = new javax.swing.JLabel();
        _producerComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bCancel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bCancel.setText("Anuluj");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText(_editMode?"Edycja rozmiaru opony":"Nowy rozmiar opony");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        _nameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _nameTextFieldFocusLost(evt);
            }
        });

        bSubmit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bSubmit.setText("Zapisz");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        _producerLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _producerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _producerLabel.setText("Producent:");

        _nameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _nameLabel.setText("Nazwa:");

        _producerComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        _producerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _producerComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_producerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_producerComboBox, 0, 107, Short.MAX_VALUE)
                            .addComponent(_nameTextField))
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
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
                    .addComponent(_producerLabel)
                    .addComponent(_producerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSubmit)
                    .addComponent(bCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
  {//GEN-HEADEREND:event_bCancelActionPerformed
    close();
  }//GEN-LAST:event_bCancelActionPerformed

  private void _nameTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__nameTextFieldFocusLost
  {//GEN-HEADEREND:event__nameTextFieldFocusLost
    _tread.setName(_nameTextField.getText());
  }//GEN-LAST:event__nameTextFieldFocusLost

  private void bSubmitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSubmitActionPerformed
  {//GEN-HEADEREND:event_bSubmitActionPerformed
    try
    {
      if(_editMode)
        TiresService.getInstance().updateTread(_tread);
      else
        _tread = TiresService.getInstance().addTread(_tread);
      if(_parent!=null)
        _parent.sendFeedback(_tread);
      close(true);
    }
    catch(DatabaseException|SQLException ex)
    {
      ErrorHandler.getInstance().reportError(ex);
    }
  }//GEN-LAST:event_bSubmitActionPerformed

  private void _producerComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__producerComboBoxActionPerformed
  {//GEN-HEADEREND:event__producerComboBoxActionPerformed
    if(((Producer)_producerComboBox.getSelectedItem()).getId()==0) {
      ViewManager.getInstance().showDialog(new AddEditProducerDialog(true, this));
      loadProducersList();
      reload();
    } else {
      _tread.setProducer((Producer)_producerComboBox.getSelectedItem());
    }
  }//GEN-LAST:event__producerComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JComboBox _producerComboBox;
    private javax.swing.JLabel _producerLabel;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}