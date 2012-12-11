/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.articles.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import stores.articles.c.TiresService;
import stores.articles.m.*;
import stores.groups.c.GroupsService;
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
  private ListCellRenderer _DOTsTableCellRenderer;
  private ListCellEditor _DOTsTableCellEditor;
  private Border _defaultComponentBorder;
    
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
    _defaultComponentBorder = new JLabel().getBorder();
    initComponents();
    initItemsLists();
    initDOTsTable();
    loadEmptyEntity();
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
    _defaultComponentBorder = new JLabel().getBorder();
    initComponents();
    initItemsLists();
    initDOTsTable();
    reload();
  }

  private void initItemsLists() {
    loadTireSizesList();
    loadLoadIndexesList();
    loadSpeedIndexesList();
    loadProducersList();
    loadTreadsList();
    loadArticlesGroupsList();
  }

  private void initDOTsTable() {
    _DOTsTableCellRenderer=new ListCellRenderer(new int[] {JLabel.CENTER, JLabel.CENTER});
    _DOTsTableCellEditor=new ListCellEditor();
    _tireDOTsTable.getColumnModel().getColumn(0).setCellRenderer(_DOTsTableCellRenderer);
    _tireDOTsTable.getColumnModel().getColumn(0).setCellEditor(_DOTsTableCellEditor);
    _tireDOTsTable.getColumnModel().getColumn(1).setCellRenderer(_DOTsTableCellRenderer);
    _tireDOTsTable.getColumnModel().getColumn(1).setCellEditor(_DOTsTableCellEditor);

    _tireDOTsTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    _tireDOTsTable.setDefaultEditor(String.class, _DOTsTableCellEditor);
    _DOTsTableCellEditor.addCellEditorListener(new CellEditorListener() {
      private boolean hasFullData() {
        for(int i=0;i<_tireDOTsTable.getRowCount();i++) {
          if(isEmptyCell(_tireDOTsTable.getValueAt(i, 0)) || isEmptyCell(_tireDOTsTable.getValueAt(i, 1)))
            return false;
        }
        return true;
      }

      @Override
      public void editingStopped(ChangeEvent e)
      {
        int iRowsCount = _tireDOTsTable.getRowCount();
        int currentRow = _tireDOTsTable.getSelectedRow();
        Object currentDOT = _tireDOTsTable.getValueAt(currentRow, 0);
        Object currentCount = _tireDOTsTable.getValueAt(currentRow, 1);
        if(hasFullData()) {
          if(isProperCount(_tireDOTsTable.getValueAt(currentRow, 1))) {
            ((DefaultTableModel)_tireDOTsTable.getModel()).addRow(new Object[] {});
          }
        } else if(currentRow!=iRowsCount-1 && (isEmptyCell(currentDOT) || !isProperCount(currentCount)) 
            && isEmptyCell(_tireDOTsTable.getValueAt(iRowsCount-1, 0)) && isEmptyCell(_tireDOTsTable.getValueAt(iRowsCount-1, 1))) {
          ((DefaultTableModel)_tireDOTsTable.getModel()).removeRow(iRowsCount-1);
        } else if (currentRow!=iRowsCount-1 && isEmptyCell(currentDOT) && isEmptyCell(currentCount)
            && isEmptyCell(_tireDOTsTable.getValueAt(iRowsCount-1, 0)) && isEmptyCell(_tireDOTsTable.getValueAt(iRowsCount-1, 1))) {
          ((DefaultTableModel)_tireDOTsTable.getModel()).removeRow(currentRow);
        }
      }

      @Override
      public void editingCanceled(ChangeEvent e)
      {
        
      }
    });
  }
  
  private void loadEmptyEntity() {
    _tire.setSize((TireSize)_tireSizeComboBox.getSelectedItem());
    _tire.setLoadIndex((LoadIndex)_loadIndexComboBox.getSelectedItem());
    _tire.setSpeedIndex((SpeedIndex)_speedIndexComboBox.getSelectedItem());
    _tire.setTread((Tread)_treadComboBox.getSelectedItem());
    _tire.setGroup((ArticlesGroup)_articlesGroupComboBox.getSelectedItem());
    
    String currentInput=_marginTextField.getText();
    
    if(currentInput.indexOf(" %")!=-1)
      currentInput=currentInput.substring(0, currentInput.indexOf(" %"));
    currentInput=currentInput.replace(",", ".");
    _tire.setMargin(currentInput);
    
    currentInput=_grossPriceTextField.getText();
    if(currentInput.indexOf(" zł")!=-1)
      currentInput=currentInput.substring(0, currentInput.indexOf(" zł"));
    currentInput=currentInput.replace(",", ".");
    _tire.setGrossPrice(currentInput);
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
    if(_tire.getGroup()!=null) {
      _articlesGroupComboBox.setSelectedIndex(findIndexForItem(_tire.getGroup()));
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
  
  private void loadArticlesGroupsList() {
    DefaultComboBoxModel<ArticlesGroup> articlesGroups = new DefaultComboBoxModel<>();
    try {
      for(ArticlesGroup articlesGroup:  GroupsService.getInstance().getTireGroups()) {
        articlesGroups.addElement(articlesGroup);
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    _articlesGroupComboBox.setModel(articlesGroups);
  }
  
  private int findIndexForItem(ArticlesGroup articlesGroup) {
    for(int i=0;i<_articlesGroupComboBox.getItemCount();i++) {
      if(((ArticlesGroup)_articlesGroupComboBox.getItemAt(i)).getCode()==articlesGroup.getCode())
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

  private boolean isProperCount(Object value) {
        try {
          Integer.parseInt((String)value);
          return true;
        } catch(NumberFormatException ex) {
          return false;
        }
      }

  private boolean isEmptyCell(Object value) {
    return value==null || value.equals("");
  }

  private void parseDOTsToEntity() {
    String sDOT, sCount;
    Map<DOT, String> tireDOTs = new LinkedHashMap<>();
    for(int i=0;i<_tireDOTsTable.getRowCount();i++) {
      sDOT = (String)_tireDOTsTable.getValueAt(i, 0);
      sCount = (String)_tireDOTsTable.getValueAt(i, 1);
      if(sDOT!=null && !sDOT.equals("") && sCount!=null && !sCount.equals("")) {
        DOT dot = new DOT();
        dot.setDot(sDOT);
        tireDOTs.put(dot, sCount);
      }
    }
    _tire.setTireDOTsText(tireDOTs);
  }

  private class ListCellRenderer extends DefaultTableCellRenderer
  {
    private int[] m_iaColumnAligments;

    public ListCellRenderer(int[] iaColumnsAligment)
    {
      m_iaColumnAligments=iaColumnsAligment;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      JLabel label = ((JLabel)c);
      final JSpinner spinner = new JSpinner();
      spinner.addMouseListener(new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e)
        {
          if(e.getClickCount()>1) {
            spinner.setFocusable(true);
            ((JTextField)spinner.getEditor()).setSelectionStart(0);
            
          }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
          
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
          
        }
      });
      label.setFont(new Font("Tahoma", Font.PLAIN, 11));
      if(column>m_iaColumnAligments.length) {
        label.setHorizontalAlignment(JLabel.CENTER);
      } else {
        label.setHorizontalAlignment(m_iaColumnAligments[column]);
      }
      label.setVerticalAlignment(JLabel.CENTER);
      if(isSelected) {
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
      }
      if(column==1 && !isEmptyCell(_tireDOTsTable.getValueAt(row, column)) && !isProperCount(_tireDOTsTable.getValueAt(row, column))) {
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
      } else {
        label.setBorder(_defaultComponentBorder);
      }
      return label;
    }
  }
  
  private class ListCellEditor extends DefaultCellEditor {
   
    public ListCellEditor() {
      super(new JTextField());
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
      JTextField tf = (JTextField) c;
      tf.setHorizontalAlignment(JTextField.CENTER);

      System.out.println("Column: "+column);
      if(column==0) {
        tf.addKeyListener(new KeyAdapter() {        
          @Override
          public void keyReleased(KeyEvent e) {
            JTextField textField = (JTextField) e.getSource();
            String text = textField.getText();
            if(text.length()>4) {
                text=text.substring(0, 4);
                textField.setText(text);
            }
          }
        });
      }

      return tf;
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
        jpBasicDetails = new javax.swing.JPanel();
        _tireSizeLabel = new javax.swing.JLabel();
        _tireSizeComboBox = new javax.swing.JComboBox();
        _producerLabel = new javax.swing.JLabel();
        _producerComboBox = new javax.swing.JComboBox();
        _treadLabel = new javax.swing.JLabel();
        _treadComboBox = new javax.swing.JComboBox();
        _loadIndexComboBox = new javax.swing.JComboBox();
        _speedIndexComboBox = new javax.swing.JComboBox();
        _articlesGroupLabel = new javax.swing.JLabel();
        _articlesGroupComboBox = new javax.swing.JComboBox();
        jpPriceDetails = new javax.swing.JPanel();
        _grossPriceLabel = new javax.swing.JLabel();
        _netPriceLabel = new javax.swing.JLabel();
        _marginLabel = new javax.swing.JLabel();
        _marginTextField = new javax.swing.JTextField();
        _netPriceTextField = new javax.swing.JTextField();
        _grossPriceTextField = new javax.swing.JTextField();
        jpPriceDetails2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        _tireDOTsTable = new javax.swing.JTable();

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

        _treadComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _treadComboBoxActionPerformed(evt);
            }
        });

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

        _articlesGroupLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _articlesGroupLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _articlesGroupLabel.setText("Grupa towarowa:");

        _articlesGroupComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _articlesGroupComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBasicDetailsLayout = new javax.swing.GroupLayout(jpBasicDetails);
        jpBasicDetails.setLayout(jpBasicDetailsLayout);
        jpBasicDetailsLayout.setHorizontalGroup(
            jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(_articlesGroupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(_producerLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(_tireSizeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                        .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                                .addComponent(_producerComboBox, 0, 78, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_treadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                                .addComponent(_tireSizeComboBox, 0, 121, Short.MAX_VALUE)
                                .addGap(16, 16, 16)))
                        .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                                .addComponent(_loadIndexComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_speedIndexComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(_treadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpBasicDetailsLayout.createSequentialGroup()
                        .addComponent(_articlesGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_articlesGroupLabel)
                    .addComponent(_articlesGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        _tireDOTsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "DOT", "Ilość"
            }
        ));
        jScrollPane1.setViewportView(_tireDOTsTable);

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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addComponent(jpPriceDetails2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpPriceDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpBasicDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bSubmit)
                        .addGap(20, 20, 20)
                        .addComponent(bCancel)
                        .addGap(116, 116, 116))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSubmit)
                    .addComponent(bCancel))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bCancel, bSubmit});

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    parseDOTsToEntity();
    try {
      if(_editMode) {
        TiresService.getInstance();
      } else {
        TiresService.getInstance().addTire(_tire);
      }
    } catch(DatabaseException|SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
  }//GEN-LAST:event_bSubmitActionPerformed

  private void _marginTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__marginTextFieldFocusGained
  {//GEN-HEADEREND:event__marginTextFieldFocusGained
	  String currentInput = _marginTextField.getText();
	  if (currentInput.indexOf(" %") != -1) {
		  currentInput = currentInput.substring(0, currentInput.indexOf(" %"));
	  }
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
      _marginTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      _tire.setMargin(value);
    } catch (NumberFormatException ex) {
      //currentInput+=" %";
      _marginTextField.setText(currentInput);
      _marginTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
      _tire.setMargin(currentInput);
    }
  }//GEN-LAST:event__marginTextFieldFocusLost

  private void _netPriceTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__netPriceTextFieldFocusGained
  {//GEN-HEADEREND:event__netPriceTextFieldFocusGained
	  String currentInput = _netPriceTextField.getText();
	  if (currentInput.indexOf(" zł") != -1) {
		  currentInput = currentInput.substring(0, currentInput.indexOf(" zł"));
	  }
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
      //currentInput+=" zł";
      _netPriceTextField.setText(currentInput);
      _netPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
    }
  }//GEN-LAST:event__netPriceTextFieldFocusLost

  private void _grossPriceTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event__grossPriceTextFieldFocusGained
  {//GEN-HEADEREND:event__grossPriceTextFieldFocusGained
	  String currentInput = _grossPriceTextField.getText();
	  if (currentInput.indexOf(" zł") != -1) {
		  currentInput = currentInput.substring(0, currentInput.indexOf(" zł"));
	  }
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
      _tire.setGrossPrice(value);
    }
    catch(NumberFormatException ex)
    {
      //currentInput+=" zł";
      _grossPriceTextField.setText(currentInput);
      _grossPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
      _tire.setGrossPrice(currentInput);
    }
  }//GEN-LAST:event__grossPriceTextFieldFocusLost

  private void _tireSizeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__tireSizeComboBoxActionPerformed
  {//GEN-HEADEREND:event__tireSizeComboBoxActionPerformed
    _tire.setSize((TireSize)_tireSizeComboBox.getSelectedItem());
  }//GEN-LAST:event__tireSizeComboBoxActionPerformed

  private void _loadIndexComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__loadIndexComboBoxActionPerformed
  {//GEN-HEADEREND:event__loadIndexComboBoxActionPerformed
    _tire.setLoadIndex((LoadIndex)_loadIndexComboBox.getSelectedItem());
  }//GEN-LAST:event__loadIndexComboBoxActionPerformed

  private void _speedIndexComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__speedIndexComboBoxActionPerformed
  {//GEN-HEADEREND:event__speedIndexComboBoxActionPerformed
    _tire.setSpeedIndex((SpeedIndex)_speedIndexComboBox.getSelectedItem());
  }//GEN-LAST:event__speedIndexComboBoxActionPerformed

  private void _treadComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__treadComboBoxActionPerformed
  {//GEN-HEADEREND:event__treadComboBoxActionPerformed
    _tire.setTread((Tread)_treadComboBox.getSelectedItem());
  }//GEN-LAST:event__treadComboBoxActionPerformed

  private void _articlesGroupComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__articlesGroupComboBoxActionPerformed
  {//GEN-HEADEREND:event__articlesGroupComboBoxActionPerformed
    _tire.setGroup((ArticlesGroup)_articlesGroupComboBox.getSelectedItem());
  }//GEN-LAST:event__articlesGroupComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox _articlesGroupComboBox;
    private javax.swing.JLabel _articlesGroupLabel;
    private javax.swing.JLabel _grossPriceLabel;
    private javax.swing.JTextField _grossPriceTextField;
    private javax.swing.JComboBox _loadIndexComboBox;
    private javax.swing.JLabel _marginLabel;
    private javax.swing.JTextField _marginTextField;
    private javax.swing.JLabel _netPriceLabel;
    private javax.swing.JTextField _netPriceTextField;
    private javax.swing.JComboBox _producerComboBox;
    private javax.swing.JLabel _producerLabel;
    private javax.swing.JComboBox _speedIndexComboBox;
    private javax.swing.JTable _tireDOTsTable;
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
    private javax.swing.JPanel jpPriceDetails2;
    // End of variables declaration//GEN-END:variables
}
