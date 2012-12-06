package stores.articles.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import stores.groups.c.GroupsService;
import stores.producers.c.ProducersService;
import stores.articles.m.Tire;
import stores.articles.m.TireSize;
import stores.articles.m.Tread;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;

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
    tire.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
    tire.setTread(getTread(result.getInt(3)));
    tire.setSize(getTireSize(result.getInt(4)));
    tire.setLoadIndex(result.getDouble(5));
    tire.setSpeedIndex(result.getString(6));
    tire.setMargin(result.getDouble(7));
    tire.setGrossPrice(result.getDouble(8));

    return tire;
  }

  public List<Tire> getTires(ArticlesGroup group) throws SQLException {
    String sGroupsCondition = "";
    if(group!=null && group.getCode()>0) {
      sGroupsCondition+=" AND KodGrupyTowarowej="+group.getCode();
    }
    String sQuery = "SELECT * FROM Opony LEFT JOIN GrupyTowarowe ON Opony.KodGrupyTowarowej=GrupyTowarowe.KodGrupy "
      + "WHERE Zawartosc='o'"+sGroupsCondition+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);

    List<Tire> tires = new ArrayList<>();
    for(ResultRow result : results) {
      Tire tire = new Tire();
      tire.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
      tire.setTread(getTread(result.getInt(3)));
      tire.setSize(getTireSize(result.getInt(4)));
      tire.setLoadIndex(result.getDouble(5));
      tire.setSpeedIndex(result.getString(6));
      tire.setMargin(result.getDouble(7));
      tire.setGrossPrice(result.getDouble(8));
      tires.add(tire);
    }

    return tires;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="TIRE SIZE methods">
  public TireSize getTireSize(int tireSizeId) throws SQLException {
    String sQuery="SELECT * FROM RozmiaryOpon WHERE IdRozmiaru="+tireSizeId+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tire size with given ID does not exist!");
		}
		ResultRow result = results.get(0);

    TireSize tireSize = new TireSize();
    tireSize.setId(tireSizeId);
    tireSize.setWidth(result.getString(2));
    tireSize.setProfile(result.getString(3));
    tireSize.setDiameter(result.getString(4));

    return tireSize;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="TREAD methods">
  public Tread getTread(int treadId) throws SQLException {
    String sQuery="SELECT * FROM Biezniki WHERE IdBieznika="+treadId+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tread with given ID does not exist!");
		}
		ResultRow result = results.get(0);

    Tread tread = new Tread();
    tread.setId(treadId);
    tread.setProducer(ProducersService.getInstance().getProducer(result.getInt(2)));
    tread.setName(result.getString(3));

    return tread;
  }
  // </editor-fold>
}
