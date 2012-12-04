/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workers.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.TablePagination;
import core.c.ViewManager;
import core.v.MainMenuView;
import core.v.PaginationPanel;
import java.awt.Cursor;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import workers.c.WorkersService;
import workers.m.Worker;

/**
 *
 * @author Zjamnik
 */
public class WorkersView extends JPanel implements Reloadable {
	public static final int ROWS_PER_PAGE = 5;
	public static final String[] COLUMN_NAMES = {
		"Imię",
		"Nazwisko",
		"Stanowisko"
	};
	private TablePagination<Worker> _pagination;

	/**
	 * Creates new form WorkersView
	 */
	public WorkersView() {
		_pagination = new TablePagination<>(new LinkedList<Worker>(), ROWS_PER_PAGE);
		initComponents();
		reload();
		_navPanel.setButtonsListener(new PaginationPanel.ButtonsListener() {
			@Override
			public void nextButtonClicked() {
				_workersTable.setModel(getTableModel(_pagination.getNextPage()));
				_navPanel.setCurrentPage(_pagination.getCurrentPage());
			}

			@Override
			public void previousButtonClicked() {
				_workersTable.setModel(getTableModel(_pagination.getPreviousPage()));
				_navPanel.setCurrentPage(_pagination.getCurrentPage());
			}

			@Override
			public void firstButtonClicked() {
				_workersTable.setModel(getTableModel(_pagination.getFirstPage()));
				_navPanel.setCurrentPage(_pagination.getCurrentPage());
			}

			@Override
			public void lastButtonClicked() {
				_workersTable.setModel(getTableModel(_pagination.getLastPage()));
				_navPanel.setCurrentPage(_pagination.getCurrentPage());
			}
		});
	}

	public TableModel getTableModel(List<Worker> workers) {
		Object[][] data = new Object[workers.size()][];
		for (int i = 0; i < workers.size(); ++i) {
			Worker worker = workers.get(i);
			data[i] = new Object[3];
			data[i][0] = worker.getName();
			data[i][1] = worker.getSurname();
			data[i][2] = worker.getJob();
		}
		return new DefaultTableModel(data, COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	@Override
	public final void reload() {
		try {
			_pagination.setTableData(WorkersService.getInstance().getWorkers());
			_workersTable.setModel(getTableModel(_pagination.getCurrentPageData()));
			_navPanel.setCurrentPage(_pagination.getCurrentPage());
			_navPanel.setPageCount(_pagination.getPageCount());
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
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

        lTitle = new javax.swing.JLabel();
        _navPanel = new core.v.PaginationPanel();
        _tableScrollPanel = new javax.swing.JScrollPane();
        _workersTable = new javax.swing.JTable();
        _workersTable.setColumnSelectionAllowed(false);
        _workersTable.setShowVerticalLines(false);
        jPanel1 = new javax.swing.JPanel();
        _editWorker = new javax.swing.JLabel();
        _addWorker = new javax.swing.JLabel();
        _deleteWorker = new javax.swing.JLabel();
        _searchWorker = new javax.swing.JLabel();
        bBack = new javax.swing.JButton();

        lTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/64x64/Group-64.png"))); // NOI18N
        lTitle.setText("  PRACOWNICY");

        _workersTable.setModel(getTableModel(_pagination.getCurrentPageData()));
        _tableScrollPanel.setViewportView(_workersTable);

        _editWorker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Male-user-Edit-48.png"))); // NOI18N
        _editWorker.setToolTipText("Edytuj wybranego pracownika");
        _editWorker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _editWorkerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _editWorkerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _editWorkerMouseExited(evt);
            }
        });

        _addWorker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Male-user-Add-48.png"))); // NOI18N
        _addWorker.setToolTipText("Dodaj nowego pracownika");
        _addWorker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _addWorkerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _addWorkerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _addWorkerMouseExited(evt);
            }
        });

        _deleteWorker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Male-user-Remove-48.png"))); // NOI18N
        _deleteWorker.setToolTipText("Usuń wybranego pracownika");
        _deleteWorker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _deleteWorkerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _deleteWorkerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _deleteWorkerMouseExited(evt);
            }
        });

        _searchWorker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Male-user-Search-48.png"))); // NOI18N
        _searchWorker.setToolTipText("Wyszukaj pracownika");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(_searchWorker)
                .addGap(18, 18, 18)
                .addComponent(_addWorker)
                .addGap(18, 18, 18)
                .addComponent(_editWorker)
                .addGap(18, 18, 18)
                .addComponent(_deleteWorker)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_searchWorker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(_addWorker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(_editWorker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(_deleteWorker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Back-48.png"))); // NOI18N
        bBack.setToolTipText("Wróć do menu głównego");
        bBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_tableScrollPanel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(_tableScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bBack, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

  private void _deleteWorkerMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteWorkerMouseClicked
  {//GEN-HEADEREND:event__deleteWorkerMouseClicked
	  if (_workersTable.getSelectedRow() >= 0) {
		  try {
			  Worker worker = WorkersService.getInstance().getWorker(_pagination.getCurrentPageData().get(_workersTable.getSelectedRow()).getId());
			  ViewManager.getInstance().showDialog(new RemoveWorkerConfirmDialog(ViewManager.getInstance().getMainWindow(), true, this, worker));
		  }
		  catch (SQLException ex) {
			  ErrorHandler.getInstance().reportError(ex);
		  }
	  }
  }//GEN-LAST:event__deleteWorkerMouseClicked

  private void _editWorkerMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editWorkerMouseClicked
  {//GEN-HEADEREND:event__editWorkerMouseClicked
	  if (_workersTable.getSelectedRow() >= 0) {
		  try {
			  Worker worker = WorkersService.getInstance().getWorker(_pagination.getCurrentPageData().get(_workersTable.getSelectedRow()).getId());
			  ViewManager.getInstance().showDialog(new EditWorkerView(ViewManager.getInstance().getMainWindow(), true, this, worker));
		  }
		  catch (SQLException ex) {
			  ErrorHandler.getInstance().reportError(ex);
		  }
	  }
  }//GEN-LAST:event__editWorkerMouseClicked

  private void _addWorkerMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addWorkerMouseClicked
  {//GEN-HEADEREND:event__addWorkerMouseClicked
	  ViewManager.getInstance().showDialog(new AddWorkerView(ViewManager.getInstance().getMainWindow(), true, this));
  }//GEN-LAST:event__addWorkerMouseClicked

  private void bBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bBackActionPerformed
  {//GEN-HEADEREND:event_bBackActionPerformed
	  ViewManager.getInstance().openView(new MainMenuView());
  }//GEN-LAST:event_bBackActionPerformed

  private void _addWorkerMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addWorkerMouseEntered
  {//GEN-HEADEREND:event__addWorkerMouseEntered
    setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__addWorkerMouseEntered

  private void _addWorkerMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addWorkerMouseExited
  {//GEN-HEADEREND:event__addWorkerMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__addWorkerMouseExited

  private void _editWorkerMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editWorkerMouseEntered
  {//GEN-HEADEREND:event__editWorkerMouseEntered
    if(_workersTable.getSelectedRow() >= 0) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
  }//GEN-LAST:event__editWorkerMouseEntered

  private void _editWorkerMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editWorkerMouseExited
  {//GEN-HEADEREND:event__editWorkerMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__editWorkerMouseExited

  private void _deleteWorkerMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteWorkerMouseEntered
  {//GEN-HEADEREND:event__deleteWorkerMouseEntered
    if(_workersTable.getSelectedRow() >= 0) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
  }//GEN-LAST:event__deleteWorkerMouseEntered

  private void _deleteWorkerMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteWorkerMouseExited
  {//GEN-HEADEREND:event__deleteWorkerMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__deleteWorkerMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _addWorker;
    private javax.swing.JLabel _deleteWorker;
    private javax.swing.JLabel _editWorker;
    private core.v.PaginationPanel _navPanel;
    private javax.swing.JLabel _searchWorker;
    private javax.swing.JScrollPane _tableScrollPanel;
    private javax.swing.JTable _workersTable;
    private javax.swing.JButton bBack;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lTitle;
    // End of variables declaration//GEN-END:variables
}
