package core.m;

import java.sql.SQLException;
import java.util.Map;

/**
 * Represents a row of results retrieved from database by execute of SQL query
 * (each result in the row represents one colum requested in SQL query).
 *
 * @author Damian Kaczybura
 */
public class ResultRow {
	// <editor-fold defaultstate="collapsed" desc="Object variables">
	private Map<Integer, Result> m_Results;
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Creating object">
	public ResultRow(Map<Integer, Result> results) {
		m_Results = results;
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	/**
	 * Retrieves the value of the designed column in the current row as the
	 * double number.
	 *
	 * @param iColumnId index of the desired column (first column index is 1,
	 *                     next is 2 and so on...)
	 *
	 * @return value for the specified colum
	 *
	 * @throws SQLException when column with specified index does not exist or
	 *                         data in specified
	 *                         column are incompatible with desired type (double)
	 */
	public double getDouble(int iColumnId) throws SQLException {
		Double dResult;
		try {
			dResult = Double.parseDouble(getString(iColumnId));
		}
		catch (NumberFormatException ex) {
			throw new SQLException("Data in specified column can not be cast to type integer.");
		}
		return dResult;
	}

	/**
	 * Retrieves the value of the designed column in the current row as the
	 * float number.
	 *
	 * @param iColumnId index of the desired column (first column index is 1,
	 *                     next is 2 and so on...)
	 *
	 * @return value for the specified colum
	 *
	 * @throws SQLException when column with specified index does not exist or
	 *                         data in specified
	 *                         column are incompatible with desired type (float)
	 */
	public float getFloat(int iColumnId) throws SQLException {
		Float fResult;
		try {
			fResult = Float.parseFloat(getString(iColumnId));
		}
		catch (NumberFormatException ex) {
			throw new SQLException("Data in specified column can not be cast to type integer.");
		}
		return fResult;
	}

	/**
	 * Retrieves the value of the designed column in the current row as the
	 * integer number.
	 *
	 * @param iColumnId index of the desired column (first column index is 1,
	 *                     next is 2 and so on...)
	 *
	 * @return value for the specified colum
	 *
	 * @throws SQLException when column with specified index does not exist or
	 *                         data in specified
	 *                         column are incompatible with desired type (integer)
	 */
	public int getInt(int iColumnId) throws SQLException {
		Integer iResult;
		try {
			iResult = Integer.parseInt(getString(iColumnId));
		}
		catch (NumberFormatException ex) {
			throw new SQLException("Data in specified column can not be cast to type integer.");
		}
		return iResult;
	}

	/**
	 * Retrieves the value of the designed column in the current row as the
	 * String item.
	 *
	 * @param iColumnId index of the desired column (first column index is 1,
	 *                     next is 2 and so on...)
	 *
	 * @return value for the specified colum
	 *
	 * @throws SQLException when column with specified index does not exist
	 */
	public String getString(int iColumnId) throws SQLException {
		String sResult = null;
		if (m_Results.containsKey(iColumnId)) {
			sResult = m_Results.get(iColumnId).getValue();
		}
		else {
			throw new SQLException("Column with index " + iColumnId + " does not exist.");
		}
		return sResult;
	}
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">

	// </editor-fold>
	public boolean isEmpty() {
		return m_Results.isEmpty();
	}
	// </editor-fold>
}