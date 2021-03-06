package core.c;

import core.m.Result;
import core.m.ResultRow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import sun.jdbc.odbc.JdbcOdbcDriver;
import utils.m.WorkingMap;

/**
 * 
 * @author Damian Kacztbura
 */
public class DatabaseManager
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">
  /**
   * Current database connection.
   */
  private Connection m_Connection;
  private boolean m_bTransactionMode;
  private boolean bMaintainConnection;
  private int iTransactionDepth;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static DatabaseManager getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final DatabaseManager p_instance = new DatabaseManager();
  }
  // </editor-fold>

  private DatabaseManager()
  {
    try
    {
      DriverManager.registerDriver(new JdbcOdbcDriver());
      m_bTransactionMode=false;
      bMaintainConnection=false;
      iTransactionDepth=0;
    }
    catch(SQLException ex)
    {
      System.out.println("Unnable to instantiate DatabaseManager due to exception:\n"+ex.getMessage());
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
  private void connect() throws SQLException
  {
    m_Connection = DriverManager.getConnection("jdbc:odbc:" + "ISBD");
  }

  private void disconnect() throws SQLException
  {
    if(m_Connection!=null && !m_Connection.isClosed()) {
      if(bMaintainConnection)
        bMaintainConnection=false;
      else
        m_Connection.close();
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
  // <editor-fold defaultstate="collapsed" desc="Getters">

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Setters">

  // </editor-fold>

  /**
   * Executes given SQL query on database and returns a set of results retrieved for that query.
   * @param sQuery SQL query to be executed
   * @return list of result rows retrieved for given query
   * @throws SQLException when an error occures while trying to execute query
   * (eg. when the query is incorrect or database connection is lost)
   */
  public List<ResultRow> executeQueryResult(String sQuery) throws SQLException
  {
    //System.out.println(sQuery+"\n");
    try {
      if(m_Connection==null || m_Connection.isClosed())
        connect();
      Statement stmt = m_Connection.createStatement();
      ResultSet rs = stmt.executeQuery(sQuery);
      List results = new ArrayList<>();
      while (rs.next())
      {
        Map<Integer, Result> resultsRow=new WorkingMap<>();
        for(int i=1;i<=rs.getMetaData().getColumnCount();i++)
        {
          Result result = new Result(i, rs.getMetaData().getColumnName(i), rs.getString(i));
          resultsRow.put(i, result);
        }
        results.add(new ResultRow(resultsRow));
      }
      if(m_Connection!=null && !m_Connection.isClosed() && !m_bTransactionMode)
        disconnect();
      return results;
    } catch(SQLException ex) {
      bMaintainConnection=false;
      disconnect();
      throw ex;
    }
  }
  
  /**
   * Executes given SQL query on database.
   * @param sQuery SQL query to be executed
   * @return true, if query was executed correctly, false otherwise 
   * @throws SQLException when an error occures while trying to execute query
   * (eg. when the query is incorrect or database connection is lost)
   */
  public Boolean executeQuery(String sQuery) throws SQLException
  {
    if(m_Connection==null || m_Connection.isClosed())
      connect();
    Statement stmt = m_Connection.createStatement();
    Boolean rs = stmt.execute(sQuery);
    if(m_Connection!=null && !m_Connection.isClosed() && !m_bTransactionMode)
        disconnect();
    return rs;
  }
  
  public void maintainConnection() {
    bMaintainConnection = true;
  }

  public void commitTransaction() throws SQLException {
    if(m_bTransactionMode) {
      if(iTransactionDepth==1) {
        //System.out.print("TRANSACTION - COMMIT");
        m_Connection.commit();
        m_Connection.setAutoCommit(m_bTransactionMode);
        disconnect();
        m_bTransactionMode=false;
        //System.out.println("\t\tDONE");
      }
      iTransactionDepth--;
    }
  }
  
  public void startTransaction() throws SQLException {
    iTransactionDepth++;
    if(!m_bTransactionMode) {
      //System.out.print("TRANSACTION - START");
      if(m_Connection==null || m_Connection.isClosed())
        connect();
      m_Connection.setAutoCommit(m_bTransactionMode);
      m_bTransactionMode=true;
      //System.out.println("\t\tDONE");
    }
  }
  
  public void rollbackTransaction() throws SQLException {
    if(m_bTransactionMode) {
      if(iTransactionDepth==1) {
        //System.out.print("TRANSACTION - ROLLBACK");
        m_Connection.rollback();
        m_Connection.setAutoCommit(m_bTransactionMode);
        disconnect();
        m_bTransactionMode=false;
        //System.out.println("\tDONE");
      }
      iTransactionDepth--;
    }
  }
  // </editor-fold>
}
