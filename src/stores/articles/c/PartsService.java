package stores.articles.c;

import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import stores.articles.c.validators.parts.PartValidator;
import stores.articles.m.ArticleAttribute;
import stores.articles.m.Part;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.producers.c.ProducersService;
import utils.m.WorkingMap;

public class PartsService {
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static PartsService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final PartsService p_instance = new PartsService();
	}
	// </editor-fold>

	private PartsService() {
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="PART methods">
	public Part getPart(int iPartId) throws SQLException {
		String sQuery = "SELECT * FROM Czesci WHERE IdCzesci=" + iPartId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Article with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		Part part = new Part();
		part.setId(result.getInt(1));
		part.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
		part.setProducer(ProducersService.getInstance().getProducer(result.getInt(3)));
		part.setCatalogNumber(result.getString(4));
		part.setName(result.getString(5));
		part.setMargin(result.getFloat(6));
		part.setGrossPrice(result.getFloat(7));
		part.setCount(result.getFloat(8));
		//TODO load image for part item

		Map<ArticleAttribute, String> partAttributes = new WorkingMap<>();
		sQuery = "SELECT AtrybutyGrup.IdAtrybutuGrupy, AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyGrup INNER JOIN AtrybutyCzesci ON AtrybutyGrup.IdAtrybutu = AtrybutyCzesci.IdAtrybutu "
				+ "WHERE AtrybutyGrup.KodGrupy=" + result.getInt(2) + ";";
		List<ResultRow> attributesNames = DatabaseManager.getInstance().executeQueryResult(sQuery);
		for (ResultRow attributeName : attributesNames) {
			sQuery = "SELECT WartosciAtrybutowCzesci.Wartosc "
					+ "FROM WartosciAtrybutowCzesci "
					+ "WHERE WartosciAtrybutowCzesci.IdCzesci = " + iPartId + " AND WartosciAtrybutowCzesci.IdAtrybutuGrupy=" + attributeName.getInt(1) + ";";
			List<ResultRow> attributesValues = DatabaseManager.getInstance().executeQueryResult(sQuery);
			String value = (attributesValues.isEmpty()) ? "" : attributesValues.get(0).getString(1);
			partAttributes.put(new ArticleAttribute(attributeName.getInt(2), attributeName.getString(3)), value);
		}
		part.setAttributes(partAttributes);

		return part;
	}

	public void changeGroup(Part part, ArticlesGroup newGroup) throws SQLException {
//		String query = "SELECT AtrybutyCzesci.IdAtrybutu "
//		+ "FROM AtrybutyCzesci LEFT JOIN AtrybutyGrup ON AtrybutyCzesci.IdAtrybutu = AtrybutyGrup.IdAtrybutu "
//				+ "WHERE AtrybutyGrup.KodGrupy = " + part.getGroup().getCode() + ";";
//		List<ResultRow> oldGroupAttributesIds = DatabaseManager.getInstance().executeQueryResult(query);

		String query = "SELECT AtrybutyCzesci.IdAtrybutu , AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyCzesci LEFT JOIN AtrybutyGrup ON AtrybutyCzesci.IdAtrybutu = AtrybutyGrup.IdAtrybutu "
				+ "WHERE AtrybutyGrup.KodGrupy = " + newGroup.getCode() + ";";
		List<ResultRow> newGroupAttributesIds = DatabaseManager.getInstance().executeQueryResult(query);

		Map<ArticleAttribute, String> newPartAttributes = new WorkingMap<>();
		if (part.getAttributes() != null) {
			for (ResultRow row : newGroupAttributesIds) {
				ArticleAttribute attribute = new ArticleAttribute(row.getInt(1), row.getString(2));
				String value = part.getAttributes().get(attribute);
				if (value == null) {
					value = "";
				}
				newPartAttributes.put(attribute, value);
			}
		}
		part.setGroup(newGroup);
		part.setAttributes(newPartAttributes);
	}

	public List<Part> getParts(ArticlesGroup group) throws SQLException {
		String sGroupsCondition = "";
		if (group != null && group.getCode() > 0) {
			sGroupsCondition += " AND KodGrupyTowarowej=" + group.getCode();
		}
		String sQuery = "SELECT * FROM Czesci LEFT JOIN GrupyTowarowe ON Czesci.KodGrupyTowarowej=GrupyTowarowe.KodGrupy "
				+ "WHERE Typ='c'" + sGroupsCondition + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		List<Part> parts = new ArrayList<>();

		for (ResultRow result : results) {
			Part part = new Part();
			part.setId(result.getInt(1));
			part.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
			part.setProducer(ProducersService.getInstance().getProducer(result.getInt(3)));
			part.setCatalogNumber(result.getString(4));
			part.setName(result.getString(5));
			part.setMargin(result.getFloat(6));
			part.setGrossPrice(result.getFloat(7));
			part.setCount(result.getFloat(8));
			parts.add(part);
		}
		//TODO load image for part item

		return parts;
	}

	public boolean addPart(Part part) throws DatabaseException, SQLException {
		PartValidator validator = new PartValidator();
		if (validator.validate(part)) {
			try {
				DatabaseManager.getInstance().startTransaction();
				String query = "INSERT INTO Czesci(KodGrupyTowarowej, IdProducenta, NrKatalogowy, Nazwa, Marza, CenaBrutto, Ilosc) "
						+ "VALUES(" + part.getGroup().getCode() + ", " + part.getProducer().getId() + ", '" + part.getCatalogNumber() + "', '" + part.getName() + "', " + part.getMargin() + ", " + part.getGrossPrice() + ", " + part.getCount() + ");";
				DatabaseManager.getInstance().executeQuery(query);

				query = "SELECT TOP 1 IdCzesci FROM Czesci ORDER BY IdCzesci DESC";
				List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
				part.setId(resultRows.get(0).getInt(1));

				for (Entry<ArticleAttribute, String> attribute : part.getAttributes().entrySet()) {
					query = "SELECT IdAtrybutuGrupy "
							+ "FROM AtrybutyGrup "
							+ "WHERE KodGrupy = " + part.getGroup().getCode() + " AND IdAtrybutu = " + attribute.getKey().getId() + ";";
					resultRows = DatabaseManager.getInstance().executeQueryResult(query);

					query = "INSERT INTO WartosciAtrybutowCzesci (IdCzesci, IdAtrybutuGrupy, Wartosc) "
							+ "VALUES (" + part.getId() + ", " + resultRows.get(0).getInt(1) + ", '" + attribute.getValue() + "');";
					DatabaseManager.getInstance().executeQuery(query);
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

	public boolean updatePart(Part part) throws DatabaseException, SQLException {
		PartValidator validator = new PartValidator();
		if (validator.validate(part)) {
			try {
				DatabaseManager.getInstance().startTransaction();
				String query = "UPDATE Czesci "
						+ "SET KodGrupyTowarowej = " + part.getGroup().getCode() + ", "
						+ "IdProducenta = " + part.getProducer().getId() + ", "
						+ "NrKatalogowy = '" + part.getCatalogNumber() + "', "
						+ "Nazwa = '" + part.getName() + "', "
						+ "Marza = " + part.getMargin() + ", "
						+ "CenaBrutto = " + part.getGrossPrice() + ", "
						+ "Ilosc = " + part.getCount() + " "
						+ "WHERE IdCzesci = " + part.getId() + ";";
				DatabaseManager.getInstance().executeQuery(query);

				StringBuilder idsToStay = new StringBuilder();

				for (Entry<ArticleAttribute, String> attribute : part.getAttributes().entrySet()) {
					query = "SELECT IdAtrybutuGrupy "
							+ "FROM AtrybutyGrup "
							+ "WHERE IdAtrybutu = " + attribute.getKey().getId() + " AND KodGrupy = " + part.getGroup().getCode() + ";";
					List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
					int articlesGroupAttributeId = resultRows.get(0).getInt(1);

					idsToStay.append(articlesGroupAttributeId).append(", ");

					query = "SELECT Wartosc "
							+ "FROM WartosciAtrybutowCzesci "
							+ "WHERE IdCzesci = " + part.getId() + " AND IdAtrybutuGrupy = " + articlesGroupAttributeId + ";";
					resultRows = DatabaseManager.getInstance().executeQueryResult(query);

					if (resultRows.isEmpty()) {
						query = "INSERT INTO WartosciAtrybutowCzesci "
								+ "(IdCzesci, IdAtrybutuGrupy, Wartosc) "
								+ "VALUES (" + part.getId() + ", " + articlesGroupAttributeId + ", '" + attribute.getValue() + "');";
						DatabaseManager.getInstance().executeQuery(query);
					}
					else {
						if (!attribute.getValue().equals(resultRows.get(0).getString(1))) {
							query = "UPDATE WartosciAtrybutowCzesci "
									+ "SET Wartosc = '" + attribute.getValue() + "' "
									+ "WHERE IdCzesci = " + part.getId() + " AND IdAtrybutuGrupy = " + articlesGroupAttributeId + ";";
							DatabaseManager.getInstance().executeQuery(query);
						}
					}
				}
				query = "DELETE FROM WartosciAtrybutowCzesci "
						+ "WHERE IdCzesci = " + part.getId();
				if (idsToStay.length() > 0) {
					idsToStay.setLength(idsToStay.length() - 2);
					query += " AND IdAtrybutuGrupy NOT IN (" + idsToStay + ")";
				}
				query += ";";
				DatabaseManager.getInstance().executeQuery(query);

				DatabaseManager.getInstance().commitTransaction();
			}
			catch (SQLException ex) {
				DatabaseManager.getInstance().rollbackTransaction();
				throw ex;
			}
		}
		return true;
	}

	public boolean deletePart(Part part) throws SQLException, SQLException {
		String query = "DELETE FROM Czesci "
				+ "WHERE IdCzesci = " + part.getId() + ";";
		DatabaseManager.getInstance().executeQuery(query);
		return true;
	}
	// </editor-fold>
}
