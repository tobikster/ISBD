/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workers.v;

import core.c.ErrorHandler;
import core.c.FormManager;
import core.c.Reloadable;
import core.m.ApplicationException;
import core.m.DatabaseException;
import core.v.ApplicationDialog;
import java.sql.SQLException;
import workers.c.WorkersService;
import workers.m.Worker;

/**
 *
 * @author tobikster
 */
public class AddWorkerView extends ApplicationDialog
{
  /**
   * A return status code - returned if Cancel button has been pressed
   */
  public static final int RET_CANCEL=0;
  /**
   * A return status code - returned if OK button has been pressed
   */
  public static final int RET_OK=1;
  private Worker _worker;

  /**
   * Creates new form EditWorkerView
   */
  public AddWorkerView(boolean modal, Reloadable reloadableParent)
  {
    super(modal, reloadableParent);
    _worker=new Worker();
    initComponents();
  }

  /**
   * @return the return status of this dialog - one of RET_OK or RET_CANCEL
   */
  public int getReturnStatus()
  {
    return returnStatus;
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _okButton = new javax.swing.JButton();
        _cancelButton = new javax.swing.JButton();
        _dataPanel = new javax.swing.JPanel();
        _nameLabel = new javax.swing.JLabel();
        _nameTextField = new javax.swing.JTextField();
        _surnameLabel = new javax.swing.JLabel();
        _surnameTextField = new javax.swing.JTextField();
        _jobLabel = new javax.swing.JLabel();
        _jobTextField = new javax.swing.JTextField();
        _loginLabel = new javax.swing.JLabel();
        _loginTextField = new javax.swing.JTextField();
        _passwordField = new javax.swing.JPasswordField();
        _passwordLabel = new javax.swing.JLabel();
        _repeatPasswordLabel = new javax.swing.JLabel();
        _repeatPasswordField = new javax.swing.JPasswordField();
        _titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dodawanie pracownika");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        _okButton.setText("OK");
        _okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _okButtonActionPerformed(evt);
            }
        });

        _cancelButton.setText("Cancel");
        _cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _cancelButtonActionPerformed(evt);
            }
        });

        _nameLabel.setText("Imię:");

        _nameTextField.setText(_worker.getName());

        _surnameLabel.setText("Nazwisko:");

        _surnameTextField.setText(_worker.getSurname());

        _jobLabel.setText("Stanowisko");

        _jobTextField.setText(_worker.getJob());

        _loginLabel.setText("Login:");

        _loginTextField.setText(_worker.getLogin());

        _passwordField.setText(_worker.getPassword());

        _passwordLabel.setText("Hasło:");

        _repeatPasswordLabel.setText("Ponownie:");

        _repeatPasswordField.setText(_worker.getPassword());

        javax.swing.GroupLayout _dataPanelLayout = new javax.swing.GroupLayout(_dataPanel);
        _dataPanel.setLayout(_dataPanelLayout);
        _dataPanelLayout.setHorizontalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_dataPanelLayout.createSequentialGroup()
                        .addComponent(_jobLabel)
                        .addGap(18, 18, 18)
                        .addComponent(_jobTextField))
                    .addGroup(_dataPanelLayout.createSequentialGroup()
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_passwordLabel)
                            .addComponent(_repeatPasswordLabel))
                        .addGap(22, 22, 22)
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_passwordField)
                            .addComponent(_repeatPasswordField)))
                    .addGroup(_dataPanelLayout.createSequentialGroup()
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_surnameLabel)
                            .addComponent(_nameLabel))
                        .addGap(24, 24, 24)
                        .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_surnameTextField)
                            .addComponent(_nameTextField)))
                    .addGroup(_dataPanelLayout.createSequentialGroup()
                        .addComponent(_loginLabel)
                        .addGap(43, 43, 43)
                        .addComponent(_loginTextField)))
                .addContainerGap())
        );
        _dataPanelLayout.setVerticalGroup(
            _dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _dataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_nameLabel)
                    .addComponent(_nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_surnameLabel)
                    .addComponent(_surnameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_jobLabel)
                    .addComponent(_jobTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_loginLabel)
                    .addComponent(_loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_passwordLabel)
                    .addComponent(_passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(_dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_repeatPasswordLabel)
                    .addComponent(_repeatPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _titleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        _titleLabel.setText("Wprowadź dane pracownika");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 182, Short.MAX_VALUE)
                        .addComponent(_okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(_titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(_dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_cancelButton, _okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_cancelButton)
                    .addComponent(_okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(_okButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__okButtonActionPerformed
      doClose(RET_OK);
    }//GEN-LAST:event__okButtonActionPerformed

    private void _cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__cancelButtonActionPerformed
      doClose(RET_CANCEL);
    }//GEN-LAST:event__cancelButtonActionPerformed

  /**
   * Closes the dialog
   */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
      doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

  private void doClose(int retStatus)
  {
    returnStatus=retStatus;
    switch(retStatus)
    {
      case RET_OK:
        try
        {
          _worker.setName(_nameTextField.getText());
          _worker.setSurname(_surnameTextField.getText());
          _worker.setJob(_jobTextField.getText());
          _worker.setLogin(_loginTextField.getText());
          if(!FormManager.getInstance().passwordMatches(_passwordField.getPassword(), _repeatPasswordField.getPassword()))
            throw new ApplicationException("Hasła się różnią!");
          else
            _worker.setPassword(new String(_passwordField.getPassword()));
          WorkersService.getInstance().addWorker(_worker);
          setHaveToReloadParent(true);
          super.close();
        }
        catch(DatabaseException|IllegalArgumentException|SQLException|ApplicationException ex)
        {
          ErrorHandler.getInstance().reportError(ex);
        }
        break;

      case RET_CANCEL:
        super.close();
        break;
    }
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _cancelButton;
    private javax.swing.JPanel _dataPanel;
    private javax.swing.JLabel _jobLabel;
    private javax.swing.JTextField _jobTextField;
    private javax.swing.JLabel _loginLabel;
    private javax.swing.JTextField _loginTextField;
    private javax.swing.JLabel _nameLabel;
    private javax.swing.JTextField _nameTextField;
    private javax.swing.JButton _okButton;
    private javax.swing.JPasswordField _passwordField;
    private javax.swing.JLabel _passwordLabel;
    private javax.swing.JPasswordField _repeatPasswordField;
    private javax.swing.JLabel _repeatPasswordLabel;
    private javax.swing.JLabel _surnameLabel;
    private javax.swing.JTextField _surnameTextField;
    private javax.swing.JLabel _titleLabel;
    // End of variables declaration//GEN-END:variables
  private int returnStatus=RET_CANCEL;
}
