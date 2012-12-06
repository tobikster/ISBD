package stores.producers.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.List;
import stores.producers.m.Producer;

public class ProducersService
{
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ProducersService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ProducersService p_instance = new ProducersService();
  }
  // </editor-fold>

  private ProducersService()
  {

  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Producers methods">
  public Producer getProducer(int producerId) throws SQLException {
    String sQuery = "SELECT * FROM Producenci WHERE IdProducenta="+producerId+";";

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
  // </editor-fold>
}
