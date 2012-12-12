package core.c;

import core.m.ResultRow;
import core.m.UserAuthenticationException;
import java.sql.SQLException;
import workers.c.WorkersService;
import workers.m.Worker;

public class AuthenticationService
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">
  private Worker m_LoggedInUser;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static AuthenticationService getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final AuthenticationService p_instance = new AuthenticationService();
  }
  // </editor-fold>

  private AuthenticationService()
  {
    m_LoggedInUser = null;
  }
  // </editor-fold>

  public boolean confirmPasswordForUser(int workerId, String sPassword) throws SQLException
  {
    String sQuery="SELECT COUNT(IdPracownika) FROM Pracownicy WHERE IdPracownika="+workerId+" AND Haslo='"+sPassword+"';";
    ResultRow rr = DatabaseManager.getInstance().executeQueryResult(sQuery).get(0);
    return rr.getInt(1)==1;
  }
  
  public void authenticateUser(String sUserName, char[] caPassword) throws UserAuthenticationException
  {
    try
    {
      Worker worker = WorkersService.getInstance().getWorker(sUserName);
      if(confirmPasswordForUser(worker.getId(), new String(caPassword))) {
        m_LoggedInUser = worker;
      } else {
        throw new UserAuthenticationException("Hasło użytkownika jest nieprawidłowe!");
      }
    }
    catch(SQLException ex)
    {
      throw new UserAuthenticationException("Login użytkownika jest nieprawidłowy - "+ex.getMessage(), ex);
    }
  }
  
  public Worker getLoggedInUser()
  {
    return m_LoggedInUser;
  }
  
  public void logout()
  {
    ViewManager.getInstance().closeMainWindow();
    m_LoggedInUser=null;
    ViewManager.getInstance().showLoginWindow();
  }
}
