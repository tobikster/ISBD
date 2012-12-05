package stores.tires.c;

public class TiresService
{
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static TiresService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final TiresService p_instance = new TiresService();
  }
  // </editor-fold>

  private TiresService()
  {

  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="TIRE methods">
  
  // </editor-fold>
}
