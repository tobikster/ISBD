/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.groups.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.ViewManager;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import finance.c.FinanceService;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import stores.articles.m.ArticleAttribute;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;

/**
 *
 * @author tobikster
 */
public class AddEditArticlesGroupDialog extends ApplicationDialog implements Reloadable {
	/**
	 * Creates new form EditArticlesGroupDialog
	 */
	public AddEditArticlesGroupDialog(boolean modal, Reloadable reloadableParent, ArticlesGroup group) {
		super(modal, reloadableParent);
		_articlesGroup = group;
		_editMode = true;
		initComponents();
		reload();
	}

	public AddEditArticlesGroupDialog(boolean modal, Reloadable reloadableParent, ArticlesGroupType type) {
		super(modal, reloadableParent);
		_articlesGroup = new ArticlesGroup();
		_articlesGroup.setType(type);
		_articlesGroup.setAttributes(new ArrayList<ArticleAttribute>());
		_editMode = false;
		initComponents();
		reload();
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _contentButtonGroup = new javax.swing.ButtonGroup();
        _titleLabel = new javax.swing.JLabel();
        _articlesGroupDataPanel = new javax.swing.JPanel();
        _nameLabel = new javax.swing.JLabel();
        _nameTextField = new javax.swing.JTextField();
        _vatRateLabel = new javax.swing.JLabel();
        _vatRateComboBox = new javax.swing.JComboBox();
        _attributesListPanel = new javax.swing.JPanel();
        _attributesListPanel.setVisible(_articlesGroup.getType() == ArticlesGroupType.PARTS);
        _atributesListLabel = new javax.swing.JLabel();
        _attributesListScrollPane = new javax.swing.JScrollPane();
        _attributesList = new javax.swing.JList();
        _manageAtributesButton = new javax.swing.JButton();
        _controlPanel = new javax.swing.JPanel();
        _okButton = new javax.swing.JButton();
        _cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        _titleLabel.setText((_editMode) ? "Edytuj grupę towarową" : "Nowa grupa towarowa");

        _articlesGroupDataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        _nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _nameLabel.setText("Nazwa:");

        _vatRateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _vatRateLabel.setText("VAT:");

        javax.swing.GroupLayout _articlesGroupDataPanelLayout = new javax.swing.GroupLayout(_articlesGroupDataPanel);
        _articlesGroupDataPanel.setLayout(_articlesGroupDataPanelLayout);
        _articlesGroupDataPanelLayout.setHorizontalGroup(
            _articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_articlesGroupDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_vatRateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_vatRateComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 112, Short.MAX_VALUE)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        _articlesGroupDataPanelLayout.setVerticalGroup(
            _articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_articlesGroupDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_vatRateLabel)
                    .addComponent(_vatRateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _attributesListPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        _atributesListLabel.setText("Atrybuty grupy:");

        _attributesListScrollPane.setViewportView(_attributesList);

        _manageAtributesButton.setText("Zarządzaj");
        _manageAtributesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _manageAtributesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _attributesListPanelLayout = new javax.swing.GroupLayout(_attributesListPanel);
        _attributesListPanel.setLayout(_attributesListPanelLayout);
        _attributesListPanelLayout.setHorizontalGroup(
            _attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_attributesListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_attributesListPanelLayout.createSequentialGroup()
                        .addComponent(_atributesListLabel)
                        .addGap(0, 131, Short.MAX_VALUE))
                    .addComponent(_attributesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _attributesListPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(_manageAtributesButton)))
                .addContainerGap())
        );
        _attributesListPanelLayout.setVerticalGroup(
            _attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_attributesListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_atributesListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_attributesListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_manageAtributesButton)
                .addContainerGap())
        );

        _okButton.setText("OK");
        _okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _okButtonActionPerformed(evt);
            }
        });

        _cancelButton.setText("Anuluj");
        _cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _cancelButtonActionPerformed(evt);
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
                    .addComponent(_cancelButton)
                    .addComponent(_okButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_articlesGroupDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_attributesListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(_titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_articlesGroupDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_attributesListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__cancelButtonActionPerformed
		super.close(false);
    }//GEN-LAST:event__cancelButtonActionPerformed

    private void _okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__okButtonActionPerformed
		try {
			save();
			if (_editMode) {
				if (GroupsService.getInstance().updateArticlesGroup(_articlesGroup)) {
					super.close(true);
				}
			}
			else {
				if (GroupsService.getInstance().addArticlesGroup(_articlesGroup)) {
					super.close(true);
				}
			}
		}
		catch (SQLException | DatabaseException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    }//GEN-LAST:event__okButtonActionPerformed

    private void _manageAtributesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__manageAtributesButtonActionPerformed
		save();
		ViewManager.getInstance().showDialog(new AttributeChooser(true, this, _articlesGroup));
    }//GEN-LAST:event__manageAtributesButtonActionPerformed

	@Override
	public final void reload() {
		try {
			_nameTextField.setText(_articlesGroup.getName());
			List<VATRate> vatRatesList = FinanceService.getInstance().getVatRates();
			VATRate[] vatRates = new VATRate[vatRatesList.size()];
			int selectedIndex = 0;
			for (int i = 0; i < vatRatesList.size(); ++i) {
				VATRate vatRate = vatRatesList.get(i);
				if (_articlesGroup.getVat() != null && _articlesGroup.getVat().getId() == vatRate.getId()) {
					selectedIndex = i;
				}
				vatRates[i] = vatRate;
			}
			_vatRateComboBox.setModel(new DefaultComboBoxModel<>(vatRates));
			_vatRateComboBox.setSelectedIndex(selectedIndex);

//			_parentGroupTextField.setText(_articlesGroup.getParentGroup() != null ? _articlesGroup.getParentGroup().getName() : "");

			DefaultListModel<ArticleAttribute> model = new DefaultListModel<>();
			List<ArticleAttribute> articlesGroupAttributes = _articlesGroup.getAttributes();
			for (ArticleAttribute attribute : articlesGroupAttributes) {
				model.addElement(attribute);
			}
			_attributesList.setModel(model);
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
	}

	private void save() {
		_articlesGroup.setName(_nameTextField.getText());
		_articlesGroup.setVat((VATRate) (_vatRateComboBox.getSelectedItem()));
//		if(_articlesGroup.getParentGroup() != null) {
//			_articlesGroup.setType(_articlesGroup.getParentGroup().getType());
//		}
	}
	private ArticlesGroup _articlesGroup;
	private final boolean _editMode;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel _articlesGroupDataPanel;
    private javax.swing.JLabel _atributesListLabel;
    private javax.swing.JList _attributesList;
    private javax.swing.JPanel _attributesListPanel;
    private javax.swing.JScrollPane _attributesListScrollPane;
    private javax.swing.JButton _cancelButton;
    private javax.swing.ButtonGroup _contentButtonGroup;
    private javax.swing.JPanel _controlPanel;
    private javax.swing.JButton _manageAtributesButton;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JButton _okButton;
    private javax.swing.JLabel _titleLabel;
    private javax.swing.JComboBox _vatRateComboBox;
    private javax.swing.JLabel _vatRateLabel;
    // End of variables declaration//GEN-END:variables
}
