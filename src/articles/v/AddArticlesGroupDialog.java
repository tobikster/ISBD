/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package articles.v;

import articles.m.ArticlesGroup;
import core.c.Reloadable;
import core.v.ApplicationDialog;

/**
 *
 * @author tobikster
 */
public class AddArticlesGroupDialog extends ApplicationDialog {
	/**
	 * Creates new form EditArticlesGroupDialog
	 */
	public AddArticlesGroupDialog(java.awt.Frame parent, boolean modal, Reloadable reloadableParent) {
		super(parent, modal, reloadableParent);
		_articlesGroup = new ArticlesGroup();
		initComponents();
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _titleLabel = new javax.swing.JLabel();
        _articlesGroupDataPanel = new javax.swing.JPanel();
        _nameLabel = new javax.swing.JLabel();
        _nameTextField = new javax.swing.JTextField();
        _codeLabel = new javax.swing.JLabel();
        _codeTextField = new javax.swing.JTextField();
        _vatRateLabel = new javax.swing.JLabel();
        _vatRateComboBox = new javax.swing.JComboBox();
        _attributesListPanel = new javax.swing.JPanel();
        _atributesListLabel = new javax.swing.JLabel();
        _attributesListScrollPane = new javax.swing.JScrollPane();
        _attributesList = new javax.swing.JList();
        _addAtributeButton = new javax.swing.JButton();
        _removeAttributeButton = new javax.swing.JButton();
        _controlPanel = new javax.swing.JPanel();
        _okButton = new javax.swing.JButton();
        _cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _titleLabel.setText("Podaj dane grupy towarowej");

        _articlesGroupDataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        _nameLabel.setText("Nazwa:");

        _codeLabel.setText("Kod:");

        _vatRateLabel.setText("VAT:");

        _vatRateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout _articlesGroupDataPanelLayout = new javax.swing.GroupLayout(_articlesGroupDataPanel);
        _articlesGroupDataPanel.setLayout(_articlesGroupDataPanelLayout);
        _articlesGroupDataPanelLayout.setHorizontalGroup(
            _articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_articlesGroupDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_vatRateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_codeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_articlesGroupDataPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(_vatRateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _articlesGroupDataPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_codeTextField))
                    .addGroup(_articlesGroupDataPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_nameTextField)))
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
                    .addComponent(_codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_codeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_articlesGroupDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_vatRateLabel)
                    .addComponent(_vatRateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        _attributesListPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        _atributesListLabel.setText("Atrybuty grupy:");

        _attributesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        _attributesListScrollPane.setViewportView(_attributesList);

        _addAtributeButton.setText("Dodaj");

        _removeAttributeButton.setText("Usuń");

        javax.swing.GroupLayout _attributesListPanelLayout = new javax.swing.GroupLayout(_attributesListPanel);
        _attributesListPanel.setLayout(_attributesListPanelLayout);
        _attributesListPanelLayout.setHorizontalGroup(
            _attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_attributesListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_attributesListPanelLayout.createSequentialGroup()
                        .addComponent(_atributesListLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(_attributesListScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _attributesListPanelLayout.createSequentialGroup()
                        .addComponent(_removeAttributeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_addAtributeButton)))
                .addContainerGap())
        );
        _attributesListPanelLayout.setVerticalGroup(
            _attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_attributesListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_atributesListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_attributesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_attributesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_addAtributeButton)
                    .addComponent(_removeAttributeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _controlPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        _okButton.setText("OK");

        _cancelButton.setText("Anuluj");

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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 41, Short.MAX_VALUE)
                        .addComponent(_titleLabel)
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addComponent(_articlesGroupDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_attributesListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_articlesGroupDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_attributesListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
	private ArticlesGroup _articlesGroup;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _addAtributeButton;
    private javax.swing.JPanel _articlesGroupDataPanel;
    private javax.swing.JLabel _atributesListLabel;
    private javax.swing.JList _attributesList;
    private javax.swing.JPanel _attributesListPanel;
    private javax.swing.JScrollPane _attributesListScrollPane;
    private javax.swing.JButton _cancelButton;
    private javax.swing.JLabel _codeLabel;
    private javax.swing.JTextField _codeTextField;
    private javax.swing.JPanel _controlPanel;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JButton _okButton;
    private javax.swing.JButton _removeAttributeButton;
    private javax.swing.JLabel _titleLabel;
    private javax.swing.JComboBox _vatRateComboBox;
    private javax.swing.JLabel _vatRateLabel;
    // End of variables declaration//GEN-END:variables
}
