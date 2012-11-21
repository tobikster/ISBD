package core.c;

import articles.c.ArticlesService;
import articles.m.Article;
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
      
      Article article = ArticlesService.getInstance().getArticle(5);
      System.out.println("Pierwszy artykuł: "+article);
      System.out.println("Grupa:\t\t"+article.getGroup().getName());
      System.out.println("Producent:\t"+article.getProducer().getName());
      System.out.println("Ilosc:\t\t"+article.getCount());
      
      ViewManager.getInstance().showMainWindow();
    }
    catch(SQLException ex)
    {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
