package core.c;

import core.m.ResultRow;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paweł
 */
public class Main
{
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    try
    {
      List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult("SELECT COUNT(*) FROM RozmiaryOpon;");
      System.out.println("Ilość rozmiarów opon: "+results.get(0).getInt(1));
      
    }
    catch(SQLException ex)
    {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
