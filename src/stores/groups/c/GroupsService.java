package stores.groups.c;

import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import stores.parts.c.validators.ArticleAttributeValidator;
import stores.parts.m.ArticleAttribute;
import stores.groups.c.validators.ArticlesGroupValidator;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;

public class GroupsService {
	// <editor-fold defaultstate="collapsed" desc="Object variables">
	// </editor-fold>
	
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static GroupsService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final GroupsService p_instance = new GroupsService();
	}
	// </editor-fold>

	private GroupsService() {
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	// </editor-fold>
	
	// <editor-fold defaultstate="collapsed" desc="Setters">
	// </editor-fold>
	
	public ArticlesGroup getArticleGroup(int iGroupCode) throws SQLException {
		String sQuery = "SELECT KodGrupy, Nazwa, VAT, Stawka, Zawartosc FROM GrupyTowarowe "
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
		switch (result.getString(5)) {
			case "c":
			case "C":
				group.setType(ArticlesGroupType.PARTS);
				break;
			case "o":
			case "O":
				group.setType(ArticlesGroupType.TIRES);
				break;
			default:
				throw new SQLException("Article group with given CODE has unknown type!");
		}

		return group;
	}

	public List<ArticlesGroup> getRootArticleGroups() throws SQLException {
		List rootGroups = new ArrayList<>();
		String sqlQuery = "SELECT KodGrupy FROM GrupyTowarowe WHERE KodGrupy NOT IN (SELECT KodGrupy FROM GrupyNadrzedne);";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			rootGroups.add(getArticleGroup(rr.getInt(1)));
		}
		return rootGroups;
	}

	public List<ArticlesGroup> getArticleSubgroups(ArticlesGroup group) throws SQLException {
		List<ArticlesGroup> subgroups = new ArrayList<>();
		String sqlQuery = "SELECT KodGrupy FROM GrupyNadrzedne WHERE KodGrupyNadrzednej=" + group.getCode() + ";";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			subgroups.add(getArticleGroup(rr.getInt(1)));
		}
		return subgroups;
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
				group.setCode(results.get(0).getInt(1));
				for (ArticleAttribute attribute : group.getAttributes()) {
					query = "INSERT INTO AtrybutyGrupTowarowych (KodGrupy, IdAtrybutu) "
							+ "VALUES (" + group.getCode() + ", " + attribute.getId() + ");";
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

	public boolean updateArticlesGroup(ArticlesGroup group) throws DatabaseException, SQLException {
		//TODO code update articles group logic
		return false;
	}

	public List<Integer> getSubGroupsIds(int groupId) throws SQLException {
		List<Integer> results = new ArrayList<>();

		String sQuery = "SELECT KodGrupy FROM GrupyNadrzedne WHERE KodGrupyNadrzednej=" + groupId + ";";
		List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(sQuery);
		for (ResultRow rr : resultRows) {
			results.add(new Integer(rr.getInt(1)));
			results.addAll(getSubGroupsIds(rr.getInt(1)));
		}

		return results;
	}

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
	// </editor-fold>
}
