package stores.parts.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import stores.parts.m.Article;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.Producer;

public class ArticlesService {
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static ArticlesService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final ArticlesService p_instance = new ArticlesService();
	}
	// </editor-fold>

	private ArticlesService() {
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="ARTICLES methods">
	public Article getArticle(int iArticleId) throws SQLException {
		String sQuery = "SELECT * FROM Czesci WHERE IdCzesci=" + iArticleId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Article with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		Article article = new Article();
		article.setId(result.getInt(1));
		article.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
		article.setProducer(getProducer(result.getInt(3)));
		article.setCatalogNumber(result.getString(4));
		article.setName(result.getString(5));
		article.setMargin(result.getDouble(6));
		article.setGrossPrice(result.getDouble(7));
		article.setCount(result.getFloat(8));
		//TODO load image for article item

		return article;
	}

  public List<Article> getArticles(ArticlesGroup group) throws SQLException {
    String sGroupsIds = "";
    if(group!=null) {
      sGroupsIds+=+group.getCode();
      for(Integer id : GroupsService.getInstance().getSubGroupsIds(group.getCode())) {
        sGroupsIds+=", "+id;
      }
    }
		String sQuery = "SELECT * FROM Czesci";
    if(group!=null) {
      sQuery+=" WHERE KodGrupyTowarowej IN (" + sGroupsIds + ")";
    }
    sQuery+=";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    List<Article> articles = new ArrayList<>();
    
    for(ResultRow result : results) {
      Article article = new Article();
      article.setId(result.getInt(1));
      article.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
      article.setProducer(getProducer(result.getInt(3)));
      article.setCatalogNumber(result.getString(4));
      article.setName(result.getString(5));
      article.setMargin(result.getDouble(6));
      article.setGrossPrice(result.getDouble(7));
      article.setCount(result.getFloat(8));
      articles.add(article);
    }
		//TODO load image for article item

		return articles;
	}
	// </editor-fold>
  
	// <editor-fold defaultstate="collapsed" desc="DOT methods">
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="PRODUCER methods">
	public Producer getProducer(int iProducerId) throws SQLException {
		String sQuery = "SELECT * FROM Producenci WHERE IdProducenta=" + iProducerId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Producer with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		Producer producer = new Producer();
		producer.setId(result.getInt(1));
		producer.setName(result.getString(2));
		//TODO load producer logo from database

		return producer;
	}
	// </editor-fold>
}
