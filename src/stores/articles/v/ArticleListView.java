package stores.articles.v;

import core.c.*;
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
import stores.articles.c.PartsService;
import stores.articles.c.TiresService;
import stores.articles.m.Part;
import stores.articles.m.Tire;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;
import stores.groups.v.AddEditArticlesGroupDialog;
import workers.m.WorkerPosition;

/**
 *
 * @author MRKACZOR
 */
public class ArticleListView extends javax.swing.JPanel implements Reloadable {
	public static final int ROWS_PER_PAGE = 15;
	public static final String[] PARTS_COLUMN_NAMES = {
		"Nazwa",
		"Producent",
		"Numer katalogowy",
		"Cena brutto",
		"Ilość"
	};
	public static final String[] TIRES_COLUMN_NAMES = {
		"Rozmiar",
		"",
		"",
		"Producent",
		"Bieżnik",
		"Cena brutto",
		"Ilość"
	};
	private static final int ARTICLES_ROOT = -1;
	private static final int TIRES_ROOT = -2;
	private TablePagination<Part> _partsPagination;
	private TablePagination<Tire> _tiresPagination;
  private ArticlesGroup _currentGroup;

	/**
	 * Creates new form ArticleListView
	 */
	public ArticleListView() {
		_partsPagination = new TablePagination<>(new ArrayList<Part>(), ROWS_PER_PAGE);
		_tiresPagination = new TablePagination<>(new ArrayList<Tire>(), ROWS_PER_PAGE);
		initComponents();
		refreshPopupMenu();
		refresArticlesToolbar();
		reload();
		_navPanel.setButtonsListener(new PaginationPanel.ButtonsListener() {
			@Override
			public void nextButtonClicked() {
        if(_currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
          _articlesTable.setModel(getPartsTableModel(_partsPagination.getNextPage()));
          _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
        } else {
          _articlesTable.setModel(getTiresTableModel(_tiresPagination.getNextPage()));
          _navPanel.setCurrentPage(_tiresPagination.getCurrentPage());
        }
			}

			@Override
			public void previousButtonClicked() {
				if(_currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
          _articlesTable.setModel(getPartsTableModel(_partsPagination.getPreviousPage()));
          _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
        } else {
          _articlesTable.setModel(getTiresTableModel(_tiresPagination.getPreviousPage()));
          _navPanel.setCurrentPage(_tiresPagination.getCurrentPage());
        }
			}

			@Override
			public void firstButtonClicked() {
				if(_currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
          _articlesTable.setModel(getPartsTableModel(_partsPagination.getFirstPage()));
          _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
        } else {
          _articlesTable.setModel(getTiresTableModel(_tiresPagination.getFirstPage()));
          _navPanel.setCurrentPage(_tiresPagination.getCurrentPage());
        }
			}

			@Override
			public void lastButtonClicked() {
				if(_currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
          _articlesTable.setModel(getPartsTableModel(_partsPagination.getLastPage()));
          _navPanel.setCurrentPage(_partsPagination.getCurrentPage());
        } else {
          _articlesTable.setModel(getTiresTableModel(_tiresPagination.getLastPage()));
          _navPanel.setCurrentPage(_tiresPagination.getCurrentPage());
        }
			}
		});
	}

	private void refreshPopupMenu() {
		_editGroup.setEnabled(isValidGroupSelected());
		_deleteGroup.setEnabled(isValidGroupSelected());
	}

	private void refresArticlesToolbar() {
		if (_currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
			_addPart.setVisible(true);
			_editPart.setVisible(true);
			_deletePart.setVisible(true);
			_addTire.setVisible(false);
			_editTire.setVisible(false);
			_deleteTire.setVisible(false);
		}
		else {
			_addPart.setVisible(false);
			_editPart.setVisible(false);
			_deletePart.setVisible(false);
			_addTire.setVisible(true);
			_editTire.setVisible(true);
			_deleteTire.setVisible(true);
		}
	}

	public TableModel getPartsTableModel(List<Part> articles) {
		Object[][] data = new Object[articles.size()][];
		for (int i = 0; i < articles.size(); ++i) {
			Part article = articles.get(i);
			data[i] = new Object[5];
			data[i][0] = article.getName();
			data[i][1] = article.getProducer().getName();
			data[i][2] = article.getCatalogNumber();
			data[i][3] = article.getGrossPrice();
			data[i][4] = article.getCount();
		}
		return new DefaultTableModel(data, PARTS_COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	public TableModel getTiresTableModel(List<Tire> articles) {
		Object[][] data = new Object[articles.size()][];
		for (int i = 0; i < articles.size(); ++i) {
			Tire article = articles.get(i);
			data[i] = new Object[7];
			data[i][0] = article.getSize();
			data[i][1] = article.getLoadIndex();
			data[i][2] = article.getSpeedIndex();
			data[i][3] = article.getTread().getProducer().getName();
			data[i][4] = article.getTread().getName();
			data[i][5] = article.getGrossPrice();
			data[i][6] = article.getCount();
		}
		return new DefaultTableModel(data, TIRES_COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private TreeModel getTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ArticlesGroup(0, "Magazyn", null, null));
		try {
			DefaultMutableTreeNode parts = new DefaultMutableTreeNode(new ArticlesGroup(ARTICLES_ROOT, "Części", ArticlesGroupType.PARTS, null));
			for (ArticlesGroup group : GroupsService.getInstance().getPartGroups(false)) {
				parts.add(new DefaultMutableTreeNode(group));
			}
			DefaultMutableTreeNode tires = new DefaultMutableTreeNode(new ArticlesGroup(TIRES_ROOT, "Opony", ArticlesGroupType.TIRES, null));
			for (ArticlesGroup group : GroupsService.getInstance().getTireGroups()) {
				tires.add(new DefaultMutableTreeNode(group));
			}
			root.add(parts);
			root.add(tires);
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
		return new DefaultTreeModel(root);
	}

	private boolean isValidGroupSelected() {
		return _currentGroup.getCode() != ARTICLES_ROOT && _currentGroup.getCode() != TIRES_ROOT;
	}

	@Override
	public final void reload() {
		try {
			if (_currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
				_partsPagination.setTableData(PartsService.getInstance().getParts(_currentGroup));
				_articlesTable.setModel(getPartsTableModel(_partsPagination.getCurrentPageData()));
				_navPanel.setCurrentPage(_partsPagination.getCurrentPage());
				_navPanel.setPageCount(_partsPagination.getPageCount());
			}
			else {
				_tiresPagination.setTableData(TiresService.getInstance().getTires(true, _currentGroup));
				_articlesTable.setModel(getTiresTableModel(_tiresPagination.getCurrentPageData()));
				_navPanel.setCurrentPage(_tiresPagination.getCurrentPage());
				_navPanel.setPageCount(_tiresPagination.getPageCount());
			}
		}
		catch (SQLException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
	}

  private void updateSelectedGroup() {
    _currentGroup = (ArticlesGroup) (((DefaultMutableTreeNode) (_articleGroupsTree.getLastSelectedPathComponent())).getUserObject());
  }

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        _groupsPopupMenu = new javax.swing.JPopupMenu();
        _addGroup = new javax.swing.JMenuItem();
        _editGroup = new javax.swing.JMenuItem();
        _deleteGroup = new javax.swing.JMenuItem();
        _popupMenuSeparator = new javax.swing.JPopupMenu.Separator();
        _manageGroupsAttributes = new javax.swing.JMenuItem();
        lTitle = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        _articleGroupsTree = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        _articlesTable = new javax.swing.JTable();
        bBack = new javax.swing.JButton();
        _navPanel = new core.v.PaginationPanel();
        jpArticlesToolbar = new javax.swing.JPanel();
        _addTire = new javax.swing.JLabel();
        _editPart = new javax.swing.JLabel();
        _deletePart = new javax.swing.JLabel();
        _editTire = new javax.swing.JLabel();
        _addPart = new javax.swing.JLabel();
        _deleteTire = new javax.swing.JLabel();

        _addGroup.setText("Dodaj grupę");
        _addGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addGroupActionPerformed(evt);
            }
        });
        _groupsPopupMenu.add(_addGroup);

        _editGroup.setText("Edytuj grupę");
        _editGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _editGroupActionPerformed(evt);
            }
        });
        _groupsPopupMenu.add(_editGroup);

        _deleteGroup.setText("Usuń grupę");
        _deleteGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _deleteGroupActionPerformed(evt);
            }
        });
        _groupsPopupMenu.add(_deleteGroup);
        _groupsPopupMenu.add(_popupMenuSeparator);

        _manageGroupsAttributes.setText("Zarządzaj atrybutami grup");
        _groupsPopupMenu.add(_manageGroupsAttributes);

        setMinimumSize(new java.awt.Dimension(800, 550));

        lTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/64x64/Stacked-Boxes-64.png"))); // NOI18N
        lTitle.setText("  MAGAZYN");

        _articleGroupsTree.setModel(getTreeModel());
        _articleGroupsTree.setRootVisible(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, _groupsPopupMenu, org.jdesktop.beansbinding.ObjectProperty.create(), _articleGroupsTree, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

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
        _articlesTable.setModel(_currentGroup.getType().equals(ArticlesGroupType.PARTS)?getPartsTableModel(_partsPagination.getCurrentPageData()):getTiresTableModel(_tiresPagination.getCurrentPageData()));
        jScrollPane4.setViewportView(_articlesTable);

        bBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Back-48.png"))); // NOI18N
        bBack.setToolTipText("Wróć do głównego menu");
        bBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBackActionPerformed(evt);
            }
        });

        _addTire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Wheel-Add-48.png"))); // NOI18N
        _addTire.setToolTipText("Dodaj nową oponę");
        _addTire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _addTireMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _addTireMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _addTireMouseExited(evt);
            }
        });

        _editPart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Engine-Edit-48.png"))); // NOI18N
        _editPart.setToolTipText("Edytuj wybraną część");
        _editPart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _editPartMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _editPartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _editPartMouseExited(evt);
            }
        });

        _deletePart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Engine-Delete-48.png"))); // NOI18N
        _deletePart.setToolTipText("Usuń wybraną część");
        _deletePart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _deletePartMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _deletePartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _deletePartMouseExited(evt);
            }
        });

        _editTire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Wheel-Edit-48.png"))); // NOI18N
        _editTire.setToolTipText("Edytuj wybraną oponę");
        _editTire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _editTireMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _editTireMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _editTireMouseExited(evt);
            }
        });

        _addPart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Engine-Add-48.png"))); // NOI18N
        _addPart.setToolTipText("Dodaj nową część");
        _addPart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _addPartMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _addPartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _addPartMouseExited(evt);
            }
        });

        _deleteTire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Wheel-Delete-48.png"))); // NOI18N
        _deleteTire.setToolTipText("Usuń wybraną oponę");
        _deleteTire.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _deleteTireMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                _deleteTireMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                _deleteTireMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpArticlesToolbarLayout = new javax.swing.GroupLayout(jpArticlesToolbar);
        jpArticlesToolbar.setLayout(jpArticlesToolbarLayout);
        jpArticlesToolbarLayout.setHorizontalGroup(
            jpArticlesToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpArticlesToolbarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_addPart)
                .addGap(18, 18, 18)
                .addComponent(_editPart)
                .addGap(18, 18, 18)
                .addComponent(_deletePart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_addTire, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_editTire, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_deleteTire))
        );
        jpArticlesToolbarLayout.setVerticalGroup(
            jpArticlesToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpArticlesToolbarLayout.createSequentialGroup()
                .addGroup(jpArticlesToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_deleteTire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_editTire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_addPart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_editPart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_addTire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_deletePart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpArticlesToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bBack))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpArticlesToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bBack, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

  private void bBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bBackActionPerformed
  {//GEN-HEADEREND:event_bBackActionPerformed
	  ViewManager.getInstance().openView(new MainMenuView());
  }//GEN-LAST:event_bBackActionPerformed

  private void _addGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__addGroupActionPerformed
  {//GEN-HEADEREND:event__addGroupActionPerformed
	  ViewManager.getInstance().showDialog(new AddEditArticlesGroupDialog(true, this, _currentGroup.getType()));
  }//GEN-LAST:event__addGroupActionPerformed

  private void _articleGroupsTreeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__articleGroupsTreeMouseClicked
  {//GEN-HEADEREND:event__articleGroupsTreeMouseClicked
	  int previous = _currentGroup.getCode();
    updateSelectedGroup();
    if(_currentGroup.getCode()!=previous) {
      refreshPopupMenu();
      refresArticlesToolbar();
      reload();
    }
  }//GEN-LAST:event__articleGroupsTreeMouseClicked

  private void _editGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__editGroupActionPerformed
  {//GEN-HEADEREND:event__editGroupActionPerformed
	  ViewManager.getInstance().showDialog(new AddEditArticlesGroupDialog(true, this, _currentGroup));
  }//GEN-LAST:event__editGroupActionPerformed

  private void _editTireMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editTireMouseClicked
  {//GEN-HEADEREND:event__editTireMouseClicked
	  if (_articlesTable.getSelectedRow() >= 0 && _currentGroup.getType().equals(ArticlesGroupType.TIRES)) {
		  try {
			  Tire tire = TiresService.getInstance().getTire(_tiresPagination.getCurrentPageData().get(_articlesTable.getSelectedRow()).getId());
			  ViewManager.getInstance().showDialog(new AddEditTireDialog(true, this, tire));
		  }
		  catch (SQLException ex) {
			  ErrorHandler.getInstance().reportError(ex);
		  }
	  }
  }//GEN-LAST:event__editTireMouseClicked

  private void _editTireMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editTireMouseEntered
  {//GEN-HEADEREND:event__editTireMouseEntered
	  if (_articlesTable.getSelectedRow() >= 0) {
		  setCursor(new Cursor(Cursor.HAND_CURSOR));
	  }
  }//GEN-LAST:event__editTireMouseEntered

  private void _editTireMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editTireMouseExited
  {//GEN-HEADEREND:event__editTireMouseExited
	  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__editTireMouseExited

  private void _addPartMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addPartMouseClicked
  {//GEN-HEADEREND:event__addPartMouseClicked
	  if (AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS) && _currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
		  ViewManager.getInstance().showDialog(new AddEditPartDialog(true, this, _currentGroup));
	  }
  }//GEN-LAST:event__addPartMouseClicked

  private void _addPartMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addPartMouseEntered
  {//GEN-HEADEREND:event__addPartMouseEntered
	  if(AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS))
      setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__addPartMouseEntered

  private void _addPartMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addPartMouseExited
  {//GEN-HEADEREND:event__addPartMouseExited
	  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__addPartMouseExited

  private void _deleteTireMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteTireMouseClicked
  {//GEN-HEADEREND:event__deleteTireMouseClicked
    if (_articlesTable.getSelectedRow() >= 0 && _currentGroup.getType().equals(ArticlesGroupType.TIRES) &&
      AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS)) {
      try {
        Tire tire = TiresService.getInstance().getTire(_tiresPagination.getCurrentPageData().get(_articlesTable.getSelectedRow()).getId());
			  boolean removeTire = false;
        String title = "Usuń oponę";
        String message;
        if(tire.getCount()>0) {
          message = "Czy jesteś pewien, że chcesz usunąć oponę, która wciąż znajduje się na magazynie w ilości "+tire.getCount()+" szt.?";
          switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, new String[] {"Tak", "Nie"}, "Nie")) {
            case 0: //Tak
              removeTire = true;
              break;
            case 1:
              removeTire = false;
              break;
          }
        } else {
          message = "Czy jesteś pewien, że chcesz usunąć wybraną oponę?";
          switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, new String[] {"Tak", "Nie"}, "Nie")) {
            case 0: //Tak
              removeTire = true;
              break;
            case 1:
              removeTire = false;
              break;
          }
        }
        if(removeTire) {
          TiresService.getInstance().deleteTire(tire);
          JOptionPane.showMessageDialog(this, "Dane opony zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
          reload();
        }
      } catch(SQLException ex) {
        ErrorHandler.getInstance().reportError(ex);
      }
    }
  }//GEN-LAST:event__deleteTireMouseClicked

  private void _deleteTireMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteTireMouseEntered
  {//GEN-HEADEREND:event__deleteTireMouseEntered
	  if (_articlesTable.getSelectedRow() >= 0 && AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS)) {
		  setCursor(new Cursor(Cursor.HAND_CURSOR));
	  }
  }//GEN-LAST:event__deleteTireMouseEntered

  private void _deleteTireMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deleteTireMouseExited
  {//GEN-HEADEREND:event__deleteTireMouseExited
	  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__deleteTireMouseExited

  private void _editPartMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editPartMouseEntered
  {//GEN-HEADEREND:event__editPartMouseEntered
	  if (_articlesTable.getSelectedRow() >= 0) {
		  setCursor(new Cursor(Cursor.HAND_CURSOR));
	  }
  }//GEN-LAST:event__editPartMouseEntered

  private void _editPartMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editPartMouseExited
  {//GEN-HEADEREND:event__editPartMouseExited
	  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__editPartMouseExited

  private void _editPartMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__editPartMouseClicked
  {//GEN-HEADEREND:event__editPartMouseClicked
	  if (_articlesTable.getSelectedRow() >= 0 && _currentGroup.getType().equals(ArticlesGroupType.PARTS)) {
		  try {
			  Part part = PartsService.getInstance().getPart(_partsPagination.getCurrentPageData().get(_articlesTable.getSelectedRow()).getId());
			  ViewManager.getInstance().showDialog(new AddEditPartDialog(true, this, part));
		  }
		  catch (SQLException ex) {
			  ErrorHandler.getInstance().reportError(ex);
		  }
	  }
  }//GEN-LAST:event__editPartMouseClicked

  private void _deletePartMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deletePartMouseEntered
  {//GEN-HEADEREND:event__deletePartMouseEntered
	  if (_articlesTable.getSelectedRow() >= 0 && AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS)) {
		  setCursor(new Cursor(Cursor.HAND_CURSOR));
	  }
  }//GEN-LAST:event__deletePartMouseEntered

  private void _deletePartMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deletePartMouseExited
  {//GEN-HEADEREND:event__deletePartMouseExited
	  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__deletePartMouseExited

  private void _deletePartMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__deletePartMouseClicked
  {//GEN-HEADEREND:event__deletePartMouseClicked
		if(_articlesTable.getSelectedRow() >= 0 && _currentGroup.getType().equals(ArticlesGroupType.PARTS) &&
      AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS)) {
      try {
        String title = "Usunięcie części";
        Part part = PartsService.getInstance().getPart(_partsPagination.getCurrentPageData().get(_articlesTable.getSelectedRow()).getId());
        switch(JOptionPane.showOptionDialog(this, "Czy na pewno chcesz usunąć część " + part.getName(), title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] {"Tak", "Nie"}, "Nie")) {
          case 0:
            PartsService.getInstance().deletePart(part);
            JOptionPane.showMessageDialog(this, "Dane części zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
            reload();
            break;
        }
      }
      catch (SQLException ex) {
        ErrorHandler.getInstance().reportError(ex);
      }
    }
  }//GEN-LAST:event__deletePartMouseClicked

  private void _addTireMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addTireMouseEntered
  {//GEN-HEADEREND:event__addTireMouseEntered
	  if(AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS))
      setCursor(new Cursor(Cursor.HAND_CURSOR));
  }//GEN-LAST:event__addTireMouseEntered

  private void _addTireMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addTireMouseExited
  {//GEN-HEADEREND:event__addTireMouseExited
	  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }//GEN-LAST:event__addTireMouseExited

  private void _addTireMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__addTireMouseClicked
  {//GEN-HEADEREND:event__addTireMouseClicked
	  if(AuthenticationService.getInstance().getLoggedInUser().getJob().equals(WorkerPosition.BOSS) && _currentGroup.getType().equals(ArticlesGroupType.TIRES)) {
      ViewManager.getInstance().showDialog(new AddEditTireDialog(true, this, _currentGroup));
    }
  }//GEN-LAST:event__addTireMouseClicked

    private void _deleteGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__deleteGroupActionPerformed
		try {
			boolean removeGroup = false;
			String message;
			final String title = "Usuń grupę towarową";
			final int removability = GroupsService.getInstance().checkRemovabilityGroup(_currentGroup);
			if (removability > 0) {
				message = "Czy na pewno chcesz usunąć grupę towarową \"" + _currentGroup + "\"?";
				switch (JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"OK", "Anuluj"}, "OK")) {
					case 0: //OK
						removeGroup = true;
						break;
					case 1: //Cancel
						removeGroup = false;
						break;
				}
			}
			else if (removability < 0) {
				message = "Grupa towarowa " + _currentGroup.getName() + " nie może zostać usunięta, ponieważ zawiera ";
				message += (_currentGroup.getType() == ArticlesGroupType.PARTS) ? "części" : "opony";
				message += "znajdujące się na magazynie";
				JOptionPane.showOptionDialog(this, message, title, JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"OK"}, "OK");
				removeGroup = false;
			}
			else {
				message = "Usunięcie grupy towarowej spowoduje usunięcie powiązanych z nią ";
				message += (_currentGroup.getType() == ArticlesGroupType.PARTS) ? "części" : "opon";
				message += ".\nCzy chcesz kontynuować?";
				switch(JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, new String[] {"Tak", "Nie"}, "Nie")) {
					case 0: //Tak
						removeGroup = true;
						break;
					case 1:
						removeGroup = false;
						break;
				}
			}
			if(removeGroup) {
				GroupsService.getInstance().removeArticlesGroup(_currentGroup);
				JOptionPane.showMessageDialog(this, "Dane grupy towarowe zostały pomyślnie usunięte!", title, JOptionPane.INFORMATION_MESSAGE);
        reload();
			}
		}
		catch (SQLException | DatabaseException ex) {
			ErrorHandler.getInstance().reportError(ex);
		}
    }//GEN-LAST:event__deleteGroupActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem _addGroup;
    private javax.swing.JLabel _addPart;
    private javax.swing.JLabel _addTire;
    private javax.swing.JTree _articleGroupsTree;
    private javax.swing.JTable _articlesTable;
    private javax.swing.JMenuItem _deleteGroup;
    private javax.swing.JLabel _deletePart;
    private javax.swing.JLabel _deleteTire;
    private javax.swing.JMenuItem _editGroup;
    private javax.swing.JLabel _editPart;
    private javax.swing.JLabel _editTire;
    private javax.swing.JPopupMenu _groupsPopupMenu;
    private javax.swing.JMenuItem _manageGroupsAttributes;
    private core.v.PaginationPanel _navPanel;
    private javax.swing.JPopupMenu.Separator _popupMenuSeparator;
    private javax.swing.JButton bBack;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel jpArticlesToolbar;
    private javax.swing.JLabel lTitle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
