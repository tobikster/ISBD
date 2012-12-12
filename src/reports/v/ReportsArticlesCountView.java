package reports.v;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import core.m.DatabaseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import reports.c.ReportsArticlesCountService;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;

public class ReportsArticlesCountView
{
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ReportsArticlesCountView getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ReportsArticlesCountView p_instance = new ReportsArticlesCountView();
  }
  // </editor-fold>

  private ReportsArticlesCountView()
  {

  }
  // </editor-fold>
  
  public String getTiresReport(Date forDate, boolean excludeZeroStates, boolean includeGroupSeparation, boolean groupSeparateCount) throws DatabaseException, ParseException, DocumentException, FileNotFoundException, IOException, SQLException {
    Document report = new Document();
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    Integer[] summaryData = new Integer[includeGroupSeparation?3:2];
    if(!includeGroupSeparation)
      groupSeparateCount=false;

    PdfWriter.getInstance(report, new FileOutputStream(new File("resources/reports/articlesCountReport.pdf")));
    report.open();
    
    //Init summary data
    for(int i=0;i<summaryData.length;i++) {
      summaryData[i] = new Integer(0);
    }

    //Basic customizations
    report.setPageSize(PageSize.A4);
    BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    Paragraph defaultEmptySpace = new Paragraph(" ");
    
    PdfPTable sumUp=new PdfPTable(1);
    PdfPCell emptyCell=new PdfPCell();
    emptyCell.setBorder(Rectangle.BOTTOM);
    emptyCell.addElement(new Paragraph("PODSUMOWANIE"));
    sumUp.addCell(emptyCell);
    
    //Title
    PdfPTable tableTop=new PdfPTable(1);
    PdfPCell titleCell=new PdfPCell();
    Paragraph title=new Paragraph("RAPORT STANÓW MAGAZYNOWYCH na dzień "+dateFormatter.format(forDate));
    title.setFont(new Font(baseFont, 14, Font.BOLD));
    title.setAlignment(Paragraph.ALIGN_CENTER);
    titleCell.setBorder(Rectangle.BOTTOM);
    titleCell.addElement(title);
    tableTop.addCell(titleCell);
    report.add(tableTop);
    
    //Some free space
    report.add(defaultEmptySpace);
    
    //Data table
    // <editor-fold defaultstate="collapsed" desc="Including groups separation">
    if(includeGroupSeparation) {
      java.util.List<ArticlesGroup> groups=GroupsService.getInstance().getTireGroups();
      boolean firstGroup = true;
      int iItemsCount = 1;
      
      //Data tables
      for(ArticlesGroup group : groups) {
        Object[][] reportData = ReportsArticlesCountService.getInstance().getTiresDataForReport(forDate, excludeZeroStates, group);
        
        if(reportData.length==0 && excludeZeroStates) {
          continue;
        }
        
        if(!firstGroup) {
          //Some free space
          report.add(defaultEmptySpace);
        } else {
          firstGroup=false;
        }
        // <editor-fold defaultstate="collapsed" desc="Creating table">
        PdfPTable dataTable = new PdfPTable(5);
        dataTable.setWidths(new float[] {10f, 35f, 20f, 20f, 15f});
        //Table title
        PdfPCell tabTitleCell=new PdfPCell();
        Paragraph tabTitle=new Paragraph(group.getName());
        tabTitle.setFont(new Font(baseFont, 10, Font.BOLD));
        tabTitle.setAlignment(Paragraph.ALIGN_LEFT);
        tabTitleCell.setColspan(5);
        tabTitleCell.setBorder(Rectangle.NO_BORDER);
        tabTitleCell.addElement(tabTitle);
        dataTable.addCell(tabTitleCell);
        //Table header
        PdfPCell columnNo = this.getCell("Lp.", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
        PdfPCell columnSize = this.getCell("Rozmiar", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
        PdfPCell columnProducer = this.getCell("Producent", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
        PdfPCell columnTread = this.getCell("Bieżnik", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
        PdfPCell columnCurrentCount = this.getCell("Stan", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
        if(reportData.length!=0) {
          dataTable.addCell(columnNo);
          dataTable.addCell(columnSize);
          dataTable.addCell(columnProducer);
          dataTable.addCell(columnTread);
          dataTable.addCell(columnCurrentCount);
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Filling table">
        if(reportData.length==0) {
          PdfPCell noDataInfo=getCell("Brak dostępnych towarów", 10, Font.ITALIC, Element.ALIGN_CENTER);
          noDataInfo.setBorder(Rectangle.NO_BORDER);
          noDataInfo.setColspan(5);
          dataTable.addCell(noDataInfo);
        } else {
          PdfPCell colNoDetails;
          PdfPCell colSizeDetails;
          PdfPCell colProducerDetails;
          PdfPCell colTreadDetails;
          PdfPCell colCurrentCountDetails;
          for(int i=0;i<reportData.length;i++) {
            colNoDetails = this.getCell(""+(groupSeparateCount?i+1:iItemsCount++), 7, Font.NORMAL, Element.ALIGN_CENTER);
            colSizeDetails = this.getCell((String)reportData[i][0], 7, Font.NORMAL, Element.ALIGN_CENTER);
            colProducerDetails = this.getCell((String)reportData[i][1], 7, Font.NORMAL, Element.ALIGN_CENTER);
            colTreadDetails = this.getCell((String)reportData[i][2], 7, Font.NORMAL, Element.ALIGN_CENTER);
            colCurrentCountDetails = this.getCell(reportData[i][3].toString(), 7, Font.NORMAL, Element.ALIGN_CENTER);

            dataTable.addCell(colNoDetails);
            dataTable.addCell(colSizeDetails);
            dataTable.addCell(colProducerDetails);
            dataTable.addCell(colTreadDetails);
            dataTable.addCell(colCurrentCountDetails);

            summaryData[1]=summaryData[1]+1;
            summaryData[2]=summaryData[2]+(Integer)reportData[i][3];
          }
        }
        // </editor-fold>
        report.add(dataTable);
        summaryData[0]=summaryData[0]+1;
      }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Excluding groups separation">
    else {
      Object[][] reportData = ReportsArticlesCountService.getInstance().getTiresDataForReport(forDate, excludeZeroStates);

      // <editor-fold defaultstate="collapsed" desc="Creating table">
      PdfPTable dataTable = new PdfPTable(5);
      dataTable.setWidths(new float[] {10f, 35f, 20f, 20f, 15f});
      PdfPCell columnNo = this.getCell("Lp.", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
      PdfPCell columnSize = this.getCell("Rozmiar", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
      PdfPCell columnProducer = this.getCell("Producent", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
      PdfPCell columnTread = this.getCell("Bieżnik", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
      PdfPCell columnCurrentCount = this.getCell("Stan", 7, Font.BOLD, Element.ALIGN_CENTER, new BaseColor(195,195,195));
      if(reportData.length!=0) {
        dataTable.addCell(columnNo);
        dataTable.addCell(columnSize);
        dataTable.addCell(columnProducer);
        dataTable.addCell(columnTread);
        dataTable.addCell(columnCurrentCount);
      }
      // </editor-fold>
      // <editor-fold defaultstate="collapsed" desc="Filling table">
      if(reportData.length==0) {
        PdfPCell noDataInfo=getCell("Brak dostępnych towarów", 10, Font.ITALIC, Element.ALIGN_CENTER);
        noDataInfo.setBorder(Rectangle.NO_BORDER);
        noDataInfo.setColspan(5);
        dataTable.addCell(noDataInfo);
      } else {
        PdfPCell colNoDetails;
        PdfPCell colSizeDetails;
        PdfPCell colProducerDetails;
        PdfPCell colTreadDetails;
        PdfPCell colCurrentCountDetails;
        for(int i=0;i<reportData.length;i++) {
          colNoDetails = this.getCell(""+(i+1), 7, Font.NORMAL, Element.ALIGN_CENTER);
          colSizeDetails = this.getCell((String)reportData[i][0], 7, Font.NORMAL, Element.ALIGN_CENTER);
          colProducerDetails = this.getCell((String)reportData[i][1], 7, Font.NORMAL, Element.ALIGN_CENTER);
          colTreadDetails = this.getCell((String)reportData[i][2], 7, Font.NORMAL, Element.ALIGN_CENTER);
          colCurrentCountDetails = this.getCell(reportData[i][3].toString(), 7, Font.NORMAL, Element.ALIGN_CENTER);

          dataTable.addCell(colNoDetails);
          dataTable.addCell(colSizeDetails);
          dataTable.addCell(colProducerDetails);
          dataTable.addCell(colTreadDetails);
          dataTable.addCell(colCurrentCountDetails);

          summaryData[0]=summaryData[0]+1;
          summaryData[1]=summaryData[1]+(Integer)reportData[i][3];
        }
      }
      // </editor-fold>
      report.add(dataTable);
    }
    // </editor-fold>
    
    //Some free space
    report.add(defaultEmptySpace);
    
    //Summary
    report.add(sumUp);
    // <editor-fold defaultstate="collapsed" desc="Creating table">
    PdfPTable sumupTable = new PdfPTable(2);
    sumupTable.setWidths(new float[]{80f,20f});
    PdfPCell rightTitles=new PdfPCell();
    rightTitles.setBorder(Rectangle.NO_BORDER);
    PdfPCell rightDetails=new PdfPCell();
    rightDetails.setBorder(Rectangle.NO_BORDER);
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Filling table">
    // title column
    String[] titlesColumn=new String[includeGroupSeparation?3:2];
    if(includeGroupSeparation) {
      titlesColumn[0]="Ilość grup towarowych:";
      titlesColumn[1]="Łączna ilość pozycji:";
      titlesColumn[2]="Łączna ilość towarów:";
    } else {
      titlesColumn[0]="Łączna ilość pozycji:";
      titlesColumn[1]="Łączna ilość towarów:";
    }
    // details column
    String[] detailsColumn=new String[includeGroupSeparation?3:2];
    detailsColumn[0]=summaryData[0].toString();
    detailsColumn[1]=summaryData[1].toString();
    if(includeGroupSeparation) {
      detailsColumn[2]=summaryData[2].toString();
    }
    // insert right titles and details
    for(int j=0; j<titlesColumn.length; j++)
    {
      Paragraph titles=new Paragraph(titlesColumn[j]);
      titles.setAlignment(Paragraph.ALIGN_RIGHT);
      titles.setFont(new Font(baseFont, 10));
      Paragraph details=new Paragraph(detailsColumn[j]);
      details.setFont(new Font(baseFont, 10));
      details.setFont(new Font(baseFont, 10, Font.BOLD));
      details.setAlignment(Paragraph.ALIGN_CENTER);
      rightTitles.addElement(titles);
      rightDetails.addElement(details);
    }
    //inserts cells and table into document
    sumupTable.addCell(rightTitles);
    sumupTable.addCell(rightDetails);
    // </editor-fold>
    report.add(sumupTable);
    
    //Finish report
    report.close();
    
    return "resources\\reports\\articlesCountReport.pdf";
  }
  
  private PdfPCell getCell(String text, int fontSize, int fontWeight, int aligment) throws DocumentException, IOException
  {
    // font with polish letters
    BaseFont helvetica=BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);

    // configure cell
    PdfPCell cell=new PdfPCell();
    Paragraph tmp_par=new Paragraph(text);
    tmp_par.setFont(new Font(helvetica, fontSize, fontWeight));
    tmp_par.setAlignment(aligment);
    Float fontS=tmp_par.getFont().getSize();
    Float capHeight=tmp_par.getFont().getBaseFont().getFontDescriptor(BaseFont.CAPHEIGHT, fontSize);
    Float padding=2f;
    cell.setPadding(padding);
    cell.setPaddingTop(capHeight-fontS+padding);
    cell.addElement(tmp_par);

    return cell;
  }

  private PdfPCell getCell(String text, int fontSize, int fontWeight, int aligment, BaseColor color) throws DocumentException, IOException
  {
    PdfPCell newCell=this.getCell(text, fontSize, fontWeight, aligment);
    newCell.setBackgroundColor(color);
    return newCell;
  }
}
