package articles.c;

import articles.m.Article;
import articles.m.ArticlesGroup;
import articles.m.Producer;
import core.c.DatabaseManager;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.List;

public class ArticlesService
{
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

  // <editor-fold defaultstate="collapsed" desc="ARTICLES methods">
  public Article getArticle(int iArticleId) throws SQLException {
    String sQuery = "SELECT * FROM Czesci WHERE IdCzesci="+iArticleId+";";
    
    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    if(results.isEmpty())
      throw new SQLException("Article with given ID does not exist!");
    ResultRow result=results.get(0);
    
    Article article = new Article();
    article.setId(result.getInt(1));
    article.setGroup(getArticleGroup(result.getInt(2)));
    article.setProducer(getProducer(result.getInt(3)));
    article.setCatalogNumber(result.getString(4));
    article.setName(result.getString(5));
    article.setMargin(result.getDouble(6));
    article.setGrossPrice(result.getDouble(7));
    article.setCount(result.getFloat(8));
    //TODO load image for article item

    return article;
  }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="DOT methods">

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="GROUPS methods methods">
  public ArticlesGroup getArticleGroup(int iGroupCode) throws SQLException {
    String sQuery = "SELECT KodGrupy, Nazwa, VAT, Stawka FROM GrupyTowarowe "
      + "INNER JOIN StawkiVAT ON GrupyTowarowe.VAT=StawkiVAT.IdStawki WHERE KodGrupy="+iGroupCode+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    if(results.isEmpty())
      throw new SQLException("Article group with given CODE does not exist!");
    ResultRow result=results.get(0);
    
    ArticlesGroup group = new ArticlesGroup();
    group.setCode(result.getInt(1));
    group.setName(result.getString(2));
    VATRate vat = new VATRate();
    vat.setId(result.getInt(3));
    vat.setRate(result.getDouble(4));
    group.setVat(vat);
    
    return group;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="PRODUCER methods">
  public Producer getProducer(int iProducerId) throws SQLException {
    String sQuery = "SELECT * FROM Producenci WHERE IdProducenta="+iProducerId+";";

    List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    if(results.isEmpty())
      throw new SQLException("Producer with given ID does not exist!");
    ResultRow result=results.get(0);

    Producer producer = new Producer();
    producer.setId(result.getInt(1));
    producer.setName(result.getString(2));
    //TODO load producer logo from database

    return producer;
  }
  // </editor-fold>
}
