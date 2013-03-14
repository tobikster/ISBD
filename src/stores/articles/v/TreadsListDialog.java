package stores.articles.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.ViewManager;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import stores.articles.c.TiresService;
import stores.articles.m.TireSize;
import stores.articles.m.Tread;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;

/**
 *
 * @author tobikster
 */
public class TreadsListDialog extends ApplicationDialog implements Reloadable {
	public static final String[] TREADS_TABLE_HEADERS = {
		"Nazwa"
	};
	
	private Producer _producer;
	/**
	 * Creates new form TreadsListDialog
	 */
	public TreadsListDialog(boolean modal, Reloadable parent) throws SQLException {
		super(modal, parent);
		initComponents();
		initProducerComboBox();
		_producer = (Producer) _producerComboBox.getSelectedItem();
		initTreadsTableModel();
	}
	
	/**
	 *
	 */
	@Override
	public void reload() {
		try {
			initProducerComboBox();
			initTreadsTableModel();
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
	}
	
	private void initProducerComboBox() throws SQLException {
		_producerComboBox.setModel(getProducerComboBoxModel());
	}
	
	private void initTreadsTableModel() throws SQLException {
		_treadsTable.setModel(getTreadsTableModel());
	}
	
	private ComboBoxModel<Producer> getProducerComboBoxModel() throws SQLException {
		List<Producer> producers = ProducersService.getInstance().getProducers();
		Producer[] comboBoxData = new Producer[producers.size()];
		for(int i = 0; i < comboBoxData.length; ++i) {
			comboBoxData[i] = producers.get(i);
		}
		return new DefaultComboBoxModel<>(comboBoxData);
	}
	
	private TableModel getTreadsTableModel() throws SQLException {
		List<Tread> treads = TiresService.getInstance().getTreads(_producer);
		Object[][] tableData = new Object[treads.size()][];
		for(int i = 0; i < tableData.length; ++i) {
			tableData[i] = new Tread[1];
			tableData[i][0] = treads.get(i);
		}
		return new DefaultTableModel(tableData, TREADS_TABLE_HEADERS);
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

        _titleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        _treadsTableScrollPane = new javax.swing.JScrollPane();
        _treadsTable = new javax.swing.JTable();
        _actionsPanel = new javax.swing.JPanel();
        _addButton = new javax.swing.JButton();
        _removeButton = new javax.swing.JButton();
        _editButton = new javax.swing.JButton();
        _producerComboBox = new javax.swing.JComboBox();
        _producerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        _titleLabel.setText("Bieżniki opon");

        _treadsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        _treadsTableScrollPane.setViewportView(_treadsTable);

        _addButton.setText("Dodaj");
        _addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addButtonActionPerformed(evt);
            }
        });

        _removeButton.setText("Usuń");
        _removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _removeButtonActionPerformed(evt);
            }
        });

        _editButton.setText("Edycja");
        _editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _actionsPanelLayout = new javax.swing.GroupLayout(_actionsPanel);
        _actionsPanel.setLayout(_actionsPanelLayout);
        _actionsPanelLayout.setHorizontalGroup(
            _actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _actionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_removeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_editButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_addButton)
                .addContainerGap())
        );
        _actionsPanelLayout.setVerticalGroup(
            _actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _actionsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(_actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_addButton)
                    .addComponent(_removeButton)
                    .addComponent(_editButton))
                .addContainerGap())
        );

        _producerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _producerComboBoxActionPerformed(evt);
            }
        });

        _producerLabel.setText("Producent:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(_actionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_treadsTableScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(_producerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_producerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_producerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_producerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_treadsTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_actionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__addButtonActionPerformed
        ViewManager.getInstance().showDialog(new AddEditTreadDialog(true, this, _producer));
    }//GEN-LAST:event__addButtonActionPerformed

    private void _producerComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__producerComboBoxActionPerformed
		try {
			_producer = (Producer) _producerComboBox.getSelectedItem();
			initTreadsTableModel();
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    }//GEN-LAST:event__producerComboBoxActionPerformed

    private void _editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__editButtonActionPerformed
        if(_treadsTable.getSelectedRow() >= 0) {
			Tread tread = (Tread) _treadsTable.getValueAt(_treadsTable.getSelectedRow(), 0);
			ViewManager.getInstance().showDialog(new AddEditTreadDialog(true, this, tread));
		}
    }//GEN-LAST:event__editButtonActionPerformed

    private void _removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__removeButtonActionPerformed
		if (_treadsTable.getSelectedRow() >= 0) {
			try {
				Tread tread = (Tread) _treadsTable.getValueAt(_treadsTable.getSelectedRow(), 0);
				boolean remove = false;
				String message;
				String title = "Usuwanie bieżnika";
				switch (TiresService.getInstance().getTreadRemovability(tread)) {
					case 1:
						message = "Czy na pewno chcesz usunąć bieżnik \"" + tread + "\"?";
						switch (JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Tak", "Nie"}, "Nie")) {
							case 0:
								remove = true;
								break;
						}
						break;

					case 0:
						message = "Usunięcie bieżnika spowoduje usunięcie powiązanych z nim opon. Czy na pewno chcesz to zrobić?";
						switch (JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Tak", "Nie"}, "Nie")) {
							case 0:
								remove = true;
								break;
						}
						break;

					case -1:
						message = "Usunięcie bieżnika jest niemożliwe, ponieważ spowodowałoby to usunięcie danych opon, których stan magazynowych jest niezerowy!";
						JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
				}
				
				if(remove) {
					TiresService.getInstance().deleteTread(tread);
					message = "Bieżnik usunięty pomyślnie!";
					reload();
					JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
				}
			}
			catch (SQLException ex) {
				ErrorHandler.getInstance().reportError(ex);
			}
		}
    }//GEN-LAST:event__removeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel _actionsPanel;
    private javax.swing.JButton _addButton;
    private javax.swing.JButton _editButton;
    private javax.swing.JComboBox _producerComboBox;
    private javax.swing.JLabel _producerLabel;
    private javax.swing.JButton _removeButton;
    private javax.swing.JLabel _titleLabel;
    private javax.swing.JTable _treadsTable;
    private javax.swing.JScrollPane _treadsTableScrollPane;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}