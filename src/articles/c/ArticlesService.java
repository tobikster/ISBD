package articles.c;

import articles.c.validators.ArticleAttributeValidator;
import articles.c.validators.ArticlesGroupValidator;
import articles.m.Article;
import articles.m.ArticleAttribute;
import articles.m.ArticlesGroup;
import articles.m.Producer;
import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
	//<editor-fold defaultstate="collapsed" desc="ATTRIBUTES method">

	public boolean addAttribute(ArticleAttribute attribute) throws DatabaseException, SQLException {
		boolean result = false;
		ArticleAttributeValidator validator = new ArticleAttributeValidator();
		if (validator.validate(attribute)) {
			String query = "INSERT INTO AtrybutyCzesci (Nazwa) "
					+ "VALUES ('" + attribute.getName() + "');";
			DatabaseManager.getInstance().executeQuery(query);
			result = true;
		}
		return result;
	}
	//</editor-fold>
	// <editor-fold defaultstate="collapsed" desc="DOT methods">
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="GROUPS methods">
	public ArticlesGroup getArticleGroup(int iGroupCode) throws SQLException {
		String sQuery = "SELECT KodGrupy, Nazwa, VAT, Stawka FROM GrupyTowarowe "
				+ "INNER JOIN StawkiVAT ON GrupyTowarowe.VAT=StawkiVAT.IdStawki WHERE KodGrupy=" + iGroupCode + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Article group with given CODE does not exist!");
		}
		ResultRow result = results.get(0);

		ArticlesGroup group = new ArticlesGroup();
		group.setCode(result.getInt(1));
		group.setName(result.getString(2));
		VATRate vat = new VATRate();
		vat.setId(result.getInt(3));
		vat.setRate(result.getDouble(4));
		group.setVat(vat);

		return group;
	}

	public List<ArticleAttribute> getArticlesGroupAttributes(int groupCode) throws SQLException {
		List<ArticleAttribute> result = new LinkedList<>();
		String query = "SELECT AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyCzesci, AtrybutyGrupTowarowych "
				+ "WHERE AtrybutyCzesci.IdAtrybutu = AtrybutyGrupTowarowych.IdAtrybutu AND AtrybutyGrupTowarowych.KodGrupy = " + groupCode + ";";
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);
		for (ResultRow queryResult : results) {
			result.add(new ArticleAttribute(queryResult.getInt(1), queryResult.getString(2)));
		}
		return result;
	}

	public List<ArticleAttribute> getAvailableAttributes(int groupCode) throws SQLException {
		List<ArticleAttribute> result = new LinkedList<>();
		String query = "SELECT AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyCzesci, AtrybutyGrupTowarowych "
				+ "WHERE AtrybutyCzesci.IdAtrybutu = AtrybutyGrupTowarowych.IdAtrybutu AND NOT AtrybutyGrupTowarowych.KodGrupy = " + groupCode + ";";
		query = "SELECT IdAtrybutu, Nazwa "
				+ "FROM AtrybutyCzesci;";
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);
		for (ResultRow row : results) {
			result.add(new ArticleAttribute(row.getInt(1), row.getString(2)));
		}
		return result;
	}

	public boolean addArticlesGroup(ArticlesGroup group) throws DatabaseException, SQLException {
		boolean result = false;
		ArticlesGroupValidator validator = new ArticlesGroupValidator();
		if (validator.validate(group)) {
			DatabaseManager.getInstance().startTransaction();
			String query = "INSERT INTO GrupyTowarowe (Nazwa, VAT) "
					+ "VALUES('" + group.getName() + "', " + group.getVat().getId() + ");";
			DatabaseManager.getInstance().executeQuery(query);

			query = "SELECT KodGrupy FROM GrupyTowarowe WHERE Nazwa = '" + group.getName() + "' AND VAT = " + group.getVat().getId() + ";";
			List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);
			if (results.size() != 1) {
			}
			else {
				group.setId(results.get(0).getInt(1));
				for (ArticleAttribute attribute : group.getAttributes()) {
					query = "INSERT INTO AtrybutyGrupTowarowych (KodGrupy, IdAtrybutu) "
							+ "VALUES (" + group.getId() + ", " + attribute.getId() + ");";
					DatabaseManager.getInstance().executeQuery(query);
				}
				result = true;
			}
			if (result) {
				DatabaseManager.getInstance().commitTransaction();
			}
			else {
				DatabaseManager.getInstance().rollbackTransaction();
			}
		}
		return result;
	}
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
