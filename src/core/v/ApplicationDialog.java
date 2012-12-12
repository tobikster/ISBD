package core.v;

import core.c.Reloadable;
import core.c.ViewManager;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 *
 * @author tobikster
 */
public abstract class ApplicationDialog extends JDialog {
  // <editor-fold defaultstate="collapsed" desc="Object variables">
	private Reloadable _reloadableParent;
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Creating object">

	public ApplicationDialog(boolean modal, Reloadable parent) {
		super(ViewManager.getInstance().getMainWindow(), modal);
		_reloadableParent = parent;

		// Close the dialog when Esc is pressed
		String cancelName = "cancel";
		InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
		ActionMap actionMap = getRootPane().getActionMap();
		actionMap.put(cancelName, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close(false);
			}
		});

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				close(false);
			}
		});
	}
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">

	public Reloadable getReloadableParent() {
		return _reloadableParent;
	}
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">

	// </editor-fold>

	public void close(boolean reload) {
		ViewManager.getInstance().closeDialog(this, reload);
	}
	// </editor-fold>
}