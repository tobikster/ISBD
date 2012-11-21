package core.c;

import core.v.MainWindow;
import javax.swing.JPanel;

public class ViewManager
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">
	MainWindow _mainWindow;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ViewManager getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ViewManager p_instance = new ViewManager();
  }
  // </editor-fold>

  private ViewManager()
  {
	 
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
  // <editor-fold defaultstate="collapsed" desc="Getters">

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Setters">

  // </editor-fold>
  public void setView(JPanel panel) {
	  
  }
  // </editor-fold>
}
