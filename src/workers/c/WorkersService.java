package workers.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import workers.m.Worker;

public class WorkersService {
	// <editor-fold defaultstate="collapsed" desc="Object variables">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static WorkersService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final WorkersService p_instance = new WorkersService();
	}
	// </editor-fold>

	private WorkersService() {
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">
	// </editor-fold>
	public List<Worker> getWorkers() throws SQLException {
		List<Worker> result = new LinkedList<>();
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult("SELECT Imie, Nazwisko, Stanowisko FROM Pracownicy");
		for (ResultRow resultRow : results) {
			result.add(new Worker(resultRow.getString(1),
								  resultRow.getString(2),
								  resultRow.getString(3)));
		}
		return result;
	}
	// </editor-fold>
}
