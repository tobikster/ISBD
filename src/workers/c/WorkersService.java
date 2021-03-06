package workers.c;

import core.c.DatabaseManager;
import core.m.DatabaseException;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import workers.c.validators.WorkerValidator;
import workers.m.Worker;
import workers.m.WorkerPosition;

public class WorkersService {
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
  
	public List<Worker> getWorkers() throws SQLException {
		List<Worker> result = new LinkedList<>();
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult("SELECT IdPracownika, Imie, Nazwisko, Stanowisko FROM Pracownicy");
		for (ResultRow resultRow : results) {
			result.add(new Worker(resultRow.getInt(1),
								  resultRow.getString(2),
								  resultRow.getString(3),
								  WorkerPosition.valueFor(resultRow.getString(4))));
		}
		return result;
	}

	public Worker getWorker(int id) throws SQLException {
		Worker result = null;
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult("SELECT IdPracownika, Imie, Nazwisko, Stanowisko, Login, Haslo FROM Pracownicy WHERE IdPracownika = " + id + ";");
		if (results.size() > 1) {
			throw new SQLException("W bazie danych istnieje " + results.size() + " pracowników o podanym identyfikatorze!");
		}
		else {
			ResultRow resultRow = results.get(0);
			result = new Worker(resultRow.getInt(1),
								resultRow.getString(2),
								resultRow.getString(3),
								WorkerPosition.valueFor(resultRow.getString(4)),
								resultRow.getString(5),
								resultRow.getString(6));
		}
		return result;
	}

  public Worker getWorker(String fullName) throws SQLException {
    String sQuery = "SELECT IdPracownika, Imie, Nazwisko, Stanowisko, Login, Haslo FROM Pracownicy WHERE Login='"+ fullName + "';";
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
    if(results.size() > 1) {
			throw new SQLException("W bazie danych istnieje " + results.size() + " pracowników o podanych danych!");
		} else if(results.isEmpty()) {
      throw new SQLException("W bazie danych nie istnieje pracownik o podanym identyfikatorze!");
    }
    
		Worker result;
    
    ResultRow resultRow = results.get(0);
    result = new Worker(resultRow.getInt(1),
              resultRow.getString(2),
              resultRow.getString(3),
              WorkerPosition.valueFor(resultRow.getString(4)),
              resultRow.getString(5),
              resultRow.getString(6));
		
		return result;
	}

	public boolean saveWorker(Worker worker) throws SQLException, DatabaseException {
		boolean result = false;
		WorkerValidator validator = new WorkerValidator();
		if (validator.validate(worker)) {
			String query = "UPDATE Pracownicy "
					+ "SET Imie = '" + worker.getName() + "', Nazwisko = '" + worker.getSurname() + "', Stanowisko = '" + worker.getJob() + "', Login = '" + worker.getLogin() + "', Haslo = '" + worker.getPassword() + "' "
					+ "WHERE IdPracownika = " + worker.getId() + ";";
			result = DatabaseManager.getInstance().executeQuery(query);
		}
		return result;
	}

	public boolean removeWorker(Worker worker) throws SQLException {
		String query = "DELETE * "
				+ "FROM Pracownicy "
				+ "WHERE IdPracownika = " + worker.getId() + ";";
		DatabaseManager.getInstance().executeQuery(query);
		return true;
	}

	public boolean addWorker(Worker worker) throws SQLException, DatabaseException {
		boolean result = false;
		WorkerValidator validator = new WorkerValidator();
		if (validator.validate(worker)) {
			String query = "INSERT INTO Pracownicy (Imie, Nazwisko, Stanowisko, Login, Haslo) "
					+ "VALUES ('" + worker.getName() + "', '" + worker.getSurname() + "', '" + worker.getJob() + "', '" + worker.getLogin() + "', '" + worker.getPassword() + "');";
			result = DatabaseManager.getInstance().executeQuery(query);
		}
		return result;
	}
}
