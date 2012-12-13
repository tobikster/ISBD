package stores.producers.c;

import core.c.DatabaseManager;
import core.c.EntityValidator;
import core.m.DatabaseException;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import stores.producers.c.validators.ProducerValidator;
import stores.producers.m.Producer;

public class ProducersService {
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static ProducersService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final ProducersService p_instance = new ProducersService();
	}
	// </editor-fold>

	private ProducersService() {
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="PRODUCER methods">
	public Producer getProducer(int producerId) throws SQLException {
		String sQuery = "SELECT * FROM Producenci WHERE IdProducenta=" + producerId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Producer with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		Producer producer = new Producer();
		producer.setId(producerId);
		producer.setName(result.getString(2));
		//TODO load logo for producer

		return producer;
	}

	public List<Producer> getProducers() throws SQLException {
		String sQuery = "SELECT * FROM Producenci ORDER BY Nazwa ASC;";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		List<Producer> producers = new ArrayList<>();
		Producer currentProducer;

		for (ResultRow result : results) {
			currentProducer = new Producer();
			currentProducer.setId(result.getInt(1));
			currentProducer.setName(result.getString(2));
			//TODO load logo for producer
			producers.add(currentProducer);
		}

		return producers;
	}

	public Producer addProducer(Producer producer) throws DatabaseException, SQLException {
		EntityValidator<Producer> validator = new ProducerValidator();
		validator.validate(producer);
		int id;

		DatabaseManager.getInstance().startTransaction();
		try {
			String sQuery = "INSERT INTO Producenci(Nazwa, Logo) VALUES ('" + producer.getName() + "', NULL);";
			DatabaseManager.getInstance().executeQuery(sQuery);

			sQuery = "SELECT TOP 1 IdProducenta FROM Producenci ORDER BY IdProducenta DESC;";
			id = DatabaseManager.getInstance().executeQueryResult(sQuery).get(0).getInt(1);
		}
		catch (SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}
		DatabaseManager.getInstance().commitTransaction();

		return getProducer(id);
	}

	public void updateProducer(Producer producer) throws DatabaseException, SQLException {
		EntityValidator<Producer> validator = new ProducerValidator();
		validator.validate(producer);

		String sQuery = "UPDATE Producenci SET Nazwa='" + producer.getName() + "' WHERE IdProducenta=" + producer.getId() + ";";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}

	public boolean removeProducer(Producer producer) throws SQLException {
		String query = "DELETE FROM Producenci "
				+ "WHERE IdProducenta = " + producer.getId() + ";";
		DatabaseManager.getInstance().executeQuery(query);
		return true;
	}

	public int checkProducerRemovability(Producer producer) throws SQLException {
//		final int containsPartsButEmpty = 32;
//		final int containsTiresButEmpty = 16;
//		final int containsParts = 8;
//		final int conatinsTires = 4;
//		final int noParts = 2;
//		final int noTires = 1;

		int result = 0;

		String query = "SELECT SUM(Ilosc) "
				+ "FROM Czesci "
				+ "WHERE IdProducenta = " + producer.getId() + ";";
		System.out.println(query);
		List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
		if (!resultRows.isEmpty()) {
			try {
				if (resultRows.get(0).getFloat(1) == 0.0) {
//					result += containsPartsButEmpty;
					result = -1;
				}
				else {
//					result += containsParts;
					result = -2;
				}
			}
			catch (SQLException ex) {
//				result += noParts;
				result = 0;
				throw ex;
			}
		}
		query = "SELECT Sum(DOTyOpon.Liczba) "
				+ "FROM (BieznikiOpon INNER JOIN Opony ON BieznikiOpon.IdBieznika = Opony.IdBieznika) INNER JOIN DOTyOpon ON Opony.IdOpony = DOTyOpon.IdOpony "
				+ "WHERE BieznikiOpon.IdProducenta = " + producer.getId() + ";";
		resultRows = DatabaseManager.getInstance().executeQueryResult(query);
		if (!resultRows.isEmpty()) {
			try {
				if (resultRows.get(0).getInt(1) == 0) {
//					result += containsTiresButEmpty;
					result = Math.min(result, -1);
				}
				else {
//					result += conatinsTires;
					result = Math.min(result, -2);
				}
			}
			catch (SQLException ex) {
//				result += noTires;
				result = Math.min(result, 0);
			}
		}
		return result;
	}
	// </editor-fold>
}
