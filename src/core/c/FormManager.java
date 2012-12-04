package core.c;

import java.util.Arrays;


public class FormManager
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static FormManager getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final FormManager p_instance = new FormManager();
  }
  // </editor-fold>

  private FormManager()
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
  public boolean passwordMatches(char[] password, char[] repeat){
    return Arrays.equals(password, repeat);
  }
  // </editor-fold>
}
