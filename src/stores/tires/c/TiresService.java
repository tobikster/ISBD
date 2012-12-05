package stores.tires.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.List;
import stores.articles.c.ArticlesService;
import stores.tires.m.Tire;
import stores.tires.m.Tread;

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
  public Tire getTire(int tireId) throws SQLException {
    String sQuery="SELECT * FROM Opony WHERE IdOpony="+tireId+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tire with given ID does not exist!");
		}
		ResultRow result = results.get(0);

    Tire tire = new Tire();
    tire.setGroup(ArticlesService.getInstance().getArticleGroup(result.getInt(2)));

    return null;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="TREAD methods">
  public Tread getTread(int treadId) throws SQLException {
    String sQuery="SELECT * FROM BieznikiOpon WHERE IdBieznika="+treadId+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tread with given ID does not exist!");
		}
		ResultRow result = results.get(0);
    
    return null;
  }
  // </editor-fold>
}
