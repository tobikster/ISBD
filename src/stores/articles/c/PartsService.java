package stores.articles.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
		part.setCount(result.getInt(8));
		//TODO load image for part item

		return part;
	}

  public List<Part> getParts(ArticlesGroup group) throws SQLException {
    String sGroupsCondition = "";
    if(group!=null && group.getCode()>0) {
      sGroupsCondition+=" AND KodGrupyTowarowej="+group.getCode();
    }
		String sQuery = "SELECT * FROM Czesci LEFT JOIN GrupyTowarowe ON Czesci.KodGrupyTowarowej=GrupyTowarowe.KodGrupy "
      + "WHERE Zawartosc='c'"+sGroupsCondition+";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    List<Part> parts = new ArrayList<>();

    for(ResultRow result : results) {
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
	// </editor-fold>
}
