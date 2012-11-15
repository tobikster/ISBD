package core.m;

import java.sql.SQLException;
import java.util.Map;

/**
 * Represents a row of results retrieved from database by execute of SQL query
 * (each result in the row represents one colum requested in SQL query).
 * @author Damian Kaczybura
 */
public class ResultRow
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">
  private Map<Integer, Result> m_Results;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  public ResultRow(Map<Integer, Result> results)
  {
    m_Results=results;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
  // <editor-fold defaultstate="collapsed" desc="Getters">
  /**
   * Retrieves the value of the designed column in the current row as the String item.
   * @param iColumnId index of the desired column (first column index is 1, next is 2 and so on...)
   * @return value for the specified colum
   * @throws SQLException when column with specified index does not exist
   */
  public String getString(int iColumnId) throws SQLException
  {
    String sResult = m_Results.get(iColumnId).getValue();
    if(sResult==null)
      throw new SQLException("Column with index "+iColumnId+" does not exist.");
    return sResult;
  }
  
  /**
   * Retrieves the value of the designed column in the current row as the integer number.
   * @param iColumnId index of the desired column (first column index is 1, next is 2 and so on...)
   * @return value for the specified colum
   * @throws SQLException when column with specified index does not exist or data in specified
   * column are incompatible with desired type (integer)
   */
  public int getInt(int iColumnId) throws SQLException
  {
    Integer iResult;
    try
    {
      iResult = Integer.parseInt(m_Results.get(iColumnId).getValue());
    }
    catch(NumberFormatException ex)
    {
      throw new SQLException("Data in specified column can not be cast to type integer.");
    }
    if(iResult == null)
      throw new SQLException("Column with index "+iColumnId+" does not exist.");
    return iResult;
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Setters">
  
  // </editor-fold>
  
  // </editor-fold>
}