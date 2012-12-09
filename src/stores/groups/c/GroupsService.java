package stores.groups.c;

import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import stores.groups.c.validators.ArticlesGroupValidator;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;
import stores.articles.c.validators.parts.ArticleAttributeValidator;
import stores.articles.m.ArticleAttribute;

public class GroupsService {
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

	public List<ArticlesGroup> getArticleGroups() throws SQLException {
		List articleGroups = new ArrayList<>();
		String sqlQuery = "SELECT KodGrupy FROM GrupyTowarowe;";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			articleGroups.add(getArticleGroup(rr.getInt(1)));
		}
		return articleGroups;
	}

	public List<ArticlesGroup> getPartGroups() throws SQLException {
		List articleGroups = new ArrayList<>();
		String sqlQuery = "SELECT KodGrupy FROM GrupyTowarowe WHERE Zawartosc='c';";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			articleGroups.add(getArticleGroup(rr.getInt(1)));
		}
		return articleGroups;
	}

	public List<ArticlesGroup> getTireGroups() throws SQLException {
		List articleGroups = new ArrayList<>();
		String sqlQuery = "SELECT KodGrupy FROM GrupyTowarowe WHERE Zawartosc='o';";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			articleGroups.add(getArticleGroup(rr.getInt(1)));
		}
		return articleGroups;
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
			String query = "INSERT INTO GrupyTowarowe (Nazwa, VAT, Zawartosc) "
					+ "VALUES('" + group.getName() + "', " + group.getVat().getId() + ", '" + group.getType().toString() + "');";
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
		boolean result = false;
		ArticlesGroupValidator validator = new ArticlesGroupValidator();
		if (validator.validate(group)) {
			DatabaseManager.getInstance().startTransaction();
			String query = "UPDATE GrupyTowarowe "
					+ "SET Nazwa='" + group.getName() + "', VAT=" + group.getVat().getId() + " "
					+ "WHERE KodGrupy=" + group.getCode() + ";";
			DatabaseManager.getInstance().executeQuery(query);
			StringBuilder groupAttributesIds = new StringBuilder();
			if (!group.getAttributes().isEmpty()) {
				for (ArticleAttribute attribute : group.getAttributes()) {
					groupAttributesIds.append(attribute.getId()).append(", ");

				}
				groupAttributesIds.setLength(groupAttributesIds.length() - 2);
			}
			query = "SELECT IdAtrybutuGrupy "
					+ "FROM AtrybutyGrupTowarowych "
					+ "WHERE KodGrupy=" + group.getCode();
			if (!"".equals(groupAttributesIds.toString())) {
				query += " AND IdAtrybutu NOT IN (" + groupAttributesIds.toString() + ")";
			}
			query += ";";
			System.out.println(query);
			List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
			for (ResultRow row : resultRows) {
				query = "DELETE FROM AtrybutyGrupTowarowych "
						+ "WHERE IdAtrybutuGrupy=" + row.getInt(1);
				DatabaseManager.getInstance().executeQuery(query);
			}
			if (!"".equals(groupAttributesIds.toString())) {
				query = "SELECT IdAtrybutu "
						+ "FROM AtrybutyCzesci "
						+ "WHERE IdAtrybutu NOT IN(SELECT IdAtrybutu FROM AtrybutyGrupTowarowych WHERE KodGrupy=" + group.getCode() + ")"
						+ "AND IdAtrybutu IN (" + groupAttributesIds + ");";
				resultRows = DatabaseManager.getInstance().executeQueryResult(query);
				for (ResultRow row : resultRows) {
					query = "INSERT INTO AtrybutyGrupTowarowych (KodGrupy, IdAtrybutu) "
							+ "VALUES(" + group.getCode() + ", " + row.getInt(1) + ");";
					DatabaseManager.getInstance().executeQuery(query);
				}
			}
			result = true;
			if (result) {
				DatabaseManager.getInstance().commitTransaction();
			}
			else {
				DatabaseManager.getInstance().rollbackTransaction();
			}
		}
		return result;
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
}
