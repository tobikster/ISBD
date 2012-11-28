package core.c;

import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author tobikster
 */
public class Main {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
		}
		ViewManager.getInstance().showMainWindow();
	}
}
