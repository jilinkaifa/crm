package com.pandawork.crm.common.utils;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * ExcelReaderUtil
 * Created by shura
 * Date:  2017/8/10.
 * Time:  0:56
 */
public class ExcelReaderUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";
    private Workbook workbook = null;
    private Sheet sheet = null;//表
    private int sheetNum = 0; // 第sheetnum个工作表

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public ExcelReaderUtil() {
    }

    public ExcelReaderUtil(InputStream inputStream,String fileName) throws IOException {
        if (fileName.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
    }

    /**
     * 指定工作表、行、列下的内容
     *
     * @param rowNum
     * @return String
     */
    public String getCellValue(int rowNum, int colNum) {
        if (sheetNum < 0 || rowNum < 0){
            return "";
        }
        String strExcelCell = "";
        try {
            sheet = this.workbook.getSheetAt(sheetNum);
            //判断是否是合并单元格，如果是，就将行、列索引改为合并单元格的索引
            for(int numMR = 0; numMR < this.sheet.getNumMergedRegions(); numMR++){
                //获取合并单元格
                CellRangeAddress cellRangeAddress = this.sheet.getMergedRegion(numMR);
                int firstColumnInd = cellRangeAddress.getFirstColumn();
                int lastColumnInd = cellRangeAddress.getLastColumn();
                int firstRowInd = cellRangeAddress.getFirstRow();
                int lastRowInd = cellRangeAddress.getLastRow();
                //如果当前单元格在这个合并单元格里
                if(rowNum >= firstRowInd && rowNum <= lastRowInd && colNum >= firstColumnInd && colNum <= lastColumnInd){
                    rowNum = firstRowInd;
                    colNum = firstColumnInd;
                    break;
                }
            }
            Row row = sheet.getRow(rowNum);
            if (row.getCell((short) colNum) != null) {
                switch (row.getCell((short) colNum).getCellType()) {
                    case Cell.CELL_TYPE_FORMULA:
                        strExcelCell = "FORMULA ";
                        break;
                    case Cell.CELL_TYPE_NUMERIC: {
                        strExcelCell = String.valueOf(row.getCell((short) colNum).getNumericCellValue());
                    }
                    break;
                    case Cell.CELL_TYPE_STRING:
                        strExcelCell = row.getCell((short) colNum).getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        strExcelCell = "";
                        break;
                    default:
                        strExcelCell = "";
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelCell;
    }

    /**
     * sheetNum下的记录行数
     *
     * @return int
     */
    public int getRowCount() {
        Sheet sheet = workbook.getSheetAt(this.sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }
}
