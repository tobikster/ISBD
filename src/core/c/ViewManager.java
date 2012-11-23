package core.c;

import core.v.MainWindow;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class ViewManager {
	// <editor-fold defaultstate="collapsed" desc="Object variables">
	private MainWindow _mainWindow;
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static ViewManager getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final ViewManager p_instance = new ViewManager();
	}
	// </editor-fold>

	private ViewManager() {
		_mainWindow = new MainWindow();
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">
	// </editor-fold>
	public void hideMainWindow() {
		_mainWindow.setVisible(false);
	}

	public void openView(JPanel view) {
		_mainWindow.getMainPanel().removeAll();
		_mainWindow.getMainPanel().add(view);
		_mainWindow.getMainPanel().revalidate();
	}

	public void showMainWindow() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		_mainWindow.setLocation((int) ((screenSize.width - _mainWindow.getSize().width) / 2), (int) (screenSize.height - _mainWindow.getSize().height) / 2);
		_mainWindow.setVisible(true);
	}
	// </editor-fold>
}
