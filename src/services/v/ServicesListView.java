/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.TablePagination;
import core.c.ViewManager;
import core.m.DatabaseException;
import core.v.MainMenuView;
import core.v.PaginationPanel;
import java.awt.Cursor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import services.c.ServicesGroupsService;
import services.c.ServicesService;
import services.m.Service;
import services.m.ServicesGroup;

/**
 *
 * @author MRKACZOR
 */
public class ServicesListView extends javax.swing.JPanel implements Reloadable
{
  public static final int ROWS_PER_PAGE = 5;
	public static final String[] SERVICES_COLUMN_NAMES = {
		"Lp.",
    "Nazwa",
		"Cena minimalna",
		"Cena maksymalna"
	};
	private TablePagination<Service> _servicesPagination;
  private ServicesGroup _currentGroup;
  private boolean _reloadTree;

  /**
   * Creates new form ServicesListView
   */
  public ServicesListView()
  {
    _servicesPagination = new TablePagination<>(new ArrayList<Service>(), ROWS_PER_PAGE);
    _reloadTree = false;
    initComponents();
		refreshPopupMenu();
		//refresArticlesToolbar();
		reload();
    _navPanel.setButtonsListener(new PaginationPanel.ButtonsListener() {
			@Override
			public void nextButtonClicked() {
        _servicesTable.setModel(getPartsTableModel(_servicesPagination.getNextPage()));
        _navPanel.setCurrentPage(_servicesPagination.getCurrentPage());
			}

			@Override
			public void previousButtonClicked() {
				_servicesTable.setModel(getPartsTableModel(_servicesPagination.getPreviousPage()));
        _navPanel.setCurrentPage(_servicesPagination.getCurrentPage());
			}

			@Override
			public void firstButtonClicked() {
				_servicesTable.setModel(getPartsTableModel(_servicesPagination.getFirstPage()));
        _navPanel.setCurrentPage(_servicesPagination.getCurrentPage());
			}

			@Override
			public void lastButtonClicked() {
				_servicesTable.setModel(getPartsTableModel(_servicesPagination.getLastPage()));
        _navPanel.setCurrentPage(_servicesPagination.getCurrentPage());
			}
		});
  }

  private void refreshPopupMenu() {
		_editGroup.setEnabled(_currentGroup.getId()!=0);
		_deleteGroup.setEnabled(_currentGroup.getId()!=0);
	}

  public TableModel getPartsTableModel(List<Service> services) {
		Object[][] data = new Object[services.size()][];
    int iStartIndex = _servicesPagination.getCurrentPage()+_servicesPagination.getRowsPerPage();
		for (int i = 0; i < services.size(); ++i) {
			Service service = services.get(i);
			data[i] = new Object[4];
      data[i][0] = ++iStartIndex;
			data[i][1] = service.getName();
			data[i][2] = service.getMinPrice();
			data[i][3] = service.getMaxPrice();
		}
		return new DefaultTableModel(data, SERVICES_COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

  private TreeModel getTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ServicesGroup());
    DefaultMutableTreeNode mainNode = new DefaultMutableTreeNode(new ServicesGroup(0, "DZIAŁY USŁUG", null));
		try {
			for (ServicesGroup group : ServicesGroupsService.getInstance().getServicesGroups()) {
				mainNode.add(new DefaultMutableTreeNode(group));
			}
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    root.add(mainNode);
		return new DefaultTreeModel(root);
	}

  @Override
  public final void reload()
  {
    try {
			_servicesPagination.setTableData(ServicesService.getInstance().getServices(_currentGroup));
			_servicesTable.setModel(getPartsTableModel(_servicesPagination.getCurrentPageData()));
			_navPanel.setCurrentPage(_servicesPagination.getCurrentPage());
			_navPanel.setPageCount(_servicesPagination.getPageCount());
      //reload groups tree if needed
      if(_reloadTree) {
        int iSelectedRow = _servicesGroupsTree.getSelectionRows()[0];
        _servicesGroupsTree.setModel(getTreeModel());
        _servicesGroupsTree.expandRow(0);
        _servicesGroupsTree.setSelectionRow(iSelectedRow);
        _reloadTree = false;
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
  }
  
  private void updateSelectedGroup() {
    _currentGroup = (ServicesGroup) (((DefaultMutableTreeNode) (_servicesGroupsTree.getLastSelectedPathComponent())).getUserObject());
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _groupsPopupMenu = new javax.swing.JPopupMenu();
        _addGroup = new javax.swing.JMenuItem();
        _editGroup = new javax.swing.JMenuItem();
        _deleteGroup = new javax.swing.JMenuItem();
        lTitle = new javax.swing.JLabel();
        bBack = new javax.swing.JButton();
        _navPanel = new core.v.PaginationPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        _servicesGroupsTree = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        _servicesTable = new javax.swing.JTable();
        jpArticlesToolbar = new javax.swing.JPanel();
        _addService = new javax.swing.JLabel();
        _addProvidedService = new javax.swing.JLabel();
        _editService = new javax.swing.JLabel();
        _deleteService = new javax.swing.JLabel();

        _addGroup.setText("Dodaj dział");
        _addGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addGroupActionPerformed(evt);
            }
        });
        _groupsPopupMenu.add(_addGroup);

        _editGroup.setText("Edytuj dział");
        _editGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _editGroupActionPerformed(evt);
            }
        });
        _groupsPopupMenu.add(_editGroup);

        _deleteGroup.setText("Usuń dział");
        _deleteGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _deleteGroupActionPerformed(evt);
            }
        });
        _groupsPopupMenu.add(_deleteGroup);

        lTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/64x64/Hand-Tools-64.png"))); // NOI18N
        lTitle.setText("  USŁUGI");

        bBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Back-48.png"))); // NOI18N
        bBack.setToolTipText("Wróć do głównego menu");
        bBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBackActionPerformed(evt);
            }
        });

        _servicesGroupsTree.setModel(getTreeModel());
        _servicesGroupsTree.setRootVisible(false);
        _servicesGroupsTree.setSelectionRow(0);
        updateSelectedGroup();
        _servicesGroupsTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _servicesGroupsTreeMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(_servicesGroupsTree);

        _servicesTable.setColumnSelectionAllowed(false);
        _servicesTable.setShowVerticalLines(false);
        _servicesTable.setModel(getPartsTableModel(_servicesPagination.getCurrentPageData()));
        jScrollPane4.setViewportView(_servicesTable);

        _addService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Service-Add-48.png"))); // NOI18N
        _addService.setToolTipText("Dodaj nową oponę");
        _addService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _addServiceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _addServiceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _addServiceMouseExited(evt);
            }
        });

        _addProvidedService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Document-Service-48.png"))); // NOI18N
        _addProvidedService.setToolTipText("Usuń wybraną część");
        _addProvidedService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _addProvidedServiceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _addProvidedServiceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _addProvidedServiceMouseExited(evt);
            }
        });

        _editService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Service-Edit-48.png"))); // NOI18N
        _editService.setToolTipText("Edytuj wybraną oponę");
        _editService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _editServiceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _editServiceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _editServiceMouseExited(evt);
            }
        });

        _deleteService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Service-Delete-48.png"))); // NOI18N
        _deleteService.setToolTipText("Usuń wybraną oponę");
        _deleteService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _deleteServiceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _deleteServiceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _deleteServiceMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpArticlesToolbarLayout = new javax.swing.GroupLayout(jpArticlesToolbar);
        jpArticlesToolbar.setLayout(jpArticlesToolbarLayout);
        jpArticlesToolbarLayout.setHorizontalGroup(
            jpArticlesToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpArticlesToolbarLayout.createSequentialGroup()
                .addComponent(_addProvidedService)
                .addGap(18, 18, 18)
                .addComponent(_addService, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_editService, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_deleteService))
        );
        jpArticlesToolbarLayout.setVerticalGroup(
            jpArticlesToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpArticlesToolbarLayout.createSequentialGroup()
                .addGroup(jpArticlesToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_deleteService, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(_editService, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_addService, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_addProvidedService, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpArticlesToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(jpArticlesToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_navPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

  private void bBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bBackActionPerformed
  {//GEN-HEADEREND:event_bBackActionPerformed
    ViewManager.getInstance().openView(new MainMenuView());
  }//GEN-LAST:event_bBackActionPerformed

  private void _servicesGroupsTreeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__servicesGroupsTreeMouseClicked
  {//GEN-HEADEREND:event__servicesGroupsTreeMouseClicked
    int previous=_currentGroup.getId();
    updateSelectedGroup();
    if(_currentGroup.getId()!=previous)
    {
      refreshPopupMenu();
      //refresArticlesToolbar();
      reload();
    }
  }//GEN-LAST:event__servicesGroupsTreeMouseClicked

  private void _addGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__addGroupActionPerformed
  {//GEN-HEADEREND:event__addGroupActionPerformed
    _reloadTree = true;
    ViewManager.getInstance().showDialog(new AddEditServicesGroupDialog(true, this));
  }//GEN-LAST:event__addGroupActionPerformed

  private void _editGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__editGroupActionPerformed
  {//GEN-HEADEREND:event__editGroupActionPerformed
    _reloadTree = true;
    ViewManager.getInstance().showDialog(new AddEditServicesGroupDialog(true, this, _currentGroup));
  }//GEN-LAST:event__editGroupActionPerformed

  private void _deleteGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__deleteGroupActionPerformed
  {//GEN-HEADEREND:event__deleteGroupActionPerformed
    try {
      boolean removeGroup=false;
      String message;
      final String title="Usuń dział usług";
      final int removability=ServicesGroupsService.getInstance().checkRemovabilityGroup(_currentGroup);
      if(removability>0) {
        message="Czy na pewno chcesz usunąć grupę towarową \""+_currentGroup+"\"?";
        switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"OK", "Anuluj"}, "OK"))
        {
          case 0: //OK
            removeGroup=true;
            break;
          case 1: //Cancel
            removeGroup=false;
            break;
        }
      }
      else if(removability<0) {
        message="Dział usług "+_currentGroup+" nie może zostać usunięty, ponieważ zawiera usługi znajdujące się w historii świadczonych usług.";
        JOptionPane.showOptionDialog(this, message, title, JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"OK"}, "OK");
        removeGroup=false;
      }
      else {
        message="Usunięcie wybranego działu usług spowoduje utratę danych dotyczących powiązanych z nim usług.\nCzy chcesz kontynuować?";
        switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"Tak", "Nie"}, "Nie"))
        {
          case 0: //Tak
            removeGroup=true;
            break;
          case 1:
            removeGroup=false;
            break;
        }
      }
      if(removeGroup) {
        ServicesGroupsService.getInstance().deleteServicesGroup(_currentGroup);
        JOptionPane.showMessageDialog(this, "Dane działu usług zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
        _reloadTree = true;
        reload();
      }
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
  }//GEN-LAST:event__deleteGroupActionPerformed

  private void _addServiceMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addServiceMouseClicked
  {//GEN-HEADEREND:event__addServiceMouseClicked
    ViewManager.getInstance().showDialog(new AddEditServiceDialog(true, this, _currentGroup));
  }//GEN-LAST:event__addServiceMouseClicked

  private void _addServiceMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addServiceMouseEntered
  {//GEN-HEADEREND:event__addServiceMouseEntered
    setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__addServiceMouseEntered

  private void _addServiceMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addServiceMouseExited
  {//GEN-HEADEREND:event__addServiceMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__addServiceMouseExited

  private void _addProvidedServiceMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addProvidedServiceMouseClicked
  {//GEN-HEADEREND:event__addProvidedServiceMouseClicked
//    try
//    {
//      String title="Usunięcie części";
//      Part part=PartsService.getInstance().getPart(_partsPagination.getCurrentPageData().get(_articlesTable.getSelectedRow()).getId());
//      switch(JOptionPane.showOptionDialog(this, "Czy na pewno chcesz usunąć część "+part.getName(), title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
//        {
//          "Tak", "Nie"
//        }, "Nie"))
//      {
//        case 0:
//          PartsService.getInstance().deletePart(part);
//          JOptionPane.showMessageDialog(this, "Dane części zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
//          reload();
//          break;
//      }
//    }
//    catch(SQLException ex)
//    {
//      ErrorHandler.getInstance().reportError(ex);
//    }
  }//GEN-LAST:event__addProvidedServiceMouseClicked

  private void _addProvidedServiceMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addProvidedServiceMouseEntered
  {//GEN-HEADEREND:event__addProvidedServiceMouseEntered
//    if(_articlesTable.getSelectedRow()>=0)
//      setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__addProvidedServiceMouseEntered

  private void _addProvidedServiceMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addProvidedServiceMouseExited
  {//GEN-HEADEREND:event__addProvidedServiceMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__addProvidedServiceMouseExited

  private void _editServiceMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editServiceMouseClicked
  {//GEN-HEADEREND:event__editServiceMouseClicked
    if(_servicesTable.getSelectedRow()>=0) {
        Service service=_servicesPagination.getCurrentPageData().get(_servicesTable.getSelectedRow());
        ViewManager.getInstance().showDialog(new AddEditServiceDialog(true, this, service));
    }
  }//GEN-LAST:event__editServiceMouseClicked

  private void _editServiceMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editServiceMouseEntered
  {//GEN-HEADEREND:event__editServiceMouseEntered
    if(_servicesTable.getSelectedRow()>=0)
      setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__editServiceMouseEntered

  private void _editServiceMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editServiceMouseExited
  {//GEN-HEADEREND:event__editServiceMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__editServiceMouseExited

  private void _deleteServiceMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteServiceMouseClicked
  {//GEN-HEADEREND:event__deleteServiceMouseClicked
//    if(_servicesTable.getSelectedRow()>=0)
//      try
//      {
//        Tire tire=TiresService.getInstance().getTire(_tiresPagination.getCurrentPageData().get(_articlesTable.getSelectedRow()).getId());
//        boolean removeTire=false;
//        String title="Usuń oponę";
//        String message;
//        if(tire.getCount()>0)
//        {
//          message="Czy jesteś pewien, że chcesz usunąć oponę, która wciąż znajduje się na magazynie w ilości "+tire.getCount()+" szt.?";
//          switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
//            {
//              "Tak", "Nie"
//            }, "Nie"))
//          {
//            case 0: //Tak
//              removeTire=true;
//              break;
//            case 1:
//              removeTire=false;
//              break;
//          }
//        }
//        else
//        {
//          message="Czy jesteś pewien, że chcesz usunąć wybraną oponę?";
//          switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
//            {
//              "Tak", "Nie"
//            }, "Nie"))
//          {
//            case 0: //Tak
//              removeTire=true;
//              break;
//            case 1:
//              removeTire=false;
//              break;
//          }
//        }
//        if(removeTire)
//        {
//          TiresService.getInstance().deleteTire(tire);
//          JOptionPane.showMessageDialog(this, "Dane opony zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
//          reload();
//        }
//      }
//      catch(SQLException ex)
//      {
//        ErrorHandler.getInstance().reportError(ex);
//      }
  }//GEN-LAST:event__deleteServiceMouseClicked

  private void _deleteServiceMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteServiceMouseEntered
  {//GEN-HEADEREND:event__deleteServiceMouseEntered
    if(_servicesTable.getSelectedRow()>=0)
      setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__deleteServiceMouseEntered

  private void _deleteServiceMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteServiceMouseExited
  {//GEN-HEADEREND:event__deleteServiceMouseExited
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__deleteServiceMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem _addGroup;
    private javax.swing.JLabel _addProvidedService;
    private javax.swing.JLabel _addService;
    private javax.swing.JMenuItem _deleteGroup;
    private javax.swing.JLabel _deleteService;
    private javax.swing.JMenuItem _editGroup;
    private javax.swing.JLabel _editService;
    private javax.swing.JPopupMenu _groupsPopupMenu;
    private core.v.PaginationPanel _navPanel;
    private javax.swing.JTree _servicesGroupsTree;
    private javax.swing.JTable _servicesTable;
    private javax.swing.JButton bBack;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel jpArticlesToolbar;
    private javax.swing.JLabel lTitle;
    // End of variables declaration//GEN-END:variables

}
