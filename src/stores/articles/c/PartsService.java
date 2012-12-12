package stores.articles.c;

import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import stores.articles.c.validators.parts.PartValidator;
import stores.articles.m.ArticleAttribute;
import stores.articles.m.Part;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.producers.c.ProducersService;

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

		Map<ArticleAttribute, String> partAttributes = new LinkedHashMap<>();
		sQuery = "SELECT AtrybutyGrupTowarowych.IdAtrybutuGrupy, AtrybutyCzesci.IdAtrybutu, AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyGrupTowarowych INNER JOIN AtrybutyCzesci ON AtrybutyGrupTowarowych.IdAtrybutu = AtrybutyCzesci.IdAtrybutu "
				+ "WHERE AtrybutyGrupTowarowych.KodGrupy=" + result.getInt(2) + ";";
		List<ResultRow> attributesNames = DatabaseManager.getInstance().executeQueryResult(sQuery);
		for (ResultRow attributeName : attributesNames) {
			sQuery = "SELECT WartosciAtrybutowCzesci.Wartosc "
					+ "FROM WartosciAtrybutowCzesci "
					+ "WHERE WartosciAtrybutowCzesci.IdAtrybutuGrupy=" + attributeName.getInt(1) + ";";
			List<ResultRow> attributesValues = DatabaseManager.getInstance().executeQueryResult(sQuery);
			String value = (attributesValues.isEmpty()) ? "" : attributesValues.get(0).getString(1);
			partAttributes.put(new ArticleAttribute(attributeName.getInt(2), attributeName.getString(3)), value);
		}
		part.setAttributes(partAttributes);

		return part;
	}

	public void changeGroup(Part part, ArticlesGroup newGroup) throws SQLException {
//		String query = "SELECT AtrybutyCzesci.IdAtrybutu "
//		+ "FROM AtrybutyCzesci LEFT JOIN AtrybutyGrupTowarowych ON AtrybutyCzesci.IdAtrybutu = AtrybutyGrupTowarowych.IdAtrybutu "
//				+ "WHERE AtrybutyGrupTowarowych.KodGrupy = " + part.getGroup().getCode() + ";";
//		List<ResultRow> oldGroupAttributesIds = DatabaseManager.getInstance().executeQueryResult(query);

		String query = "SELECT AtrybutyCzesci.IdAtrybutu , AtrybutyCzesci.Nazwa "
				+ "FROM AtrybutyCzesci LEFT JOIN AtrybutyGrupTowarowych ON AtrybutyCzesci.IdAtrybutu = AtrybutyGrupTowarowych.IdAtrybutu "
				+ "WHERE AtrybutyGrupTowarowych.KodGrupy = " + newGroup.getCode() + ";";
		List<ResultRow> newGroupAttributesIds = DatabaseManager.getInstance().executeQueryResult(query);

		Map<ArticleAttribute, String> newPartAttributes = new LinkedHashMap<>();
		for (ResultRow row : newGroupAttributesIds) {
			ArticleAttribute attribute = new ArticleAttribute(row.getInt(1), row.getString(2));
			String value = "";
			for (Entry<ArticleAttribute, String> entry : part.getAttributes().entrySet()) {
				if (entry.getKey().getId() == row.getInt(1)) {
					attribute = entry.getKey();
					value = entry.getValue();
				}
			}
			newPartAttributes.put(attribute, value);
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
				+ "WHERE Zawartosc='c'" + sGroupsCondition + ";";

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
		boolean result = false;
		PartValidator validator = new PartValidator();
		if (validator.validate(part)) {
			DatabaseManager.getInstance().startTransaction();
			String query = "INSERT INTO Czesci(KodGrupyTowarowej, IdProducenta. NrKatalogowy, Nazwa, Marza, CenaBrutto, Ilosc) "
					+ "VALUES(" + part.getGroup().getCode() + ", " + part.getProducer().getId() + ", '" + part.getCatalogNumber() + "', '" + part.getName() + "', " + part.getMargin() + ", " + part.getGrossPrice() + ", " + part.getCount() + ");";
			System.out.println(query);
//			DatabaseManager.getInstance().executeQuery(query);
			result = true;
			if(result) {
				DatabaseManager.getInstance().commitTransaction();
			}
			else {
				DatabaseManager.getInstance().rollbackTransaction();
			}
		}
		return result;
	}
	// </editor-fold>
}
