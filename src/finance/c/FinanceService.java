package finance.c;

import core.c.DatabaseManager;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FinanceService {
	// <editor-fold defaultstate="collapsed" desc="Object variables">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static FinanceService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final FinanceService p_instance = new FinanceService();
	}
	// </editor-fold>

	private FinanceService() {
	}
	// </editor-fold>
  
	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">
	// </editor-fold>
	public List<VATRate> getVatRates() throws SQLException {
		List<VATRate> result = new LinkedList<>();
		String query = "SELECT IdStawki, Stawka "
				+ "FROM StawkiVAT;";
		List<ResultRow> queryResults = DatabaseManager.getInstance().executeQueryResult(query);
		for (ResultRow row : queryResults) {
			result.add(new VATRate(row.getInt(1), row.getDouble(2)));
		}
		return result;
	}
	// </editor-fold>
}
