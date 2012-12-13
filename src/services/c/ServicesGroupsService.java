package services.c;

import core.c.DatabaseManager;
import core.c.EntityValidator;
import core.m.DatabaseException;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.c.validators.ServicesGroupValidator;
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
    String sQuery = "SELECT * FROM DzialyUslug INNER JOIN StawkiVAT ON DzialyUslug.VAT=StawkiVAT.IdStawki "
      + "ORDER BY Nazwa ASC;";
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
  
  public void addServicesGroup(ServicesGroup group) throws DatabaseException, SQLException {
    EntityValidator<ServicesGroup> validator = new ServicesGroupValidator();
    validator.validate(group);

    String sQuery = "INSERT INTO DzialyUslug(Nazwa, VAT) VALUES ('"+group.getName()+"', "+group.getVat().getId()+")";
    DatabaseManager.getInstance().executeQuery(sQuery);
  }

  public void updateServicesGroup(ServicesGroup group) throws DatabaseException, SQLException {
    EntityValidator<ServicesGroup> validator = new ServicesGroupValidator();
    validator.validate(group);

    String sQuery = "UPDATE DzialyUslug SET Nazwa='"+group.getName()+"', VAT="+group.getVat().getId()+" WHERE KodDzialu="+group.getId()+";";
    DatabaseManager.getInstance().executeQuery(sQuery);
  }

  public void deleteServicesGroup(ServicesGroup group) throws SQLException {
    String sQuery = "DELETE FROM DzialyUslug WHERE KodDzialu="+group.getId()+";";
    DatabaseManager.getInstance().executeQuery(sQuery);
  }

  public int checkRemovabilityGroup(ServicesGroup group) throws SQLException {
    String sQuery = "SELECT COUNT(Uslugi.KodDzialu), COUNT(WykonaneUslugi.IdUslugi) "
      + "FROM Uslugi LEFT JOIN WykonaneUslugi ON Uslugi.IdUslugi=WykonaneUslugi.IdUslugi "
      + "HAVING Uslugi.KodDzialu="+group.getId()+";";
    
    ResultRow rr = DatabaseManager.getInstance().executeQueryResult(sQuery).get(0);
    int iServicesCount = rr.getInt(1);
    int iProvidedServicesCount = rr.getInt(2);

    if(iServicesCount==0 && iProvidedServicesCount==0)
      return 1;
    else if(iServicesCount>0 && iProvidedServicesCount==0)
      return 0;
    else
      return -1;
  }
  // </editor-fold>
}
