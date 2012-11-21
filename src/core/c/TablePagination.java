/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.c;

/**
 *
 * @author Zjamnik
 */
public class TablePagination
{
	private Object[] tableData;
	private int rowsPerPage;
	private int currentPage;
	private int pageCount;
	
	public TablePagination(Object[] tableData, int rowsPerPage)
	{
		this.tableData = tableData;
		this.rowsPerPage = rowsPerPage;
		currentPage = 0;
		pageCount = (int)Math.ceil((double)(tableData.length) / (double)(rowsPerPage));
	}

	public Object[] getTableData()
	{
		return tableData;
	}

	public int getRowsPerPage()
	{
		return rowsPerPage;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setTableData(Object[] tableData)
	{
		this.tableData = tableData;
		pageCount = (int)Math.ceil((tableData.length * 1.0) / (rowsPerPage * 1.0));
	}

	public void setRowsPerPage(int rowsPerPage)
	{
		this.rowsPerPage = rowsPerPage;
		pageCount = (int)Math.ceil((tableData.length * 1.0) / (rowsPerPage * 1.0));
	}
	
	public Object[]getCurrentPageData() throws PageNumberException
	{
		if (currentPage < 0 || currentPage > pageCount-1)
		{
			throw new PageNumberException("Numer strony (" + currentPage + ") poza zakresem (" + 0 + "-" + (pageCount-1) + ").");
		}
		
		Object[] currentPageData = new Object[currentPage == pageCount-1 ? (tableData.length % rowsPerPage == 0 ? rowsPerPage : tableData.length % rowsPerPage) : rowsPerPage][];
		
		for(int i = 0; currentPage * rowsPerPage + i < tableData.length && i < rowsPerPage; ++i)
		{
			currentPageData[i] = tableData[currentPage * rowsPerPage + i];
		}
		
		return currentPageData;
	}
	
	public Object[] getNextPage() throws PageNumberException
	{
		++currentPage;
		
		return getCurrentPageData();
	}
	
	public Object[] getPreviousPage() throws PageNumberException
	{
		--currentPage;
		
		return getCurrentPageData();
	}
	
	public Object[] getPageData(int page) throws PageNumberException
	{
		currentPage = page;
		
		return getCurrentPageData();
	}
	
	public class PageNumberException extends Exception
	{
		public PageNumberException(String msg)
		{
			super(msg);
		}
	}
}
