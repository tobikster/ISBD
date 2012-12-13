/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.articles.v;

import core.c.AuthenticationService;
import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.ViewManager;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.awt.Color;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import stores.articles.c.PartsService;
import stores.articles.m.ArticleAttribute;
import stores.articles.m.Part;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;
import stores.groups.v.AddEditArticlesGroupDialog;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;
import utils.m.WorkingMap;
import workers.m.WorkerPosition;

/**
 *
 * @author tobikster
 */
public class AddEditPartDialog extends ApplicationDialog implements Reloadable {
	private boolean _editMode;
	private Part _part;
	private DecimalFormat _priceFormat;
	private DecimalFormat _percentFormat;
	private final Border _defaultBorder;
	public static final String[] COLUMN_NAMES = {
		"Nazwa",
		"Wartość"
	};

	/**
	 * Creates new form AddArticleDialog
	 *
	 * @param modal
	 * @param reloadableParent
	 * @param group
	 */
	public AddEditPartDialog(boolean modal, Reloadable reloadableParent, ArticlesGroup group) {
		super(modal, reloadableParent);
		_priceFormat = new DecimalFormat("0.00 zł");
		_percentFormat = new DecimalFormat("0.00 %");
		_defaultBorder = new JLabel().getBorder();
		_editMode = false;
		_part = new Part();
		_part.setAttributes(new WorkingMap<ArticleAttribute, String>());
		if (group != null && group.getCode() > 0) {
			_part.setGroup(group);
		}
		initComponents();
		save();
		reload();
	}

	/**
	 * Creates new form AddArticleDialog
	 *
	 * @param modal
	 * @param reloadableParent
	 * @param group
	 */
	public AddEditPartDialog(boolean modal, Reloadable reloadableParent, Part part) {
		super(modal, reloadableParent);
		_priceFormat = new DecimalFormat("0.00 zł");
		_percentFormat = new DecimalFormat("0.00 %");
		_defaultBorder = new JLabel().getBorder();
		_editMode = true;
		_part = part;
		initComponents();
		reload();
    applyPermissions();
	}

	private DefaultComboBoxModel<ArticlesGroup> getGroupsModel() {
		DefaultComboBoxModel<ArticlesGroup> result = null;
		try {
			List<ArticlesGroup> groupsList = GroupsService.getInstance().getPartGroups(false);
			ArticlesGroup[] groups = new ArticlesGroup[groupsList.size()];
			for (int i = 0; i < groups.length; ++i) {
				groups[i] = groupsList.get(i);
			}
			result = new DefaultComboBoxModel<>(groups);
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
		return result;
	}

	private DefaultComboBoxModel<Producer> getProducersModel() {
		DefaultComboBoxModel<Producer> result = null;
		try {
			List<Producer> groupsList = ProducersService.getInstance().getProducers();
			Producer[] groups = new Producer[groupsList.size()];
			for (int i = 0; i < groups.length; ++i) {
				groups[i] = groupsList.get(i);
			}
			result = new DefaultComboBoxModel<>(groups);
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
		return result;
	}

	private TableModel getAttributesModel() {
		TableModel result = new DefaultTableModel();
		if (_part.getAttributes() != null) {
			Object[][] tableData = new Object[_part.getAttributes().size()][];
			int index = 0;
			for (Entry<ArticleAttribute, String> attribute : _part.getAttributes().entrySet()) {
				tableData[index] = new Object[2];
				tableData[index][0] = attribute.getKey();
				tableData[index][1] = attribute.getValue();
				++index;
			}
			result = new DefaultTableModel(tableData, COLUMN_NAMES) {
				boolean[] canEdit = {false, true};

				@Override
				public boolean isCellEditable(int row, int col) {
					return canEdit[col];
				}
			};
		}
		return result;
	}

  private void applyPermissions() {
    if(AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.MECHANIC)) {
      _nameTextField.setEnabled(false);
      _articlesGroupComboBox.setEnabled(false);
      _addArticlesGroupButton.setEnabled(false);
      _producerComboBox.setEnabled(false);
      _catalogNumberTextField.setEnabled(false);
      _marginTextField.setEnabled(false);
      _netPriceTextField.setEnabled(false);
      _grossPriceTextField.setEnabled(false);
      _attributesTable.setEnabled(false);
    }
  }

	@Override
	public final void reload() {
		if (_part != null) {
			_nameTextField.setText(_part.getName());
			_articlesGroupComboBox.setModel(getGroupsModel());
			if (_part.getGroup() != null) {
				int selectedArticlesGroupIndex = 0;
				for (int i = 0; i < _articlesGroupComboBox.getItemCount(); ++i) {
					if (_part.getGroup().getCode() == ((ArticlesGroup) (_articlesGroupComboBox.getItemAt(i))).getCode()) {
						selectedArticlesGroupIndex = i;
					}
				}
				_articlesGroupComboBox.setSelectedIndex(selectedArticlesGroupIndex);
				_attributesTable.setModel(getAttributesModel());
			}
			if (_part.getProducer() != null) {
				int selectedProducerIndex = 0;
				for (int i = 0; i < _producerComboBox.getItemCount(); ++i) {
					if (_part.getProducer().getId() == ((Producer) (_producerComboBox.getItemAt(i))).getId()) {
						selectedProducerIndex = i;
					}
				}
				_producerComboBox.setSelectedIndex(selectedProducerIndex);
			}
			if (_part.getCatalogNumber() != null) {
				_catalogNumberTextField.setText(_part.getCatalogNumber());
			}

			if (_part.getMargin() != null) {
				_marginTextField.setText(_percentFormat.format(_part.getMargin()));
			}

			if (_part.getGrossPrice() != null) {
				_grossPriceTextField.setText(_priceFormat.format(_part.getGrossPrice()));
				_netPriceTextField.setText(_priceFormat.format(_part.getGrossPrice() / (1.0 + _part.getGroup().getVat().getRate())));
			}

			if (_part.getCount() != null) {
				_countSpinner.setValue(_part.getCount());
			}
		}
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _pictureFileChooser = new javax.swing.JFileChooser();
        _titleLabel = new javax.swing.JLabel();
        _dataPanel = new javax.swing.JPanel();
        _articlesGroupLabel = new javax.swing.JLabel();
        _producerLabel = new javax.swing.JLabel();
        _articlesGroupComboBox = new javax.swing.JComboBox();
        _producerComboBox = new javax.swing.JComboBox();
        _catalogNumberLabel = new javax.swing.JLabel();
        _catalogNumberTextField = new javax.swing.JTextField();
        _nameLabel = new javax.swing.JLabel();
        _nameTextField = new javax.swing.JTextField();
        _countLabel = new javax.swing.JLabel();
        _countSpinner = new javax.swing.JSpinner();
        _pictureLabel = new javax.swing.JLabel();
        _picturePathTextField = new javax.swing.JTextField();
        _choosePictureButton = new javax.swing.JButton();
        _marginLabel = new javax.swing.JLabel();
        _marginTextField = new javax.swing.JTextField();
        _netPriceLabel = new javax.swing.JLabel();
        _netPriceTextField = new javax.swing.JTextField();
        _grossPriceLabel = new javax.swing.JLabel();
        _grossPriceTextField = new javax.swing.JTextField();
        _addArticlesGroupButton = new javax.swing.JButton();
        _addProducerButton = new javax.swing.JButton();
        _controlPanel = new javax.swing.JPanel();
        _cancelButton = new javax.swing.JButton();
        _okButton = new javax.swing.JButton();
        _attributesTableScrollPane = new javax.swing.JScrollPane();
        _attributesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        _titleLabel.setText((_editMode) ? "Edycja części" : "Nowa część");

        _articlesGroupLabel.setText("Grupa towarowa:");

        _producerLabel.setText("Producent:");

        _articlesGroupComboBox.setModel(getGroupsModel());
        _articlesGroupComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _articlesGroupComboBoxActionPerformed(evt);
            }
        });

        _producerComboBox.setModel(getProducersModel());
        _producerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _producerComboBoxActionPerformed(evt);
            }
        });

        _catalogNumberLabel.setText("Numer katalogowy:");

        _catalogNumberTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _catalogNumberTextFieldFocusLost(evt);
            }
        });

        _nameLabel.setText("Nazwa:");

        _nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _nameTextFieldFocusLost(evt);
            }
        });

        _countLabel.setText("Ilość:");

        _countSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));
        _countSpinner.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _countSpinnerFocusLost(evt);
            }
        });

        _pictureLabel.setText("Zdjęcie:");

        _picturePathTextField.setText("Podaj ścieżkę do pliku");
        _picturePathTextField.setEnabled(false);

        _choosePictureButton.setText("Wybierz");
        _choosePictureButton.setEnabled(false);

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

        _netPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _netPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _netPriceLabel.setText("Cena netto:");

        _netPriceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _netPriceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _netPriceTextFieldFocusLost(evt);
            }
        });

        _grossPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _grossPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _grossPriceLabel.setText("Cena brutto:");

        _grossPriceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _grossPriceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _grossPriceTextFieldFocusLost(evt);
            }
        });

        _addArticlesGroupButton.setText("Dodaj");
        _addArticlesGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addArticlesGroupButtonActionPerformed(evt);
            }
        });

        _addProducerButton.setText("Dodaj");
        _addProducerButton.setEnabled(false);

        javax.swing.GroupLayout _dataPanelLayout = new javax.swing.GroupLayout(_dataPanel);
        _dataPanel.setLayout(_dataPanelLayout);
        _dataPanelLayout.setHorizontalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_dataPanelLayout.createSequentialGroup()
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, _dataPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_catalogNumberLabel)
                            .addComponent(_nameLabel)
                            .addComponent(_articlesGroupLabel)
                            .addComponent(_producerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(_dataPanelLayout.createSequentialGroup()
                                .addComponent(_producerComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_addProducerButton))
                            .addGroup(_dataPanelLayout.createSequentialGroup()
                                .addComponent(_articlesGroupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_addArticlesGroupButton))
                            .addComponent(_nameTextField)
                            .addComponent(_catalogNumberTextField, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, _dataPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_marginLabel)
                            .addComponent(_netPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_grossPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_countLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_countSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_grossPriceTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_netPriceTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_marginTextField)))
                    .addGroup(_dataPanelLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(_pictureLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_picturePathTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_choosePictureButton)))
                .addContainerGap())
        );
        _dataPanelLayout.setVerticalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_articlesGroupLabel)
                    .addComponent(_articlesGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_addArticlesGroupButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_producerLabel)
                    .addComponent(_producerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_addProducerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_catalogNumberLabel)
                    .addComponent(_catalogNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_marginLabel)
                    .addComponent(_marginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_netPriceLabel)
                    .addComponent(_netPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_grossPriceLabel)
                    .addComponent(_grossPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_countLabel)
                    .addComponent(_countSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_pictureLabel)
                    .addComponent(_picturePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_choosePictureButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout _controlPanelLayout = new javax.swing.GroupLayout(_controlPanel);
        _controlPanel.setLayout(_controlPanelLayout);
        _controlPanelLayout.setHorizontalGroup(
            _controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _controlPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_okButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_cancelButton)
                .addContainerGap())
        );
        _controlPanelLayout.setVerticalGroup(
            _controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_okButton)
                    .addComponent(_cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _attributesTable.setModel(getAttributesModel());
        _attributesTableScrollPane.setViewportView(_attributesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(_attributesTableScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(_titleLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_dataPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_dataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_attributesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void save() {
		_part.setName(_nameTextField.getText());
		_part.setCatalogNumber(_catalogNumberTextField.getText());
		_part.setGroup((ArticlesGroup) (_articlesGroupComboBox.getSelectedItem()));
		_part.setProducer((Producer) (_producerComboBox.getSelectedItem()));
		_part.setCount((Float) (_countSpinner.getValue()));

		String marginInput = _marginTextField.getText();
		if (marginInput.indexOf(" %") != -1) {
			marginInput = marginInput.substring(0, marginInput.indexOf(" %"));
		}
		_part.setMarginText(marginInput.replace(',', '.'));

		String grossPriceInput = _grossPriceTextField.getText();
		if (grossPriceInput.indexOf(" zł") != -1) {
			grossPriceInput = grossPriceInput.substring(0, grossPriceInput.indexOf(" zł"));
		}
		_part.setGrossPriceText(grossPriceInput.replace(',', '.'));

		_part.getAttributes().clear();
		for (int row = 0; row < _attributesTable.getRowCount(); ++row) {
			if (!"".equals((String) (_attributesTable.getValueAt(row, 1)))) {
				_part.getAttributes().put((ArticleAttribute) (_attributesTable.getValueAt(row, 0)), (String) (_attributesTable.getValueAt(row, 1)));
			}
		}
	}

	private void refreshGrossPrice() {
		if (_part.getGroup() != null && _part.getGroup().getVat() != null) {
			String currentInput = _netPriceTextField.getText();
			currentInput = currentInput.substring(0, currentInput.indexOf(" zł"));
			currentInput = currentInput.replace(",", ".");
			double value = Double.parseDouble(currentInput);
			value *= 1 + _part.getGroup().getVat().getRate();
			_grossPriceTextField.setText(_priceFormat.format(value));
		}
	}

	private void refreshNetPrice() {
		if (_part.getGroup() != null && _part.getGroup().getVat() != null) {
			String currentInput = _grossPriceTextField.getText();
			currentInput = currentInput.substring(0, currentInput.indexOf(" zł"));
			currentInput = currentInput.replace(",", ".");
			double value = Double.parseDouble(currentInput);
			value /= 1 + _part.getGroup().getVat().getRate();
			_netPriceTextField.setText(_priceFormat.format(value));
		}
	}

    private void _cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__cancelButtonActionPerformed
		close(false);
    }//GEN-LAST:event__cancelButtonActionPerformed

    private void _articlesGroupComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__articlesGroupComboBoxActionPerformed
		try {
			PartsService.getInstance().changeGroup(_part, (ArticlesGroup) (_articlesGroupComboBox.getSelectedItem()));
			reload();
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    }//GEN-LAST:event__articlesGroupComboBoxActionPerformed

    private void _marginTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__marginTextFieldFocusGained
		String currentInput = _marginTextField.getText();
		if (currentInput.indexOf(" %") != -1) {
			currentInput = currentInput.substring(0, currentInput.indexOf(" %"));
		}
		_marginTextField.setText(currentInput);
		_marginTextField.setSelectionStart(0);
    }//GEN-LAST:event__marginTextFieldFocusGained

    private void _marginTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__marginTextFieldFocusLost
    String currentInput=_marginTextField.getText();
    currentInput=currentInput.replace(",", ".");
    try {
      float value = Float.parseFloat(currentInput);
      _marginTextField.setText(_percentFormat.format(value));
      _marginTextField.setBorder(_defaultBorder);
      _part.setMargin(value);
    } catch (NumberFormatException ex) {
      //currentInput+=" %";
      _marginTextField.setText(currentInput);
      _marginTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
      _part.setMarginText(currentInput);
    }
    }//GEN-LAST:event__marginTextFieldFocusLost

    private void _netPriceTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__netPriceTextFieldFocusGained
		String currentInput = _netPriceTextField.getText();
		if (currentInput.indexOf(" zł") != -1) {
			currentInput = currentInput.substring(0, currentInput.indexOf(" zł"));
		}
		_netPriceTextField.setText(currentInput);
		_netPriceTextField.setSelectionStart(0);
    }//GEN-LAST:event__netPriceTextFieldFocusGained

    private void _netPriceTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__netPriceTextFieldFocusLost
    String currentInput=_netPriceTextField.getText();
    currentInput=currentInput.replace(",", ".");
    try
    {
      float value=Float.parseFloat(currentInput);
      _netPriceTextField.setText(_priceFormat.format(value));
      _netPriceTextField.setBorder(_defaultBorder);
      refreshGrossPrice();
    }
    catch(NumberFormatException ex)
    {
      //currentInput+=" zł";
      _netPriceTextField.setText(currentInput);
      _netPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
    }
    }//GEN-LAST:event__netPriceTextFieldFocusLost

    private void _grossPriceTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__grossPriceTextFieldFocusGained
		String currentInput = _grossPriceTextField.getText();
		if (currentInput.indexOf(" zł") != -1) {
			currentInput = currentInput.substring(0, currentInput.indexOf(" zł"));
		}
		_grossPriceTextField.setText(currentInput);
		_grossPriceTextField.setSelectionStart(0);
    }//GEN-LAST:event__grossPriceTextFieldFocusGained

    private void _grossPriceTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__grossPriceTextFieldFocusLost
    String currentInput=_grossPriceTextField.getText();
    currentInput=currentInput.replace(",", ".");
    try
    {
      float value=Float.parseFloat(currentInput);
      _grossPriceTextField.setText(_priceFormat.format(value));
      _grossPriceTextField.setBorder(_defaultBorder);
      refreshNetPrice();
      _part.setGrossPrice(value);
    }
    catch(NumberFormatException ex)
    {
      //currentInput+=" zł";
      _grossPriceTextField.setText(currentInput);
      _grossPriceTextField.setBorder(BorderFactory.createLineBorder(Color.red));
      _part.setGrossPriceText(currentInput);
    }
    }//GEN-LAST:event__grossPriceTextFieldFocusLost

    private void _okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__okButtonActionPerformed
		save();
		boolean result;
		try {
			if (_editMode) {
				result = PartsService.getInstance().updatePart(_part);
			}
			else {
				result = PartsService.getInstance().addPart(_part);
			}

			if (result) {
				super.close(true);
			}
		}
		catch (DatabaseException | SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    }//GEN-LAST:event__okButtonActionPerformed

    private void _addArticlesGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__addArticlesGroupButtonActionPerformed
		ViewManager.getInstance().showDialog(new AddEditArticlesGroupDialog(true, this, ArticlesGroupType.PARTS));
    }//GEN-LAST:event__addArticlesGroupButtonActionPerformed

    private void _nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__nameTextFieldFocusLost
		_part.setName(_nameTextField.getText());
    }//GEN-LAST:event__nameTextFieldFocusLost

    private void _catalogNumberTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__catalogNumberTextFieldFocusLost
        _part.setCatalogNumber(_catalogNumberTextField.getText());
    }//GEN-LAST:event__catalogNumberTextFieldFocusLost

    private void _producerComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__producerComboBoxActionPerformed
        _part.setProducer((Producer)(_producerComboBox.getSelectedItem()));
    }//GEN-LAST:event__producerComboBoxActionPerformed

    private void _countSpinnerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__countSpinnerFocusLost
		_part.setCount((Float)(_countSpinner.getValue()));
    }//GEN-LAST:event__countSpinnerFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _addArticlesGroupButton;
    private javax.swing.JButton _addProducerButton;
    private javax.swing.JComboBox _articlesGroupComboBox;
    private javax.swing.JLabel _articlesGroupLabel;
    private javax.swing.JTable _attributesTable;
    private javax.swing.JScrollPane _attributesTableScrollPane;
    private javax.swing.JButton _cancelButton;
    private javax.swing.JLabel _catalogNumberLabel;
    private javax.swing.JTextField _catalogNumberTextField;
    private javax.swing.JButton _choosePictureButton;
    private javax.swing.JPanel _controlPanel;
    private javax.swing.JLabel _countLabel;
    private javax.swing.JSpinner _countSpinner;
    private javax.swing.JPanel _dataPanel;
    private javax.swing.JLabel _grossPriceLabel;
    private javax.swing.JTextField _grossPriceTextField;
    private javax.swing.JLabel _marginLabel;
    private javax.swing.JTextField _marginTextField;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JLabel _netPriceLabel;
    private javax.swing.JTextField _netPriceTextField;
    private javax.swing.JButton _okButton;
    private javax.swing.JFileChooser _pictureFileChooser;
    private javax.swing.JLabel _pictureLabel;
    private javax.swing.JTextField _picturePathTextField;
    private javax.swing.JComboBox _producerComboBox;
    private javax.swing.JLabel _producerLabel;
    private javax.swing.JLabel _titleLabel;
    // End of variables declaration//GEN-END:variables
}
