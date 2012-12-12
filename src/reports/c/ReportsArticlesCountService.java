package reports.c;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import stores.articles.c.TiresService;
import stores.articles.m.Tire;
import stores.groups.m.ArticlesGroup;

public class ReportsArticlesCountService
{
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ReportsArticlesCountService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ReportsArticlesCountService p_instance = new ReportsArticlesCountService();
  }
  // </editor-fold>

  private ReportsArticlesCountService()
  {

  }
  // </editor-fold>

  public Object[][] getTiresDataForReport(Date forDate, boolean excludeZeroStates) throws SQLException {
    List<Tire> results = TiresService.getInstance().getTires(null);
    int iterator = 0;
    
    if(excludeZeroStates) {
      for(Tire tire : results) {
        if(tire.getCount()>0)
          iterator++;
      }
    } else {
      iterator = results.size();
    }
    
    Object articles[][] = new Object[iterator][4];
    iterator=0;
    
    for(Tire tire : results) {
      if(!excludeZeroStates || (excludeZeroStates && tire.getCount()>0)) {
        articles[iterator][0]=tire.getFullSize();
        articles[iterator][1]=tire.getTread().getProducer().toString();
        articles[iterator][2]=tire.getTread().toString();
        articles[iterator][3]=tire.getCount();
        iterator++;
      }
    }
    
    return articles;
  }
  
  public Object[][] getTiresDataForReport(Date forDate, boolean excludeZeroStates, ArticlesGroup group) throws SQLException {
    List<Tire> results = TiresService.getInstance().getTires(group);
    int iterator = 0;
    
    if(excludeZeroStates) {
      for(Tire tire : results) {
        if(tire.getCount()>0)
          iterator++;
      }
    } else {
      iterator = results.size();
    }
    
    Object articles[][] = new Object[iterator][4];
    iterator=0;
    
    for(Tire tire : results) {
      if(!excludeZeroStates || (excludeZeroStates && tire.getCount()>0)) {
        articles[iterator][0]=tire.getFullSize();
        articles[iterator][1]=tire.getTread().getProducer().toString();
        articles[iterator][2]=tire.getTread().toString();
        articles[iterator][3]=tire.getCount();
        iterator++;
      }
    }
    
    return articles;
  }
}
