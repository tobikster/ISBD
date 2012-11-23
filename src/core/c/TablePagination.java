/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.c;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Zjamnik
 */
public class TablePagination <T>
{
	private List<T> tableData;
	private int rowsPerPage;
	private int currentPage;
	private int pageCount;
	
	public TablePagination(List<T> tableData, int rowsPerPage)
	{
		this.tableData = tableData;
		this.rowsPerPage = rowsPerPage;
		currentPage = 0;
		pageCount = (int)Math.ceil((double)(tableData.size()) / (double)(rowsPerPage));
	}

	public List<T> getTableData()
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

	public void setTableData(List<T> tableData)
	{
		this.tableData = tableData;
		pageCount = (int)Math.ceil((tableData.size() * 1.0) / (rowsPerPage * 1.0));
	}

	public void setRowsPerPage(int rowsPerPage)
	{
		this.rowsPerPage = rowsPerPage;
		pageCount = (int)Math.ceil((tableData.size() * 1.0) / (rowsPerPage * 1.0));
	}
	
	public List<T> getCurrentPageData()
	{
		if (currentPage < 0 || currentPage > pageCount-1)
		{
//			throw new PageNumberException("Numer strony (" + currentPage + ") poza zakresem (" + 0 + "-" + (pageCount-1) + ").");
		}
		
		List<T> currentPageData = new LinkedList<T>();
		
		for(int i = 0; currentPage * rowsPerPage + i < tableData.size() && i < rowsPerPage; ++i)
		{
			currentPageData.add(tableData.get(currentPage * rowsPerPage + i));
		}
		
		return currentPageData;
	}
	
	public List<T> getNextPage()
	{
		++currentPage;
		
		return getCurrentPageData();
	}
	
	public List<T> getPreviousPage()
	{
		--currentPage;
		
		return getCurrentPageData();
	}
	
	public List<T> getPageData(int page)
	{
		currentPage = page;
		
		return getCurrentPageData();
	}
}
