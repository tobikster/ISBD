/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.articles.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import stores.articles.m.Part;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;

/**
 *
 * @author tobikster
 */
public class AddEditPartDialog extends ApplicationDialog implements Reloadable {
	private boolean _editMode;
	private Part _part;

	/**
	 * Creates new form AddArticleDialog
	 *
	 * @param modal
	 * @param reloadableParent
	 * @param group
	 */
	public AddEditPartDialog(boolean modal, Reloadable reloadableParent, ArticlesGroup group) {
		super(modal, reloadableParent);
		_editMode = false;
		_part = new Part();
		if (group != null && group.getCode() > 0) {
			_part.setGroup(group);
		}
		initComponents();
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
		_editMode = true;
		_part = part;
		initComponents();
		reload();
	}

	private DefaultComboBoxModel<ArticlesGroup> getGroupsModel() {
		DefaultComboBoxModel<ArticlesGroup> result = null;
		try {
			List<ArticlesGroup> groupsList = GroupsService.getInstance().getPartGroups();
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

	@Override
	public final void reload() {
		if (_part != null) {
			_nameTextField.setText(_part.getName());
			if (_part.getGroup() != null) {
				int selectedArticlesGroupIndex = 0;
				for (int i = 0; i < _articlesGroupComboBox.getItemCount(); ++i) {
					if (_part.getGroup().getCode() == ((ArticlesGroup) (_articlesGroupComboBox.getItemAt(i))).getCode()) {
						selectedArticlesGroupIndex = i;
					}
				}
				_articlesGroupComboBox.setSelectedIndex(selectedArticlesGroupIndex);
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
			_catalogNumberTextField.setText(_part.getCatalogNumber());
			_countSpinner.setValue(_part.getCount());
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
        _marginLabel = new javax.swing.JLabel();
        _priceLabel = new javax.swing.JLabel();
        _countLabel = new javax.swing.JLabel();
        _countSpinner = new javax.swing.JSpinner();
        _pictureLabel = new javax.swing.JLabel();
        _picturePathTextField = new javax.swing.JTextField();
        _choosePictureButton = new javax.swing.JButton();
        _marginTextField = new javax.swing.JTextField();
        _grossPriceTextField = new javax.swing.JTextField();
        _controlPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        _attributesTableScrollPane = new javax.swing.JScrollPane();
        _attributesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        _titleLabel.setText("Nowa część");

        _articlesGroupLabel.setText("Grupa towarowa:");

        _producerLabel.setText("Producent:");

        _articlesGroupComboBox.setModel(getGroupsModel());
        _articlesGroupComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _articlesGroupComboBoxActionPerformed(evt);
            }
        });

        _producerComboBox.setModel(getProducersModel());

        _catalogNumberLabel.setText("Numer katalogowy:");

        _nameLabel.setText("Nazwa:");

        _marginLabel.setText("Marża:");

        _priceLabel.setText("Cena:");

        _countLabel.setText("Ilość:");

        _countSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        _pictureLabel.setText("Zdjęcie:");

        _picturePathTextField.setText("Podaj ścieżkę do pliku");
        _picturePathTextField.setEnabled(false);

        _choosePictureButton.setText("Wybierz");

        javax.swing.GroupLayout _dataPanelLayout = new javax.swing.GroupLayout(_dataPanel);
        _dataPanel.setLayout(_dataPanelLayout);
        _dataPanelLayout.setHorizontalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(_pictureLabel)
                    .addComponent(_countLabel)
                    .addComponent(_priceLabel)
                    .addComponent(_marginLabel)
                    .addComponent(_catalogNumberLabel)
                    .addComponent(_producerLabel)
                    .addComponent(_articlesGroupLabel)
                    .addComponent(_nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_catalogNumberTextField)
                    .addComponent(_countSpinner)
                    .addComponent(_producerComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_articlesGroupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(_dataPanelLayout.createSequentialGroup()
                        .addComponent(_picturePathTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_choosePictureButton))
                    .addComponent(_nameTextField)
                    .addComponent(_marginTextField)
                    .addComponent(_grossPriceTextField))
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
                    .addComponent(_articlesGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_producerLabel)
                    .addComponent(_producerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(_priceLabel)
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
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jButton1.setText("Anuluj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("OK");

        javax.swing.GroupLayout _controlPanelLayout = new javax.swing.GroupLayout(_controlPanel);
        _controlPanel.setLayout(_controlPanelLayout);
        _controlPanelLayout.setHorizontalGroup(
            _controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _controlPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        _controlPanelLayout.setVerticalGroup(
            _controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _attributesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
                .addComponent(_attributesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
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
		_part.setCount((int) (_countSpinner.getValue()));
//		_part.setMargin(Double.parseDouble(_marginTextField.getText()));
//		_part.setGrossPrice(Double.parseDouble(_priceLabel.getText()));
	}

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void _articlesGroupComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__articlesGroupComboBoxActionPerformed
		System.out.println("Siema");
    }//GEN-LAST:event__articlesGroupComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox _articlesGroupComboBox;
    private javax.swing.JLabel _articlesGroupLabel;
    private javax.swing.JTable _attributesTable;
    private javax.swing.JScrollPane _attributesTableScrollPane;
    private javax.swing.JLabel _catalogNumberLabel;
    private javax.swing.JTextField _catalogNumberTextField;
    private javax.swing.JButton _choosePictureButton;
    private javax.swing.JPanel _controlPanel;
    private javax.swing.JLabel _countLabel;
    private javax.swing.JSpinner _countSpinner;
    private javax.swing.JPanel _dataPanel;
    private javax.swing.JTextField _grossPriceTextField;
    private javax.swing.JLabel _marginLabel;
    private javax.swing.JTextField _marginTextField;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JFileChooser _pictureFileChooser;
    private javax.swing.JLabel _pictureLabel;
    private javax.swing.JTextField _picturePathTextField;
    private javax.swing.JLabel _priceLabel;
    private javax.swing.JComboBox _producerComboBox;
    private javax.swing.JLabel _producerLabel;
    private javax.swing.JLabel _titleLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
