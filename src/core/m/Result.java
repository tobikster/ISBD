package core.m;

/**
 * Represents a single database result retrived by execute of SQL query.
 * @author Damian Kaczybura
 */
public class Result
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">
  private final int m_fiColumnId;
  private final String m_fsColumnName;
  private final String m_fsValue;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  /**
   * Creates new result with given column id, column name and value.
   * @param iColumnId id of the column that is represented by created result
   * @param sColumnName name of the column that is represented by created result
   * @param sValue value for created result
   */
  public Result(int iColumnId, String sColumnName, String sValue)
  {
    m_fiColumnId=iColumnId;
    m_fsColumnName=sColumnName;
    m_fsValue=sValue;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
  // <editor-fold defaultstate="collapsed" desc="Getters">
  /**
   * Returns id of the column represented by this result
   * @return id of the column represented by this result
   */
  public int getColumnId()
  {
    return m_fiColumnId;
  }
  
  /**
   * Returns name of the column represented by this result
   * @return name of the column represented by this result
   */
  public String getColumnName()
  {
    return m_fsColumnName;
  }
  
  /**
   * Returns value of this result
   * @return value of this result
   */
  public String getValue()
  {
    return m_fsValue;
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Setters">
  
  // </editor-fold>
  
  // </editor-fold>
}