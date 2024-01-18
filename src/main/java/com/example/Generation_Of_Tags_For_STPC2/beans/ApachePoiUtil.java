package com.example.Generation_Of_Tags_For_STPC2.beans;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ApachePoiUtil {
    private static XSSFSheet sheet;
    private static Row row;

    public static void checkType(Object valueOfCell, Cell cell) {
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) valueOfCell);
        } else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        }
    }

    public static void createCell(Row row, int colNumber, Object valueOfCell, CellStyle style) {
        Cell cell = row.createCell(colNumber);
        checkType(valueOfCell, cell);
        cell.setCellStyle(style);
    }

    //получить ячейку
    public static void getCell(Row row, int colNumber, Object valueOfCell) {
        Cell cell = row.getCell(colNumber);
        checkType(valueOfCell, cell);
    }

    public static void setResponseHeader(
            HttpServletResponse response,
            String contentType,
            String extension,
            String prefix) throws UnsupportedEncodingException
    {
        String fileName = prefix + extension;
        response.setContentType(contentType);
        String headerKey = Constants.CONTENT_DISPOSITION;
        String headerValue = Constants.ATTACHMENT_FILE_NAME + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replaceAll("\\+", " ");
        response.setHeader(headerKey, headerValue);
    }
}
