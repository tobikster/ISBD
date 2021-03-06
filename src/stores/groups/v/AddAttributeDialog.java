/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stores.groups.v;

import stores.articles.c.PartsService;
import stores.articles.m.ArticleAttribute;
import core.c.ErrorHandler;
import core.c.Reloadable;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import stores.groups.c.GroupsService;

/**
 *
 * @author tobikster
 */
public class AddAttributeDialog extends ApplicationDialog implements Reloadable{
  /**
	 * Creates new form AddAttributeDialog
	 */
	public AddAttributeDialog(boolean modal, Reloadable reloadableParent) {
		super(modal, reloadableParent);
		_attribute = new ArticleAttribute();
		initComponents();
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _dataPanel = new javax.swing.JPanel();
        _nameLabel = new javax.swing.JLabel();
        _nameTextField = new javax.swing.JTextField();
        _titleLabel = new javax.swing.JLabel();
        _controlPanel = new javax.swing.JPanel();
        _cancelButton = new javax.swing.JButton();
        _okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _nameLabel.setText("Nazwa:");

        javax.swing.GroupLayout _dataPanelLayout = new javax.swing.GroupLayout(_dataPanel);
        _dataPanel.setLayout(_dataPanelLayout);
        _dataPanelLayout.setHorizontalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_nameTextField)
                .addContainerGap())
        );
        _dataPanelLayout.setVerticalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _titleLabel.setText("Dodaj atrybut");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _controlPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(_controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_cancelButton)
                    .addComponent(_okButton))
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
                        .addComponent(_controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(_titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(_dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_dataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	@Override
	public void reload() {
		_nameTextField.setText(_attribute.getName());
	}
	
	private void save() {
		_attribute.setName(_nameTextField.getText());
	}
	
    private void _cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__cancelButtonActionPerformed
		close(false);
    }//GEN-LAST:event__cancelButtonActionPerformed

    private void _okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__okButtonActionPerformed
		try {
			save();
			if(GroupsService.getInstance().addAttribute(_attribute)) {
				close(true);
			}
		}
		catch (DatabaseException | SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    }//GEN-LAST:event__okButtonActionPerformed
	private ArticleAttribute _attribute;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _cancelButton;
    private javax.swing.JPanel _controlPanel;
    private javax.swing.JPanel _dataPanel;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JButton _okButton;
    private javax.swing.JLabel _titleLabel;
    // End of variables declaration//GEN-END:variables
}
