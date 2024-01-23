package com.example.Generation_Of_Tags_For_STPC2.tagReports;

import com.example.Generation_Of_Tags_For_STPC2.beans.ApachePoiUtil;
import com.example.Generation_Of_Tags_For_STPC2.models.Tag;
import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.Generation_Of_Tags_For_STPC2.beans.Constants.*;

@RequiredArgsConstructor
public class TagReport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Row row;
    private Cell cell;
    private final TagRepo tagRepo;

    public void exportToExcel(String area, String millNumber, String materialCode, Integer diameter, Integer operator, HttpServletResponse response) throws Exception {
        InputStream file =
                getClass().getClassLoader().getResourceAsStream(BIRKA_GSV + DOT_XLSX);
        if(file != null) workbook = new XSSFWorkbook(file);

        ApachePoiUtil.setResponseHeader(response, CONTENT_TYPE2, DOT_XLSX, BIRKA_GSV);
        write(area, millNumber, materialCode, diameter, operator);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    private void write(String area, String millNumber, String materialCode, Integer diameter, Integer operator){
        List<Tag> tagList = tagRepo.findByAreaAndMillNumberAndMaterialCode(area, millNumber, materialCode);
        sheet = workbook.getSheet(SHEET_NAME);
        for (Tag t: tagList
        ) {
            //if(row.getCell().)
            row = sheet.getRow(1);
            cell = row.createCell(2);
            cell.setCellValue(1234);
            row = sheet.getRow(2);
            cell = row.createCell(1);
            cell.setCellValue(t.getMillNumber());
            cell = row.createCell(3);
            cell.setCellValue(t.getDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            cell = row.createCell(5);
            cell.setCellValue(t.getShift());
            row = sheet.getRow(3);
            cell = row.createCell(1);
            cell.setCellValue(operator);
            cell = row.createCell(3);
            cell.setCellValue(t.getDateTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            cell = row.createCell(5);
            cell.setCellValue(t.getBrigade());
            row = sheet.getRow(4);
            cell = row.createCell(1);
            cell.setCellValue(t.getMaterialCode());
            cell = row.createCell(3);
            cell.setCellValue(diameter);
            cell = row.createCell(5);
            cell.setCellValue(t.getWeight());
            row = sheet.getRow(5);
            cell = row.createCell(1);
            cell.setCellValue(t.getMelt());
            cell = row.createCell(3);
            cell.setCellValue(t.getLength());
            row = sheet.getRow(6);
            cell = row.createCell(4);
            cell.setCellValue(t.getJobDie());
        }
    }
}
