package articles.c;

import articles.v.ArticlesListView;
import core.c.DatabaseManager;
import core.c.TablePagination;
import core.c.TablePagination.PageNumberException;
import core.m.ResultRow;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticlesViewController {
	// <editor-fold defaultstate="collapsed" desc="Object variables">

	public static final String[] ARTICLE_LIST_TABLE_HEADERS = {
		"Nazwa",
		"Numer katalogowy",
		"Grupa towarowa",
		"Producent",
		"Liczba",
		"Cena",
		"Marża"
	};
	private TablePagination _data;
	private ArticlesListView _articlesListView;
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Creating object">

	public ArticlesViewController(ArticlesListView view) throws SQLException {
		String query = "SELECT Czesci.Nazwa, Czesci.NrKatalogowy, GrupyTowarowe.Nazwa, Producenci.Nazwa, Czesci.Ilosc, Czesci.CenaBrutto, Czesci.Marza "
				+ "FROM Czesci, Producenci, GrupyTowarowe "
				+ "WHERE Czesci.IdProducenta = Producenci.IdProducenta AND Czesci.KodGrupyTowarowej = GrupyTowarowe.KodGrupy;";
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(query);

		Object[][] tableData = new Object[results.size()][ARTICLE_LIST_TABLE_HEADERS.length];

		for (int i = 0; i < tableData.length; ++i) {
			tableData[i][0] = results.get(i).getString(1);
			tableData[i][1] = results.get(i).getString(2);
			tableData[i][2] = results.get(i).getString(3);
			tableData[i][3] = results.get(i).getString(4);
			tableData[i][4] = results.get(i).getString(5);
			tableData[i][5] = results.get(i).getString(6);
			tableData[i][6] = results.get(i).getString(7);
		}
		_data = new TablePagination(tableData, 5);
		_articlesListView = view;
    try
    {
      _articlesListView.setArticlesListValues(_data.getCurrentPageData(), ARTICLE_LIST_TABLE_HEADERS);
    }
    catch(PageNumberException ex)
    {
      Logger.getLogger(ArticlesViewController.class.getName()).log(Level.SEVERE, null, ex);
    }
	}
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">
	// </editor-fold>
	// </editor-fold>
}