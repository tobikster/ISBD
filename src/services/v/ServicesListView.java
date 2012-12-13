/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services.v;

import core.c.ErrorHandler;
import core.c.Reloadable;
import core.c.TablePagination;
import core.c.ViewManager;
import core.v.MainMenuView;
import core.v.PaginationPanel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
	private TablePagination<Service> _partsPagination;
  private ServicesGroup _currentGroup;

  /**
   * Creates new form ServicesListView
   */
  public ServicesListView()
  {
    _partsPagination = new TablePagination<>(new ArrayList<Service>(), ROWS_PER_PAGE);
    initComponents();
		refreshPopupMenu();
		//refresArticlesToolbar();
		reload();
    _navPanel.setButtonsListener(new PaginationPanel.ButtonsListener() {
			@Override
			public void nextButtonClicked() {
        _articlesTable.setModel(getPartsTableModel(_partsPagination.getNextPage()));
        _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
			}

			@Override
			public void previousButtonClicked() {
				_articlesTable.setModel(getPartsTableModel(_partsPagination.getPreviousPage()));
        _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
			}

			@Override
			public void firstButtonClicked() {
				_articlesTable.setModel(getPartsTableModel(_partsPagination.getFirstPage()));
        _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
			}

			@Override
			public void lastButtonClicked() {
				_articlesTable.setModel(getPartsTableModel(_partsPagination.getLastPage()));
        _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
			}
		});
  }

  private void refreshPopupMenu() {
		_editGroup.setEnabled(_currentGroup!=null);
		_deleteGroup.setEnabled(_currentGroup!=null);
	}

  public TableModel getPartsTableModel(List<Service> services) {
		Object[][] data = new Object[services.size()][];
		for (int i = 0; i < services.size(); ++i) {
			Service service = services.get(i);
			data[i] = new Object[4];
      data[i][0] = 1;
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
			_partsPagination.setTableData(ServicesService.getInstance().getServices(_currentGroup));
			_articlesTable.setModel(getPartsTableModel(_partsPagination.getCurrentPageData()));
			_navPanel.setCurrentPage(_partsPagination.getCurrentPage());
			_navPanel.setPageCount(_partsPagination.getPageCount());
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
  }
  
  private void updateSelectedGroup() {
    _currentGroup = (ServicesGroup) (((DefaultMutableTreeNode) (_articleGroupsTree.getLastSelectedPathComponent())).getUserObject());
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
        _articleGroupsTree = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        _articlesTable = new javax.swing.JTable();

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

        _articleGroupsTree.setModel(getTreeModel());
        _articleGroupsTree.setRootVisible(false);
        _articleGroupsTree.setSelectionRow(0);
        updateSelectedGroup();
        _articleGroupsTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _articleGroupsTreeMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(_articleGroupsTree);

        _articlesTable.setColumnSelectionAllowed(false);
        _articlesTable.setShowVerticalLines(false);
        _articlesTable.setModel(getPartsTableModel(_partsPagination.getCurrentPageData()));
        jScrollPane4.setViewportView(_articlesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                        .addGap(396, 396, 396))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_navPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

  private void bBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bBackActionPerformed
  {//GEN-HEADEREND:event_bBackActionPerformed
    ViewManager.getInstance().openView(new MainMenuView());
  }//GEN-LAST:event_bBackActionPerformed

  private void _articleGroupsTreeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__articleGroupsTreeMouseClicked
  {//GEN-HEADEREND:event__articleGroupsTreeMouseClicked
    int previous=_currentGroup.getId();
    updateSelectedGroup();
    if(_currentGroup.getId()!=previous)
    {
      refreshPopupMenu();
      //refresArticlesToolbar();
      reload();
    }
  }//GEN-LAST:event__articleGroupsTreeMouseClicked

  private void _addGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__addGroupActionPerformed
  {//GEN-HEADEREND:event__addGroupActionPerformed
//    ssViewManager.getInstance().showDialog(new AddEditArticlesGroupDialog(true, this, _currentGroup.getType()));
  }//GEN-LAST:event__addGroupActionPerformed

  private void _editGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__editGroupActionPerformed
  {//GEN-HEADEREND:event__editGroupActionPerformed
//    ViewManager.getInstance().showDialog(new AddEditArticlesGroupDialog(true, this, _currentGroup));
  }//GEN-LAST:event__editGroupActionPerformed

  private void _deleteGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__deleteGroupActionPerformed
  {//GEN-HEADEREND:event__deleteGroupActionPerformed
//    try
//    {
//      boolean removeGroup=false;
//      String message;
//      final String title="Usuń grupę towarową";
//      final int removability=GroupsService.getInstance().checkRemovabilityGroup(_currentGroup);
//      if(removability>0)
//      {
//        message="Czy na pewno chcesz usunąć grupę towarową \""+_currentGroup+"\"?";
//        switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
//          {
//            "OK", "Anuluj"
//          }, "OK"))
//        {
//          case 0: //OK
//            removeGroup=true;
//            break;
//          case 1: //Cancel
//            removeGroup=false;
//            break;
//        }
//      }
//      else if(removability<0)
//      {
//        message="Grupa towarowa "+_currentGroup.getName()+" nie może zostać usunięta, ponieważ zawiera ";
//        message+=(_currentGroup.getType()==ArticlesGroupType.PARTS) ? "części" : "opony";
//        message+="znajdujące się na magazynie";
//        JOptionPane.showOptionDialog(this, message, title, JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
//          {
//            "OK"
//          }, "OK");
//        removeGroup=false;
//      }
//      else
//      {
//        message="Usunięcie grupy towarowej spowoduje usunięcie powiązanych z nią ";
//        message+=(_currentGroup.getType()==ArticlesGroupType.PARTS) ? "części" : "opon";
//        message+=".\nCzy chcesz kontynuować?";
//        switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]
//          {
//            "Tak", "Nie"
//          }, "Nie"))
//        {
//          case 0: //Tak
//            removeGroup=true;
//            break;
//          case 1:
//            removeGroup=false;
//            break;
//        }
//      }
//      if(removeGroup)
//      {
//        GroupsService.getInstance().removeArticlesGroup(_currentGroup);
//        JOptionPane.showMessageDialog(this, "Dane grupy towarowe zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
//        reload();
//      }
//    }
//    catch(SQLException|DatabaseException ex)
//    {
//      ErrorHandler.getInstance().reportError(ex);
//    }
  }//GEN-LAST:event__deleteGroupActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem _addGroup;
    private javax.swing.JTree _articleGroupsTree;
    private javax.swing.JTable _articlesTable;
    private javax.swing.JMenuItem _deleteGroup;
    private javax.swing.JMenuItem _editGroup;
    private javax.swing.JPopupMenu _groupsPopupMenu;
    private core.v.PaginationPanel _navPanel;
    private javax.swing.JButton bBack;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lTitle;
    // End of variables declaration//GEN-END:variables

}
