package services.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.m.ServicesGroup;

public class ServicesGroupsService
{
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ServicesGroupsService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ServicesGroupsService p_instance = new ServicesGroupsService();
  }
  // </editor-fold>

  private ServicesGroupsService()
  {

  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="SERVICES GROUP methods">
  public List<ServicesGroup> getServicesGroups() throws SQLException {
    String sQuery = "SELECT * FROM DzialyUslug INNER JOIN StawkiVAT ON DzialyUslug.VAT=StawkiVAT.IdStawki;";
    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    List<ServicesGroup> groups = new ArrayList<>();
    ServicesGroup currentGroup;
    for(ResultRow rr : results) {
      currentGroup = new ServicesGroup();
      currentGroup.setId(rr.getInt(1));
      currentGroup.setName(rr.getString(2));
      VATRate vat = new VATRate();
      vat.setId(rr.getInt(4));
      vat.setRate(rr.getFloat(5));
      currentGroup.setVat(vat);
      groups.add(currentGroup);
    }
    return groups;
  }
  // </editor-fold>
}
