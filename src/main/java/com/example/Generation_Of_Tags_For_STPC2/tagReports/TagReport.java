package com.example.Generation_Of_Tags_For_STPC2.tagReports;

import com.example.Generation_Of_Tags_For_STPC2.models.Tag;
import com.example.Generation_Of_Tags_For_STPC2.repositories.MaterialCodeRepo;
import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import com.example.Generation_Of_Tags_For_STPC2.services.StatusMachineServices;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.Generation_Of_Tags_For_STPC2.beans.Constants.*;

@Slf4j
@RequiredArgsConstructor
public class TagReport {
    private final TagRepo tagRepo;
    private final MaterialCodeRepo materialCodeRepo;
    private final StatusMachineServices machineServices;
    private static final String OUTPUT_FILE = "src\\main\\resources\\birka_GSV.pdf";

    public void exportToPdf(String area, String millNumber, String materialCode, String diameter, Integer melt) throws Exception {
        if (area.equals("D") && millNumber.isEmpty() || materialCode == null || melt == null) {
            call("src\\main\\resources\\empty_Birka_GSV_D.pdf");
        } else if (area.equals("H") && millNumber.isEmpty() || materialCode == null || melt == null) {
            call("src\\main\\resources\\empty_Birka_GSV_H.pdf");
        }

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(OUTPUT_FILE));
        Document document = new Document(pdfDocument, PageSize.A4.rotate());

        PdfFont pdfFont = PdfFontFactory.createFont("src\\main\\resources\\static\\arial.ttf",
                PdfEncodings.IDENTITY_H);
        document.setFont(pdfFont);
        Style style = new Style();
        List<Tag> tagList = tagRepo.findByAreaAndMillNumberAndMaterialCode(area, millNumber, materialCode);
        for (Tag t : tagList
        ) {
            Table table = new Table(6);
            table.setWidth(UnitValue.createPercentValue(98));
            table.setBorder(Border.NO_BORDER);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.setVerticalAlignment(VerticalAlignment.MIDDLE);

            //-------------------------- HEADER --------------------------

            if (t.getArea().equals("D")) {
                table.addCell(createCell(AREA_D, 6, style, TextAlignment.CENTER).setBorderLeft(new DashedBorder(1))
                        .setBorderTop(new DashedBorder(1)).setBorderRight(new DashedBorder(1)).setBorderBottom(Border.NO_BORDER).setPaddingTop(25));
            } else if(t.getArea().equals("H")) {
                table.addCell(createCell(AREA_H, 6, style, TextAlignment.CENTER).setBorderLeft(new DashedBorder(1))
                        .setBorderTop(new DashedBorder(1)).setBorderRight(new DashedBorder(1)).setBorderBottom(Border.NO_BORDER).setPaddingTop(25));
            }

            //-------------------------- 1 СТРОКА ------------------------

            table.addCell(createCell(KEY_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderLeft(new DashedBorder(1))
                    .setPaddingLeft(32).setBorderRight(Border.NO_BORDER).setPaddingTop(25));

            table.addCell(createCell(String.valueOf(t.getId()), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(SPACE, 4, style, TextAlignment.LEFT).setPaddingTop(25).setBorder(Border.NO_BORDER)
                    .setBorderRight(new DashedBorder(1)));

            //-------------------------- 2 СТРОКА ------------------------

            table.addCell(createCell(MILL_NUMBER_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setBorderLeft(new DashedBorder(1)).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(String.valueOf(t.getMillNumber()), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(DATE_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(t.getDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), 0, style, TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(SHIFT_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(String.valueOf(t.getShift()), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderRight(new DashedBorder(1))
                    .setPaddingTop(25));

            //-------------------------- 3 СТРОКА ------------------------

            table.addCell(createCell(OPERATOR_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderLeft(new DashedBorder(1))
                    .setPaddingLeft(32).setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(String.valueOf(machineServices.getTabNum(area, millNumber)), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setPaddingLeft(32).setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(TIME_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setPaddingLeft(32)
                    .setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(t.getDateTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)), 0, style, TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER).setPaddingLeft(32).setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(BRIGADE_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setPaddingLeft(32)
                    .setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(String.valueOf(t.getBrigade()), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setBorderRight(new DashedBorder(1)).setPaddingTop(25).setPaddingBottom(25));

            //-------------------------- 4 СТРОКА ------------------------

            table.addCell(createCell(MATERIAL_CODE_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setBorderLeft(new DashedBorder(1)).setBorderTop(new DashedBorder(1)).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(String.valueOf(t.getMaterialCode()), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setBorderTop(new DashedBorder(1)).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(DIAMETER_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderTop(new DashedBorder(1))
                    .setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(materialCodeRepo.getDiameter(materialCode).get(0).getDiameter() + MM, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setBorderTop(new DashedBorder(1)).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(WEIGHT_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderTop(new DashedBorder(1))
                    .setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(diameter + KG, 0, style,
                    TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderTop(new DashedBorder(1))
                    .setBorderRight(new DashedBorder(1)).setPaddingTop(25));

            //-------------------------- 5 СТРОКА ------------------------

            table.addCell(createCell(MELT_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderLeft(new DashedBorder(1))
                    .setPaddingLeft(32).setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(String.valueOf(melt), 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setPaddingLeft(32).setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(LENGTH_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setPaddingLeft(32)
                    .setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(t.getLength() + M, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER)
                    .setPaddingLeft(32).setPaddingTop(25).setPaddingBottom(25));

            table.addCell(createCell(QUALITY_COLON, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setPaddingLeft(32).setPaddingTop(25));

            table.addCell(createCell(CS, 0, style, TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderRight(new DashedBorder(1))
                    .setPaddingTop(25).setPaddingBottom(25));

            //-------------------------- 6 СТРОКА ------------------------

            table.addCell(createCell(HOURS_AFTER_DIE_CHANGE_COLON, 3, style, TextAlignment.LEFT).setBorderLeft(new DashedBorder(1))
                    .setBorderTop(new DashedBorder(1)).setBorderRight(Border.NO_BORDER).setBorderBottom(new DashedBorder(1)).setPaddingLeft(32)
                    .setPaddingTop(25));

            table.addCell(createCell(String.valueOf(t.getJobDie()), 3, style, TextAlignment.CENTER).setBorderTop(new DashedBorder(1))
                    .setBorderRight(new DashedBorder(1)).setBorderLeft(Border.NO_BORDER).setBorderBottom(new DashedBorder(1)).setPaddingTop(25)
                    .setPaddingBottom(25));

            document.add(table);
        }
        document.close();
        call("src\\main\\resources\\birka_GSV.pdf");  //path of the pdf file which you want to print
    }

    public static void call(String filePath) throws IOException {
        try {
            printer(filePath);
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    public static void printer(String filePath)
            throws IOException, PrinterException {
        PrintService printServices = PrintServiceLookup.lookupDefaultPrintService();
        PDDocument document = PDDocument.load(new File(filePath));
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.setPrintService(printServices);
        job.print();
        document.close();
    }

    private static Cell createCell(String content, int colspan, Style style, TextAlignment alignment) {
        Paragraph paragraph = new Paragraph(content)
                .setFontSize(16)
                .setTextAlignment(alignment)
                .setBold();

        Cell cell = new Cell(0, colspan).add(paragraph);
        cell.addStyle(style);

        return cell;
    }
}
