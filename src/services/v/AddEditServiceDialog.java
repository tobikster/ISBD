package services.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.border.Border;
import services.c.ServicesGroupsService;
import services.c.ServicesService;
import services.m.Service;
import services.m.ServicesGroup;
import utils.c.Formatter;

/**
 *
 * @author MRKACZOR
 */
public class AddEditServiceDialog extends ApplicationDialog
{
  private final boolean _editMode;
  private Service _service;
  private Border _defaultComponentBorder;

  /**
   * Creates new form AddEditServiceDialog
   */
  public AddEditServiceDialog(boolean modal, Reloadable parent, ServicesGroup group)
  {
    super(modal, parent);
    _editMode = false;
    _service = new Service();
    _service.setGroup(group);
    initComponents();
    initialize();
  }

  public AddEditServiceDialog(boolean modal, Reloadable parent, Service service)
  {
    super(modal, parent);
    _editMode = false;
    _service = new Service();
    _service = service.clone();
    initComponents();
    initialize();
  }

  private void initialize() {
    _defaultComponentBorder = _nameLabel.getBorder();
    loadServicesGroupsList();
    if(_editMode) {
      _nameTextField.setText(_service.getName());
      _groupComboBox.setSelectedIndex(findIndexForItem(_service.getGroup()));
      _minPriceTextField.setText(Formatter.formatPrice(_service.getMinPrice()));
      _maxPriceTextField.setText(Formatter.formatPrice(_service.getMaxPrice()));
    } else {
      _service.setName(_nameTextField.getText());
      if(_service.getGroup()!=null) {
        _groupComboBox.setSelectedIndex(findIndexForItem(_service.getGroup()));
      } else {
        _service.setGroup((ServicesGroup)_groupComboBox.getSelectedItem());
      }
    }
  }

  private void loadServicesGroupsList() {
    DefaultComboBoxModel<ServicesGroup> servicesGroups = new DefaultComboBoxModel<>();
    try {
      List<ServicesGroup> groups = ServicesGroupsService.getInstance().getServicesGroups();
      for(ServicesGroup group : groups) {
        servicesGroups.addElement(group);
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    _groupComboBox.setModel(servicesGroups);
  }

  private int findIndexForItem(ServicesGroup group) {
    for(int i=0;i<_groupComboBox.getItemCount();i++) {
      if(((ServicesGroup)_groupComboBox.getItemAt(i)).getId().equals(group.getId()))
        return i;
    }
    return 0;
  }

  private void refreshMaxPrice() {
    if(_service.getMaxPrice()!=null && _service.getMinPrice()!=null) {
      if(_service.getMinPrice()<=_service.getMaxPrice()) {
        _maxPriceTextField.setBorder(_defaultComponentBorder);
      } else {
        _minPriceTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
      }
    }
  }

  private void refreshMinPrice() {
    if(_service.getMaxPrice()!=null && _service.getMinPrice()!=null) {
      if(_service.getMinPrice()<=_service.getMaxPrice()) {
        _minPriceTextField.setBorder(_defaultComponentBorder);
      } else {
        _maxPriceTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
      }
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

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        _minPriceTextField = new javax.swing.JTextField();
        _minPriceLabel = new javax.swing.JLabel();
        _maxPriceLabel = new javax.swing.JLabel();
        _maxPriceTextField = new javax.swing.JTextField();
        _nameLabel = new javax.swing.JLabel();
        _nameTextField = new javax.swing.JTextField();
        _groupLabel = new javax.swing.JLabel();
        _groupComboBox = new javax.swing.JComboBox();
        bCancel = new javax.swing.JButton();
        bSubmit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 210));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText(_editMode?"Edycja usługi":"Nowa usługa");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        _minPriceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _minPriceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _minPriceTextFieldFocusLost(evt);
            }
        });

        _minPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _minPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _minPriceLabel.setText("Cena minimalna:");

        _maxPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _maxPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _maxPriceLabel.setText("Cena maksymalna:");

        _maxPriceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _maxPriceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _maxPriceTextFieldFocusLost(evt);
            }
        });

        _nameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _nameLabel.setText("Nazwa:");

        _nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _nameTextFieldFocusLost(evt);
            }
        });

        _groupLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _groupLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _groupLabel.setText("Dział usług:");

        bCancel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bCancel.setText("Anuluj");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        bSubmit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bSubmit.setText("Zapisz");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_groupLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(_groupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(_minPriceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(_maxPriceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(_nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(_maxPriceTextField)
                                    .addComponent(_minPriceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                    .addComponent(_nameTextField))))
                        .addGap(24, 24, 24))
                    .addComponent(jSeparator1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_groupLabel, _maxPriceLabel, _minPriceLabel, _nameLabel});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bCancel, bSubmit});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_groupLabel)
                    .addComponent(_groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_minPriceLabel)
                    .addComponent(_minPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_maxPriceLabel)
                    .addComponent(_maxPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancel)
                    .addComponent(bSubmit))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_groupLabel, _maxPriceLabel, _maxPriceTextField, _minPriceLabel, _minPriceTextField, _nameLabel, _nameTextField});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bCancel, bSubmit});

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void _minPriceTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__minPriceTextFieldFocusGained
  {//GEN-HEADEREND:event__minPriceTextFieldFocusGained
    String currentInput=_minPriceTextField.getText();
    if(currentInput.indexOf(" zł")!=-1) {
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
      _minPriceTextField.setText(currentInput);
    }
    _minPriceTextField.setSelectionStart(0);
  }//GEN-LAST:event__minPriceTextFieldFocusGained

  private void _minPriceTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__minPriceTextFieldFocusLost
  {//GEN-HEADEREND:event__minPriceTextFieldFocusLost
    String currentInput=_minPriceTextField.getText();
    currentInput=currentInput.replace(",", ".");
    float value;
    try
    {
      value=Float.parseFloat(currentInput);
      _minPriceTextField.setText(Formatter.formatPrice(value));
      _minPriceTextField.setBorder(_defaultComponentBorder);
      refreshMaxPrice();
      _service.setMinPrice(value);
    }
    catch(NumberFormatException ex)
    {
      //currentInput+=" zł";
      _minPriceTextField.setText(currentInput);
      _minPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
      _service.setMinPrice(currentInput);
    }
  }//GEN-LAST:event__minPriceTextFieldFocusLost

  private void _maxPriceTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__maxPriceTextFieldFocusGained
  {//GEN-HEADEREND:event__maxPriceTextFieldFocusGained
    String currentInput=_maxPriceTextField.getText();
    if(currentInput.indexOf(" zł")!=-1) {
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
      _maxPriceTextField.setText(currentInput);
    }
    _maxPriceTextField.setSelectionStart(0);
  }//GEN-LAST:event__maxPriceTextFieldFocusGained

  private void _maxPriceTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__maxPriceTextFieldFocusLost
  {//GEN-HEADEREND:event__maxPriceTextFieldFocusLost
    String currentInput=_maxPriceTextField.getText();
    currentInput=currentInput.replace(",", ".");
    float value;
    try
    {
      value=Float.parseFloat(currentInput);
      _maxPriceTextField.setText(Formatter.formatPrice(value));
      _maxPriceTextField.setBorder(_defaultComponentBorder);
      refreshMinPrice();
      _service.setMaxPrice(value);
    }
    catch(NumberFormatException ex)
    {
      //currentInput+=" zł";
      _maxPriceTextField.setText(currentInput);
      _maxPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
      _service.setMaxPrice(currentInput);
    }
  }//GEN-LAST:event__maxPriceTextFieldFocusLost

  private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
  {//GEN-HEADEREND:event_bCancelActionPerformed
    close(false);
  }//GEN-LAST:event_bCancelActionPerformed

  private void bSubmitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSubmitActionPerformed
  {//GEN-HEADEREND:event_bSubmitActionPerformed
    try {
      if(_editMode) {
        
      } else {
        ServicesService.getInstance().addService(_service);
      }
      close(true);
    } catch(DatabaseException|SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
  }//GEN-LAST:event_bSubmitActionPerformed

  private void _nameTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__nameTextFieldFocusLost
  {//GEN-HEADEREND:event__nameTextFieldFocusLost
    _service.setName(_nameTextField.getText());
  }//GEN-LAST:event__nameTextFieldFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox _groupComboBox;
    private javax.swing.JLabel _groupLabel;
    private javax.swing.JLabel _maxPriceLabel;
    private javax.swing.JTextField _maxPriceTextField;
    private javax.swing.JLabel _minPriceLabel;
    private javax.swing.JTextField _minPriceTextField;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
