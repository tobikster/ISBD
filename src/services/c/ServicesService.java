package services.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.m.Service;
import services.m.ServicesGroup;

public class ServicesService
{
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ServicesService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ServicesService p_instance = new ServicesService();
  }
  // </editor-fold>

  private ServicesService()
  {

  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="SERVICE methods">
  public List<Service> getServices(ServicesGroup group) throws SQLException {
    String groupCondition="";
    if(group!=null) {
      groupCondition+=" WHERE KodDzialu="+group.getId()+"";
    }
    String sQuery = "SELECT * FROM Uslugi"+groupCondition+";";
    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    List<Service> services = new ArrayList<>();
    Service currentService;
    for(ResultRow rr : results) {
      currentService = new Service();
      currentService.setId(rr.getInt(1));
      currentService.setName(rr.getString(2));
      currentService.setMinPrice(rr.getFloat(3));
      currentService.setMaxPrice(rr.getFloat(4));
      currentService.setGroup(group);
      services.add(currentService);
    }
    return services;
  }
  // </editor-fold>
}
