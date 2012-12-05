package stores.parts.v;

import stores.groups.v.AddArticlesGroupDialog;
import stores.groups.v.EditArticlesGroupDialog;
import stores.parts.c.ArticlesService;
import stores.parts.m.Article;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;
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
import javax.swing.tree.TreeModel;
import stores.groups.c.GroupsService;

/**
 *
 * @author MRKACZOR
 */
public class ArticleListView extends javax.swing.JPanel implements Reloadable
{
  public static final int ROWS_PER_PAGE=5;
  public static final String[] COLUMN_NAMES=
  {
    "Nazwa",
    "Producent",
    "Numer katalogowy"
  };
  private static final int ARTICLES_ROOT = -1;
  private static final int TIRES_ROOT = -2;
  private TablePagination<Article> _pagination;

  /**
   * Creates new form ArticleListView
   */
  public ArticleListView()
  {
    _pagination=new TablePagination<>(new ArrayList<Article>(), ROWS_PER_PAGE);
    initComponents();
    refreshPopupMenu();
    reload();
    _navPanel.setButtonsListener(new PaginationPanel.ButtonsListener()
    {
      @Override
      public void nextButtonClicked()
      {
        _articlesTable.setModel(getTableModel(_pagination.getNextPage()));
        _navPanel.setCurrentPage(_pagination.getCurrentPage());
      }

      @Override
      public void previousButtonClicked()
      {
        _articlesTable.setModel(getTableModel(_pagination.getPreviousPage()));
        _navPanel.setCurrentPage(_pagination.getCurrentPage());
      }

      @Override
      public void firstButtonClicked()
      {
        _articlesTable.setModel(getTableModel(_pagination.getFirstPage()));
        _navPanel.setCurrentPage(_pagination.getCurrentPage());
      }

      @Override
      public void lastButtonClicked()
      {
        _articlesTable.setModel(getTableModel(_pagination.getLastPage()));
        _navPanel.setCurrentPage(_pagination.getCurrentPage());
      }
    });
  }

  private void refreshPopupMenu() {
    _editGroup.setEnabled(isValidGroupSelected());
    _deleteGroup.setEnabled(isValidGroupSelected());
  }

  public TableModel getTableModel(List<Article> articles)
  {
    Object[][] data=new Object[articles.size()][];
    for(int i=0; i<articles.size(); ++i)
    {
      Article article=articles.get(i);
      data[i]=new Object[3];
      data[i][0]=article.getName();
      data[i][1]=article.getProducer().getName();
      data[i][2]=article.getCatalogNumber();
    }
    return new DefaultTableModel(data, COLUMN_NAMES)
    {
      @Override
      public boolean isCellEditable(int row, int column)
      {
        return false;
      }
    };
  }
  
  private TreeModel getTreeModel()
  {
    try {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ArticlesGroup(0, "Magazyn", null, null));
      DefaultMutableTreeNode articles = new DefaultMutableTreeNode(new ArticlesGroup(ARTICLES_ROOT, "Części", ArticlesGroupType.PARTS, null));
      for(ArticlesGroup group : GroupsService.getInstance().getRootArticleGroups())
      {
        articles.add(loadGroupsTree(group));
      }
      DefaultMutableTreeNode tires = new DefaultMutableTreeNode(new ArticlesGroup(TIRES_ROOT, "Opony", ArticlesGroupType.TIRES, null));
      for(ArticlesGroup group : GroupsService.getInstance().getRootArticleGroups())
      {
        tires.add(loadGroupsTree(group));
      }
      root.add(articles);
      root.add(tires);
      return new javax.swing.tree.DefaultTreeModel(root);
    } catch(SQLException ex) {
      ErrorHandler.getInstance().reportError(ex);
    }
    return null;
  }
  
  private DefaultMutableTreeNode loadGroupsTree(ArticlesGroup start) throws SQLException
  {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(start);
    for (ArticlesGroup subgroup : GroupsService.getInstance().getArticleSubgroups(start)) {
      node.add(loadGroupsTree(subgroup));
    }
    return node;
  }
  
  private ArticlesGroup getSelectedGroup()
  {
//    ArticlesGroup selectedGroup = (ArticlesGroup)(((DefaultMutableTreeNode)(_articleGroupsTree.getLastSelectedPathComponent())).getUserObject());
//    if(selectedGroup.getCode()==ARTICLES_ROOT || selectedGroup.getCode()==TIRES_ROOT)
//      return null;
    return (ArticlesGroup)(((DefaultMutableTreeNode)(_articleGroupsTree.getLastSelectedPathComponent())).getUserObject());
  }

  private boolean isValidGroupSelected() {
    ArticlesGroup group = getSelectedGroup();
    return group.getCode()!=ARTICLES_ROOT && group.getCode()!=TIRES_ROOT;
  }

  @Override
  public final void reload()
  {
    try {
      _pagination.setTableData(ArticlesService.getInstance().getArticles(null));
      _articlesTable.setModel(getTableModel(_pagination.getCurrentPageData()));
      _navPanel.setCurrentPage(_pagination.getCurrentPage());
      _navPanel.setPageCount(_pagination.getPageCount());
    } catch(SQLException ex) {
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
        jButton1 = new javax.swing.JButton();

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
        _groupsPopupMenu.add(_deleteGroup);
        _groupsPopupMenu.add(_popupMenuSeparator);

        _manageGroupsAttributes.setText("Zarządzaj atrybutami grup");
        _groupsPopupMenu.add(_manageGroupsAttributes);

        lTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/64x64/Stacked-Boxes-64.png"))); // NOI18N
        lTitle.setText("  MAGAZYN");

        _articleGroupsTree.setModel(getTreeModel());
        _articleGroupsTree.setRootVisible(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, _groupsPopupMenu, org.jdesktop.beansbinding.ObjectProperty.create(), _articleGroupsTree, org.jdesktop.beansbinding.BeanProperty.create("componentPopupMenu"));
        bindingGroup.addBinding(binding);

        _articleGroupsTree.setSelectionRow(0);
        _articleGroupsTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _articleGroupsTreeMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(_articleGroupsTree);

        _articlesTable.setColumnSelectionAllowed(false);
        _articlesTable.setShowVerticalLines(false);
        _articlesTable.setModel(getTableModel(_pagination.getCurrentPageData()));
        jScrollPane4.setViewportView(_articlesTable);

        bBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48x48/Back-48.png"))); // NOI18N
        bBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBackActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(77, 77, 77))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bBack))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(_navPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTitle)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
    ViewManager.getInstance().showDialog(new AddArticlesGroupDialog(true, this, getSelectedGroup()));
  }//GEN-LAST:event__addGroupActionPerformed

  private void _articleGroupsTreeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event__articleGroupsTreeMouseClicked
  {//GEN-HEADEREND:event__articleGroupsTreeMouseClicked
    refreshPopupMenu();
  }//GEN-LAST:event__articleGroupsTreeMouseClicked

  private void _editGroupActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event__editGroupActionPerformed
  {//GEN-HEADEREND:event__editGroupActionPerformed
    ViewManager.getInstance().showDialog(new EditArticlesGroupDialog(true, this, getSelectedGroup()));
  }//GEN-LAST:event__editGroupActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		ViewManager.getInstance().showDialog(new AddArticleDialog(true, this));
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem _addGroup;
    private javax.swing.JTree _articleGroupsTree;
    private javax.swing.JTable _articlesTable;
    private javax.swing.JMenuItem _deleteGroup;
    private javax.swing.JMenuItem _editGroup;
    private javax.swing.JPopupMenu _groupsPopupMenu;
    private javax.swing.JMenuItem _manageGroupsAttributes;
    private core.v.PaginationPanel _navPanel;
    private javax.swing.JPopupMenu.Separator _popupMenuSeparator;
    private javax.swing.JButton bBack;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lTitle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
