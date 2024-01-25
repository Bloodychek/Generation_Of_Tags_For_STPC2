package com.example.Generation_Of_Tags_For_STPC2.beans;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CellStyleUtil {
    //Базовый универсальный стиль
    public static CellStyle setBodyStyle(XSSFWorkbook workbook,
                                         int fontSize, String fontName,
                                         HorizontalAlignment horizontalAlignment,
                                         boolean bold, boolean borders) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(horizontalAlignment);
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setFontHeightInPoints((short) fontSize);
        font.setFontName(fontName);
        style.setFont(font);
//        style.setWrapText(true);
        if (borders) createBorders(style);
        return style;
    }

    private static void createBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    public static void setBordersForTag(Row row, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress rangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        RegionUtil.setBorderBottom(BorderStyle.DASH_DOT, rangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.DASH_DOT, rangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.DASH_DOT, rangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.DASH_DOT, rangeAddress, sheet);
    }
}