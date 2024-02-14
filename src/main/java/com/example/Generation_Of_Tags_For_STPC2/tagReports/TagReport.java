package com.example.Generation_Of_Tags_For_STPC2.tagReports;

import com.example.Generation_Of_Tags_For_STPC2.beans.ApachePoiUtil;
import com.example.Generation_Of_Tags_For_STPC2.models.Tag;
import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.Generation_Of_Tags_For_STPC2.beans.CellStyleUtil.setBodyStyle;
import static com.example.Generation_Of_Tags_For_STPC2.beans.CellStyleUtil.setBordersForTag;
import static com.example.Generation_Of_Tags_For_STPC2.beans.Constants.*;

@RequiredArgsConstructor
public class TagReport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Row row;
    private Cell cell;
    private final TagRepo tagRepo;

    public void exportToExcel(String area, Integer millNumber, String materialCode, String diameter, Integer operator, HttpServletResponse response) throws Exception {
        InputStream file =
                getClass().getClassLoader().getResourceAsStream(BIRKA_GSV + DOT_XLSX);
        if(file != null) workbook = new XSSFWorkbook(file);

        ApachePoiUtil.setResponseHeader(response, CONTENT_TYPE2, DOT_XLSX, BIRKA_GSV);
        write(area, millNumber, materialCode, diameter, operator);
        setAutosizeColumn();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    private void write(String area, Integer millNumber, String materialCode, String diameter, Integer operator){
        List<Tag> tagList = tagRepo.findByAreaAndMillNumberAndMaterialCode(area, millNumber, materialCode);
        sheet = workbook.getSheet(SHEET_NAME);
        for (Tag t: tagList
        ) {
            ApachePoiUtil.createCell(sheet.getRow(1), 1, t.getId(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(2), 1, t.getMillNumber(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(2), 3, t.getDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(2), 5, t.getShift(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(3), 1, operator, setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(3), 3, t.getDateTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(3), 5, t.getBrigade(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(4), 1, t.getMaterialCode(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(4), 3, diameter, setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(4), 5, t.getWeight(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(5), 1, t.getMelt(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(5), 3, t.getLength(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
            ApachePoiUtil.createCell(sheet.getRow(6), 4, t.getJobDie(), setBodyStyle(workbook, 12, TNR, CENTER, false, false));
        }
        setBordersForTag(row, sheet, 0, 3, 0, 5);
        setBordersForTag(row, sheet, 4, 5, 0, 5);
        setBordersForTag(row, sheet, 6, 6, 0, 5);
    }

    private void setAutosizeColumn() {
        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(2, true);
        sheet.autoSizeColumn(3, true);
        sheet.autoSizeColumn(4, true);
        sheet.autoSizeColumn(5, true);
        sheet.autoSizeColumn(6, true);
    }
}
