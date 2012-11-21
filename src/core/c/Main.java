package core.c;

import core.c.TablePagination.PageNumberException;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *git
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
			
			Object[][] o = {{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"},{"sss", "sss"}};
			System.out.println(o.length);
			
			TablePagination tp = new TablePagination(o, 3);
			
			System.out.println(tp.getPageCount());
			System.out.println(tp.getPageData(3).length);
			for (Object[] object : tp.getPageData(3))
			{
				System.out.println((String)object[0]);
			}
    } catch (PageNumberException | SQLException ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
  }
}
