/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.articles.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.v.ApplicationDialog;
import java.awt.Color;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.NumberFormatter;
import stores.articles.c.TiresService;
import stores.articles.m.*;
import stores.groups.m.ArticlesGroup;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;

/**
 *
 * @author MRKACZOR
 */
public class AddEditTireDialog extends ApplicationDialog implements Reloadable
{
  private boolean _editMode;
  private Tire _tire;
  private DecimalFormat _priceFormat = new DecimalFormat("0.00 zł");
  private DecimalFormat _percentFormat = new DecimalFormat("0.00 %");
    
  /**
   * Creates new form AddEditTireDialog
   */
  public AddEditTireDialog(boolean modal, Reloadable reloadableParent, ArticlesGroup group)
  {
    super(modal, reloadableParent);
    _editMode=false;
    _tire=new Tire();
    if(group!=null && group.getCode()>0) {
      _tire.setGroup(group);
    }
    initComponents();
    initItemsLists();
    reload();
  }
  
  /**
   * Creates new form AddEditTireDialog
   */
  public AddEditTireDialog(boolean modal, Reloadable reloadableParent, Tire tire)
  {
    super(modal, reloadableParent);
    _editMode=true;
    _tire=tire;
    initComponents();
    initItemsLists();
    reload();
  }

  private void initItemsLists() {
    loadTireSizesList();
    loadLoadIndexesList();
    loadSpeedIndexesList();
    loadProducersList();
    loadTreadsList();
  }

  @Override
  public final void reload()
  {
    if(_tire.getSize()!=null) {
      _tireSizeComboBox.setSelectedIndex(findIndexForItem(_tire.getSize()));
    }
    if(_tire.getLoadIndex()!=null) {
      _loadIndexComboBox.setSelectedIndex(findIndexForItem(_tire.getLoadIndex()));
    }
    if(_tire.getSpeedIndex()!=null) {
      _speedIndexComboBox.setSelectedIndex(findIndexForItem(_tire.getSpeedIndex()));
    }
    if(_tire.getTread()!=null) {
      _producerComboBox.setSelectedIndex(findIndexForItem(_tire.getTread().getProducer()));
      _treadComboBox.setSelectedIndex(findIndexForItem(_tire.getTread()));
    }
    if(_tire.getMargin()!=null) {
      _marginTextField.setText(_percentFormat.format(_tire.getMargin()));
    }
    if(_tire.getGrossPrice()!=null) {
      _grossPriceTextField.setText(_priceFormat.format(_tire.getGrossPrice()));
      if(_tire.getGroup()!=null)
        _netPriceTextField.setText(_priceFormat.format(_tire.getNetPrice()));
    }
  }

  private void loadTireSizesList() {
    DefaultComboBoxModel<TireSize> tireSizes = new DefaultComboBoxModel<>();
    try {
      for(TireSize tireSize:  TiresService.getInstance().getTireSizes()) {
        tireSizes.addElement(tireSize);
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    _tireSizeComboBox.setModel(tireSizes);
  }
  
  private int findIndexForItem(TireSize tireSize) {
    for(int i=0;i<_tireSizeComboBox.getItemCount();i++) {
      if(((TireSize)_tireSizeComboBox.getItemAt(i)).getId()==tireSize.getId())
        return i;
    }
    return 0;
  }

  private void loadSpeedIndexesList() {
    DefaultComboBoxModel<SpeedIndex> speedIndexes = new DefaultComboBoxModel<>(SpeedIndex.values());
    _speedIndexComboBox.setModel(speedIndexes);
  }
  
  private int findIndexForItem(SpeedIndex speedIndex) {
    for(int i=0;i<_speedIndexComboBox.getItemCount();i++) {
      if(((SpeedIndex)_speedIndexComboBox.getItemAt(i)).equals(speedIndex))
        return i;
    }
    return 0;
  }

  private void loadLoadIndexesList() {
    DefaultComboBoxModel<LoadIndex> loadIndexes = new DefaultComboBoxModel<>(LoadIndex.values());
    _loadIndexComboBox.setModel(loadIndexes);
  }
  
  private int findIndexForItem(LoadIndex loadIndex) {
    for(int i=0;i<_loadIndexComboBox.getItemCount();i++) {
      if(((LoadIndex)_loadIndexComboBox.getItemAt(i)).equals(loadIndex))
        return i;
    }
    return 0;
  }

  private void loadProducersList() {
    DefaultComboBoxModel<Producer> producers = new DefaultComboBoxModel<>();
    try {
      for(Producer producer : ProducersService.getInstance().getProducers()) {
        producers.addElement(producer);
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    _producerComboBox.setModel(producers);
  }
  
  private int findIndexForItem(Producer producer) {
    for(int i=0;i<_producerComboBox.getItemCount();i++) {
      if(((Producer)_producerComboBox.getItemAt(i)).getId()==producer.getId())
        return i;
    }
    return 0;
  }

  private void loadTreadsList() {
    DefaultComboBoxModel<Tread> treads = new DefaultComboBoxModel<>();
    try {
      for(Tread tread : TiresService.getInstance().getTreads((Producer)_producerComboBox.getSelectedItem())) {
        treads.addElement(tread);
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    _treadComboBox.setModel(treads);
  }
  
  private int findIndexForItem(Tread tread) {
    for(int i=0;i<_treadComboBox.getItemCount();i++) {
      if(((Tread)_treadComboBox.getItemAt(i)).getId()==tread.getId())
        return i;
    }
    return 0;
  }
  
  private void refreshGrossPrice() {
    if(_tire.getGroup() != null && _tire.getGroup().getVat()!=null) {
      String currentInput=_netPriceTextField.getText();
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
      currentInput=currentInput.replace(",", ".");
      double value = Double.parseDouble(currentInput);
      value*=1+_tire.getGroup().getVat().getRate();
      _grossPriceTextField.setText(_priceFormat.format(value));
    }
  }
  
  private void refreshNetPrice() {
    if(_tire.getGroup() != null && _tire.getGroup().getVat()!=null) {
      String currentInput=_grossPriceTextField.getText();
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
      currentInput=currentInput.replace(",", ".");
      double value = Double.parseDouble(currentInput);
      value/=1+_tire.getGroup().getVat().getRate();
      _netPriceTextField.setText(_priceFormat.format(value));
    }
  }

  private void getDOTsTableModel() {
    
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
        jpBasicDetails = new javax.swing.JPanel();
        _tireSizeLabel = new javax.swing.JLabel();
        _tireSizeComboBox = new javax.swing.JComboBox();
        _producerLabel = new javax.swing.JLabel();
        _producerComboBox = new javax.swing.JComboBox();
        _treadLabel = new javax.swing.JLabel();
        _treadComboBox = new javax.swing.JComboBox();
        _loadIndexComboBox = new javax.swing.JComboBox();
        _speedIndexComboBox = new javax.swing.JComboBox();
        jpPriceDetails = new javax.swing.JPanel();
        _grossPriceLabel = new javax.swing.JLabel();
        _netPriceLabel = new javax.swing.JLabel();
        _marginLabel = new javax.swing.JLabel();
        _marginTextField = new javax.swing.JTextField();
        _netPriceTextField = new javax.swing.JTextField();
        _grossPriceTextField = new javax.swing.JTextField();
        jpPriceDetails2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtTireDOTs = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText(_editMode?"Edycja opony":"Nowa opona");
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

        jpBasicDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Informacje podstawowe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(153, 153, 153)));

        _tireSizeLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _tireSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _tireSizeLabel.setText("Rozmiar:");

        _tireSizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _tireSizeComboBoxActionPerformed(evt);
            }
        });

        _producerLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _producerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _producerLabel.setText("Producent:");

        _producerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _producerComboBoxActionPerformed(evt);
            }
        });

        _treadLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _treadLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _treadLabel.setText("Bieżnik:");

        _loadIndexComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _loadIndexComboBoxActionPerformed(evt);
            }
        });

        _speedIndexComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _speedIndexComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBasicDetailsLayout = new javax.swing.GroupLayout(jpBasicDetails);
        jpBasicDetails.setLayout(jpBasicDetailsLayout);
        jpBasicDetailsLayout.setHorizontalGroup(
            jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_producerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(_tireSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                        .addComponent(_producerComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_treadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addComponent(_tireSizeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                        .addComponent(_loadIndexComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_speedIndexComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(_treadComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpBasicDetailsLayout.setVerticalGroup(
            jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_tireSizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_tireSizeLabel)
                    .addComponent(_loadIndexComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_speedIndexComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_producerLabel)
                    .addComponent(_treadLabel)
                    .addComponent(_treadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_producerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpBasicDetailsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_producerComboBox, _tireSizeComboBox, _treadComboBox});

        jpPriceDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Informacje cenowe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(153, 153, 153)));

        _grossPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _grossPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _grossPriceLabel.setText("Cena brutto:");

        _netPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _netPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _netPriceLabel.setText("Cena netto:");

        _marginLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _marginLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _marginLabel.setText("Marża:");

        _marginTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _marginTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _marginTextFieldFocusLost(evt);
            }
        });

        _netPriceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _netPriceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _netPriceTextFieldFocusLost(evt);
            }
        });

        _grossPriceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _grossPriceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _grossPriceTextFieldFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jpPriceDetailsLayout = new javax.swing.GroupLayout(jpPriceDetails);
        jpPriceDetails.setLayout(jpPriceDetailsLayout);
        jpPriceDetailsLayout.setHorizontalGroup(
            jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPriceDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(_netPriceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_grossPriceLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(_marginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_marginTextField)
                    .addComponent(_netPriceTextField)
                    .addComponent(_grossPriceTextField))
                .addGap(20, 20, 20))
        );
        jpPriceDetailsLayout.setVerticalGroup(
            jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPriceDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_marginLabel)
                    .addComponent(_marginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_netPriceLabel)
                    .addComponent(_netPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPriceDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_grossPriceLabel)
                    .addComponent(_grossPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jpPriceDetails2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Informacje ilościowe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(153, 153, 153)));

        jtTireDOTs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "DOT", "Ilość"
            }
        ));
        jScrollPane1.setViewportView(jtTireDOTs);

        javax.swing.GroupLayout jpPriceDetails2Layout = new javax.swing.GroupLayout(jpPriceDetails2);
        jpPriceDetails2.setLayout(jpPriceDetails2Layout);
        jpPriceDetails2Layout.setHorizontalGroup(
            jpPriceDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPriceDetails2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpPriceDetails2Layout.setVerticalGroup(
            jpPriceDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPriceDetails2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jpPriceDetails2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpPriceDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpBasicDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 114, Short.MAX_VALUE)
                        .addComponent(bSubmit)
                        .addGap(20, 20, 20)
                        .addComponent(bCancel)
                        .addGap(0, 112, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bCancel, bSubmit});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBasicDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpPriceDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpPriceDetails2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSubmit)
                    .addComponent(bCancel))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bCancel, bSubmit});

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void _tireSizeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__tireSizeComboBoxActionPerformed
  {//GEN-HEADEREND:event__tireSizeComboBoxActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event__tireSizeComboBoxActionPerformed

  private void _producerComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__producerComboBoxActionPerformed
  {//GEN-HEADEREND:event__producerComboBoxActionPerformed
    loadTreadsList();
  }//GEN-LAST:event__producerComboBoxActionPerformed

  private void bCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bCancelActionPerformed
  {//GEN-HEADEREND:event_bCancelActionPerformed
    close();
  }//GEN-LAST:event_bCancelActionPerformed

  private void bSubmitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSubmitActionPerformed
  {//GEN-HEADEREND:event_bSubmitActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_bSubmitActionPerformed

  private void _marginTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__marginTextFieldFocusGained
  {//GEN-HEADEREND:event__marginTextFieldFocusGained
    String currentInput=_marginTextField.getText();
    if(currentInput.indexOf(" %")!=-1)
      currentInput=currentInput.substring(0, currentInput.indexOf(" %"));
    _marginTextField.setText(currentInput);
    _marginTextField.setSelectionStart(0);
  }//GEN-LAST:event__marginTextFieldFocusGained

  private void _marginTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__marginTextFieldFocusLost
  {//GEN-HEADEREND:event__marginTextFieldFocusLost
    String currentInput=_marginTextField.getText();
    currentInput=currentInput.replace(",", ".");
    double value;
    try {
      value = Double.parseDouble(currentInput);
      _marginTextField.setText(_percentFormat.format(value));
      _marginTextField.setBorder(null);
    } catch (NumberFormatException ex) {
      currentInput+=" %";
      _marginTextField.setText(currentInput);
      _marginTextField.setBorder(BorderFactory.createLineBorder(Color.red));
    }
  }//GEN-LAST:event__marginTextFieldFocusLost

  private void _netPriceTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__netPriceTextFieldFocusGained
  {//GEN-HEADEREND:event__netPriceTextFieldFocusGained
    String currentInput=_netPriceTextField.getText();
    if(currentInput.indexOf(" zł")!=-1)
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
    _netPriceTextField.setText(currentInput);
    _netPriceTextField.setSelectionStart(0);
  }//GEN-LAST:event__netPriceTextFieldFocusGained

  private void _netPriceTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__netPriceTextFieldFocusLost
  {//GEN-HEADEREND:event__netPriceTextFieldFocusLost
    String currentInput=_netPriceTextField.getText();
    currentInput=currentInput.replace(",", ".");
    double value;
    try
    {
      value=Double.parseDouble(currentInput);
      _netPriceTextField.setText(_priceFormat.format(value));
      _netPriceTextField.setBorder(null);
      refreshGrossPrice();
    }
    catch(NumberFormatException ex)
    {
      currentInput+=" zł";
      _netPriceTextField.setText(currentInput);
      _netPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
    }
  }//GEN-LAST:event__netPriceTextFieldFocusLost

  private void _grossPriceTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__grossPriceTextFieldFocusGained
  {//GEN-HEADEREND:event__grossPriceTextFieldFocusGained
    String currentInput=_grossPriceTextField.getText();
    if(currentInput.indexOf(" zł")!=-1)
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
    _grossPriceTextField.setText(currentInput);
    _grossPriceTextField.setSelectionStart(0);
  }//GEN-LAST:event__grossPriceTextFieldFocusGained

  private void _grossPriceTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event__grossPriceTextFieldFocusLost
  {//GEN-HEADEREND:event__grossPriceTextFieldFocusLost
    String currentInput=_grossPriceTextField.getText();
    currentInput=currentInput.replace(",", ".");
    double value;
    try
    {
      value=Double.parseDouble(currentInput);
      _grossPriceTextField.setText(_priceFormat.format(value));
      _grossPriceTextField.setBorder(null);
      refreshNetPrice();
    }
    catch(NumberFormatException ex)
    {
      currentInput+=" zł";
      _grossPriceTextField.setText(currentInput);
      _grossPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
    }
  }//GEN-LAST:event__grossPriceTextFieldFocusLost

  private void _loadIndexComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__loadIndexComboBoxActionPerformed
  {//GEN-HEADEREND:event__loadIndexComboBoxActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event__loadIndexComboBoxActionPerformed

  private void _speedIndexComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__speedIndexComboBoxActionPerformed
  {//GEN-HEADEREND:event__speedIndexComboBoxActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event__speedIndexComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _grossPriceLabel;
    private javax.swing.JLabel _grossPriceLabel1;
    private javax.swing.JTextField _grossPriceTextField;
    private javax.swing.JFormattedTextField _grossPriceTextField1;
    private javax.swing.JComboBox _loadIndexComboBox;
    private javax.swing.JLabel _marginLabel;
    private javax.swing.JLabel _marginLabel1;
    private javax.swing.JTextField _marginTextField;
    private javax.swing.JFormattedTextField _marginTextField1;
    private javax.swing.JLabel _netPriceLabel;
    private javax.swing.JTextField _netPriceTextField;
    private javax.swing.JLabel _priceLabel1;
    private javax.swing.JFormattedTextField _priceTextField1;
    private javax.swing.JComboBox _producerComboBox;
    private javax.swing.JLabel _producerLabel;
    private javax.swing.JComboBox _speedIndexComboBox;
    private javax.swing.JComboBox _tireSizeComboBox;
    private javax.swing.JLabel _tireSizeLabel;
    private javax.swing.JComboBox _treadComboBox;
    private javax.swing.JLabel _treadLabel;
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jpBasicDetails;
    private javax.swing.JPanel jpPriceDetails;
    private javax.swing.JPanel jpPriceDetails1;
    private javax.swing.JPanel jpPriceDetails2;
    private javax.swing.JTable jtTireDOTs;
    // End of variables declaration//GEN-END:variables
}
