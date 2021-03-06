package stores.groups.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.ViewManager;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import stores.articles.c.PartsService;
import stores.articles.m.ArticleAttribute;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;

/**
 *
 * @author tobikster
 */
public class AttributeChooser extends ApplicationDialog implements Reloadable {
  /**
	 * Creates new form AttributeChooser
	 */
	public AttributeChooser(boolean modal, Reloadable reloadableParent, ArticlesGroup articlesGroup) {
		super(modal, reloadableParent);
		_articlesGroup = articlesGroup;
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

        _titleLabel = new javax.swing.JLabel();
        _attributesListsPanel = new javax.swing.JPanel();
        _availableAttributesListLabel = new javax.swing.JLabel();
        _availableAttributesListScrollPane = new javax.swing.JScrollPane();
        _availableAttributesList = new javax.swing.JList();
        _buttonsPanel = new javax.swing.JPanel();
        _addAllButton = new javax.swing.JButton();
        _addButton = new javax.swing.JButton();
        _removeButton = new javax.swing.JButton();
        _removeAllButton = new javax.swing.JButton();
        _selectedAttributesListLabel = new javax.swing.JLabel();
        _selectedAttributesListScrollPane = new javax.swing.JScrollPane();
        _selectedAttributesList = new javax.swing.JList();
        _addAttributeButton = new javax.swing.JButton();
        _controlPanel = new javax.swing.JPanel();
        _cancelButton = new javax.swing.JButton();
        _okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(320, 300));

        _titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        _titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _titleLabel.setText("Atrybuty grupy " + _articlesGroup.getName());

        _availableAttributesListLabel.setText("Dostępne atrybuty");

        _availableAttributesListScrollPane.setViewportView(_availableAttributesList);

        _addAllButton.setText(">>");
        _addAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addAllButtonActionPerformed(evt);
            }
        });

        _addButton.setText(">");
        _addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addButtonActionPerformed(evt);
            }
        });

        _removeButton.setText("<");
        _removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _removeButtonActionPerformed(evt);
            }
        });

        _removeAllButton.setText("<<");
        _removeAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _removeAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _buttonsPanelLayout = new javax.swing.GroupLayout(_buttonsPanel);
        _buttonsPanel.setLayout(_buttonsPanelLayout);
        _buttonsPanelLayout.setHorizontalGroup(
            _buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(_buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_addAllButton)
                    .addComponent(_addButton)
                    .addComponent(_removeButton)
                    .addComponent(_removeAllButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _buttonsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_addAllButton, _addButton, _removeAllButton, _removeButton});

        _buttonsPanelLayout.setVerticalGroup(
            _buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_addAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_removeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_removeAllButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _selectedAttributesListLabel.setText("Wybrane atrybuty");

        _selectedAttributesListScrollPane.setViewportView(_selectedAttributesList);

        _addAttributeButton.setText("Dodaj");
        _addAttributeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addAttributeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _attributesListsPanelLayout = new javax.swing.GroupLayout(_attributesListsPanel);
        _attributesListsPanel.setLayout(_attributesListsPanelLayout);
        _attributesListsPanelLayout.setHorizontalGroup(
            _attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_attributesListsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_availableAttributesListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(_availableAttributesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(_addAttributeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_selectedAttributesListLabel)
                    .addComponent(_selectedAttributesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        _attributesListsPanelLayout.setVerticalGroup(
            _attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_attributesListsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(_attributesListsPanelLayout.createSequentialGroup()
                        .addGroup(_attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_availableAttributesListLabel)
                            .addComponent(_selectedAttributesListLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_attributesListsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(_attributesListsPanelLayout.createSequentialGroup()
                                .addComponent(_availableAttributesListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_addAttributeButton))
                            .addComponent(_selectedAttributesListScrollPane))
                        .addContainerGap())))
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
                .addComponent(_titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(_controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(_attributesListsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_attributesListsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__okButtonActionPerformed
		close(true);
    }//GEN-LAST:event__okButtonActionPerformed

    private void _cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__cancelButtonActionPerformed
		super.close(false);
    }//GEN-LAST:event__cancelButtonActionPerformed

    private void _addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__addButtonActionPerformed
		for (Object attribute : _availableAttributesList.getSelectedValuesList()) {
			_articlesGroup.addAttribute((ArticleAttribute)(attribute));
		}
		reload();
    }//GEN-LAST:event__addButtonActionPerformed

    private void _removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__removeButtonActionPerformed
		for (Object attribute : _selectedAttributesList.getSelectedValuesList()) {
			_articlesGroup.removeAttribute((ArticleAttribute)(attribute));
		}
		reload();
    }//GEN-LAST:event__removeButtonActionPerformed

    private void _addAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__addAllButtonActionPerformed
		for (int i = 0; i < _availableAttributesList.getModel().getSize(); ++i) {
			_articlesGroup.addAttribute((ArticleAttribute)(_availableAttributesList.getModel().getElementAt(i)));
		}
		reload();
    }//GEN-LAST:event__addAllButtonActionPerformed

    private void _removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__removeAllButtonActionPerformed
		for (int i = 0; i < _selectedAttributesList.getModel().getSize(); ++i) {
			_articlesGroup.removeAttribute((ArticleAttribute)(_selectedAttributesList.getModel().getElementAt(i)));
		}
		reload();
    }//GEN-LAST:event__removeAllButtonActionPerformed

    private void _addAttributeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__addAttributeButtonActionPerformed
		ViewManager.getInstance().showDialog(new AddAttributeDialog(true, this));
    }//GEN-LAST:event__addAttributeButtonActionPerformed

	@Override
	public final void reload() {
		try {
			ListModel<ArticleAttribute> availableAttributesListModel = new DefaultListModel<>();
			for (ArticleAttribute attribute : GroupsService.getInstance().getAvailableAttributes(_articlesGroup.getCode())) {
				((DefaultListModel<ArticleAttribute>)(availableAttributesListModel)).addElement(attribute);
			}
			_availableAttributesList.setModel(availableAttributesListModel);
			ListModel<ArticleAttribute> selectedAttributesListModel = new DefaultListModel<>();
			for (ArticleAttribute attribute : _articlesGroup.getAttributes()) {
				((DefaultListModel<ArticleAttribute>)(selectedAttributesListModel)).addElement(attribute);
			}
			_selectedAttributesList.setModel(selectedAttributesListModel);
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
	}
	
	private ArticlesGroup _articlesGroup;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _addAllButton;
    private javax.swing.JButton _addAttributeButton;
    private javax.swing.JButton _addButton;
    private javax.swing.JPanel _attributesListsPanel;
    private javax.swing.JList _availableAttributesList;
    private javax.swing.JLabel _availableAttributesListLabel;
    private javax.swing.JScrollPane _availableAttributesListScrollPane;
    private javax.swing.JPanel _buttonsPanel;
    private javax.swing.JButton _cancelButton;
    private javax.swing.JPanel _controlPanel;
    private javax.swing.JButton _okButton;
    private javax.swing.JButton _removeAllButton;
    private javax.swing.JButton _removeButton;
    private javax.swing.JList _selectedAttributesList;
    private javax.swing.JLabel _selectedAttributesListLabel;
    private javax.swing.JScrollPane _selectedAttributesListScrollPane;
    private javax.swing.JLabel _titleLabel;
    // End of variables declaration//GEN-END:variables
}
