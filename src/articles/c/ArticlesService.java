package articles.c;

public class ArticlesService
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ArticlesService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ArticlesService p_instance = new ArticlesService();
  }
  // </editor-fold>

  private ArticlesService()
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

  // </editor-fold>
}
