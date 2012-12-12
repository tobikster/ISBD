package stores.groups.c;

import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import stores.articles.c.validators.parts.ArticleAttributeValidator;
import stores.articles.m.ArticleAttribute;
import stores.groups.c.validators.ArticlesGroupValidator;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;

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
		String sQuery = "SELECT KodGrupy, Nazwa, VAT, Stawka, Typ FROM GrupyTowarowe "
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

		Set<ArticleAttribute> attributes = new LinkedHashSet<>();
		sQuery = "SELECT AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyGrup LEFT JOIN AtrybutyCzesci ON AtrybutyGrup.IdAtrybutu = AtrybutyCzesci.IdAtrybutu "
				+ "WHERE AtrybutyGrup.KodGrupy = " + iGroupCode + ";";
		List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(sQuery);
		for (ResultRow row : resultRows) {
			ArticleAttribute attribute = new ArticleAttribute(row.getInt(1), row.getString(2));
			attributes.add(attribute);
		}
		group.setAttributes(attributes);
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
		String sqlQuery = "SELECT KodGrupy FROM GrupyTowarowe WHERE Typ='c' ORDER BY Nazwa;";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			articleGroups.add(getArticleGroup(rr.getInt(1)));
		}
		return articleGroups;
	}

	public List<ArticlesGroup> getTireGroups() throws SQLException {
		List articleGroups = new ArrayList<>();
		String sqlQuery = "SELECT KodGrupy FROM GrupyTowarowe WHERE Typ='o';";
		ArrayList<ResultRow> result = (ArrayList<ResultRow>) DatabaseManager.getInstance().executeQueryResult(sqlQuery);
		for (ResultRow rr : result) {
			articleGroups.add(getArticleGroup(rr.getInt(1)));
		}
		return articleGroups;
	}

	public List<ArticleAttribute> getArticlesGroupAttributes(int groupCode) throws SQLException {
		List<ArticleAttribute> result = new LinkedList<>();
		String query = "SELECT AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyCzesci, AtrybutyGrup "
				+ "WHERE AtrybutyCzesci.IdAtrybutu = AtrybutyGrup.IdAtrybutu AND AtrybutyGrup.KodGrupy = " + groupCode + ";";
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);
		for (ResultRow queryResult : results) {
			result.add(new ArticleAttribute(queryResult.getInt(1), queryResult.getString(2)));
		}
		return result;
	}

	public List<ArticleAttribute> getAvailableAttributes(int groupCode) throws SQLException {
		List<ArticleAttribute> result = new LinkedList<>();
//		String query = "SELECT AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
//				+ "FROM AtrybutyCzesci, AtrybutyGrup "
//				+ "WHERE AtrybutyCzesci.IdAtrybutu = AtrybutyGrup.IdAtrybutu AND NOT AtrybutyGrup.KodGrupy = " + groupCode + ";";
		String query = "SELECT IdAtrybutu, Nazwa "
				+ "FROM AtrybutyCzesci;";
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);
		for (ResultRow row : results) {
			result.add(new ArticleAttribute(row.getInt(1), row.getString(2)));
		}
		return result;
	}

	public boolean addArticlesGroup(ArticlesGroup group) throws DatabaseException, SQLException {
		ArticlesGroupValidator validator = new ArticlesGroupValidator();
		if (validator.validate(group)) {
			try {
				DatabaseManager.getInstance().startTransaction();
				String query = "INSERT INTO GrupyTowarowe (Nazwa, VAT, Typ) "
						+ "VALUES('" + group.getName() + "', " + group.getVat().getId() + ", '" + group.getType().toString() + "');";
				DatabaseManager.getInstance().executeQuery(query);

				query = "SELECT KodGrupy FROM GrupyTowarowe WHERE Nazwa = '" + group.getName() + "' AND VAT = " + group.getVat().getId() + ";";
				List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);
				if (results.size() != 1) {
				}
				else {
					group.setCode(results.get(0).getInt(1));
					for (ArticleAttribute attribute : group.getAttributes()) {
						query = "INSERT INTO AtrybutyGrup (KodGrupy, IdAtrybutu) "
								+ "VALUES (" + group.getCode() + ", " + attribute.getId() + ");";
						DatabaseManager.getInstance().executeQuery(query);
					}
				}
				DatabaseManager.getInstance().commitTransaction();
			}
			catch (SQLException ex) {
				DatabaseManager.getInstance().rollbackTransaction();
				throw ex;
			}
		}
		return true;
	}

	public boolean updateArticlesGroup(ArticlesGroup group) throws DatabaseException, SQLException {
		ArticlesGroupValidator validator = new ArticlesGroupValidator();
		if (validator.validate(group)) {
			try {
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
						+ "FROM AtrybutyGrup "
						+ "WHERE KodGrupy=" + group.getCode();
				if (!"".equals(groupAttributesIds.toString())) {
					query += " AND IdAtrybutu NOT IN (" + groupAttributesIds.toString() + ")";
				}
				query += ";";
				List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
				for (ResultRow row : resultRows) {
					query = "DELETE FROM AtrybutyGrup "
							+ "WHERE IdAtrybutuGrupy=" + row.getInt(1);
					DatabaseManager.getInstance().executeQuery(query);
				}
				if (!"".equals(groupAttributesIds.toString())) {
					query = "SELECT IdAtrybutu "
							+ "FROM AtrybutyCzesci "
							+ "WHERE IdAtrybutu NOT IN(SELECT IdAtrybutu FROM AtrybutyGrup WHERE KodGrupy=" + group.getCode() + ")"
							+ "AND IdAtrybutu IN (" + groupAttributesIds + ");";
					resultRows = DatabaseManager.getInstance().executeQueryResult(query);
					for (ResultRow row : resultRows) {
						query = "INSERT INTO AtrybutyGrup (KodGrupy, IdAtrybutu) "
								+ "VALUES(" + group.getCode() + ", " + row.getInt(1) + ");";
						DatabaseManager.getInstance().executeQuery(query);
					}
				}
				DatabaseManager.getInstance().commitTransaction();
			}
			catch (SQLException ex) {
				DatabaseManager.getInstance().rollbackTransaction();
				throw ex;
			}
		}
		return true;
	}

	public boolean removeArticlesGroup(ArticlesGroup group) throws DatabaseException, SQLException {
		boolean result = false;
		ArticlesGroupValidator validator = new ArticlesGroupValidator();
		if (validator.validate(group)) {
			String query = "DELETE FROM GrupyTowarowe "
					+ "WHERE KodGrupy=" + group.getCode() + ";";
			DatabaseManager.getInstance().executeQuery(query);
			result = true;
		}
		return result;
	}

	public int checkRemovabilityGroup(ArticlesGroup group) throws SQLException {
		int result = 0;
		String query;
		List<ResultRow> resultRows;
		switch (group.getType()) {
			case PARTS:
				query = "SELECT COUNT(IdCzesci) "
						+ "FROM Czesci "
						+ "WHERE KodGrupyTowarowej = " + group.getCode() + ";";
				resultRows = DatabaseManager.getInstance().executeQueryResult(query);
				if (resultRows.get(0).getInt(1) == 0) {
					result = 1;
				}
				else {
					query = "SELECT COUNT(IdCzesci) "
							+ "FROM Czesci "
							+ "WHERE KodGrupyTowarowej = " + group.getCode() + " AND Ilosc <> 0;";
					resultRows = DatabaseManager.getInstance().executeQueryResult(query);
					if (resultRows.get(0).getInt(1) == 0) {
						result = 0;
					}
					else {
						result = -1;
					}
				}
				break;
			case TIRES:
				query = "SELECT COUNT(IdOpony) "
						+ "FROM Opony "
						+ "WHERE KodGrupyTowarowej = " + group.getCode() + ";";
				resultRows = DatabaseManager.getInstance().executeQueryResult(query);
				if (resultRows.get(0).getInt(1) == 0) {
					result = 1;
				}
				else {
					query = "SELECT Count(Opony.IdOpony) "
							+ "FROM Opony, DOTyOpon "
							+ "WHERE Opony.IdOpony = DOTyOpon.IdOpony AND Opony.KodGrupyTowarowej = " + group.getCode() + " AND DOTyOpon.Liczba <> 0;";
					resultRows = DatabaseManager.getInstance().executeQueryResult(query);
					if (resultRows.get(0).getInt(1) == 0) {
						result = 0;
					}
					else {
						result = -1;
					}
				}
				break;
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
