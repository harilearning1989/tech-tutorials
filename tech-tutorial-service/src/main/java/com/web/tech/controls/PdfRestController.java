package com.web.tech.controls;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.web.tech.dto.Person;
import com.web.tech.services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("pdf")
public class PdfRestController {

    private final PdfService pdfService;

    public PdfRestController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/generateFooter")
    public ResponseEntity<byte[]> generatePdfWithFooter() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 1. Open document
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        PdfFormXObject totalPagesPlaceholder = new PdfFormXObject(new Rectangle(0, 0, 50, 12));
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterWithTotalHandler(font, totalPagesPlaceholder));

        //no need start
        Document document = new Document(pdfDoc);
// 2. Add dynamic content
        for (int i = 1; i <= 100; i++) {
            document.add(new Paragraph("Line number " + i).setFont(font).setFontSize(12));
        }
//no need end
// 3. Get total pages BEFORE closing document
        int totalPages = pdfDoc.getNumberOfPages();  // This works even before close()

// 4. Fill the placeholder BEFORE closing the document
        PdfCanvas canvas = new PdfCanvas(totalPagesPlaceholder, pdfDoc);
        canvas.beginText()
                .setFontAndSize(font, 10)
                .moveText(0, 0)
                .showText(String.valueOf(totalPages))
                .endText();

// 5. Now it's safe to close
        document.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
    }

    private static class FooterWithTotalHandler implements IEventHandler {

        private final PdfFont font;
        private final PdfFormXObject totalPagePlaceholder;

        public FooterWithTotalHandler(PdfFont font, PdfFormXObject totalPagePlaceholder) {
            this.font = font;
            this.totalPagePlaceholder = totalPagePlaceholder;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);

            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            float x = page.getPageSize().getWidth() - 150;
            float y = 20;

            canvas.beginText()
                    .setFontAndSize(font, 10)
                    .moveText(x, y)
                    .showText("Page " + pageNumber + " of ")
                    .endText();

            canvas.addXObject(totalPagePlaceholder, x + 45, y);
        }
    }

    @GetMapping("/generate4")
    public ResponseEntity<byte[]> generatePdf4() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PageSize pageSize = new PageSize(900, 842);
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
            //Document document = new Document(pdfDoc, PageSize.A4);
            Document document = new Document(pdf, pageSize);
            //document.setMargins(110, 40, 70, 40); // top, right, bottom, left

            float width = pageSize.getWidth();
            float height = pageSize.getHeight();

            // Draw Header (Top 100pt)
            canvas.setFillColor(new DeviceRgb(30, 136, 229))  // Blue
                    .rectangle(0, height - 100, width, 100)
                    .fill();

            // Draw Footer (Bottom 60pt)
            canvas.setFillColor(new DeviceRgb(76, 175, 80))  // Green
                    .rectangle(0, 0, width, 60)
                    .fill();

            // Draw Body (Remaining area)
            canvas.setFillColor(new DeviceRgb(255, 241, 118))  // Light Yellow
                    .rectangle(0, 60, width, height - 160)
                    .fill();

            // Header Text (automatically flows into margin space)
            Paragraph title = new Paragraph("APPLICATION FORM")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(255, 255, 255))
                    .setMarginBottom(20);
            document.add(title);

            // Body Table
            float[] colWidths = {3, 7};
            Table table = new Table(UnitValue.createPercentArray(colWidths)).useAllAvailableWidth();

            table.addCell("Full Name:");
            table.addCell("_____________________________________");

            table.addCell("Date of Birth:");
            table.addCell("_____________________________________");

            table.addCell("Phone Number:");
            table.addCell("_____________________________________");

            table.addCell("Email Address:");
            table.addCell("_____________________________________");

            table.addCell("Address:");
            table.addCell("_____________________________________");

            document.add(table);

            // Footer Text (placed in bottom margin area using alignment and margins)
            Paragraph footer = new Paragraph("Footer Section")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(255, 255, 255))
                    .setMarginTop(20);
            document.add(footer);

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/generateTmp3")
    public ResponseEntity<byte[]> generatePdfTmp3() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Table table1 = new Table(UnitValue.createPercentArray(new float[]{1}));
            table1.setWidth(UnitValue.createPercentValue(100));
            table1.setFixedLayout();
            table1.addCell(createCell05("Name of Institute/College/University"));
            document.add(table1);

            Table table2 = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
            table2.setWidth(UnitValue.createPercentValue(100));
            table2.setFixedLayout();
            table2.addCell(createCell05("Qualification (Degree/Course)"));
            table2.addCell(createCell05("Majored In"));
            document.add(table2);

            Table table3 = new Table(UnitValue.createPercentArray(new float[]{50, 25, 25}));
            table3.setWidth(UnitValue.createPercentValue(100));
            table3.setFixedLayout();
            table3.addCell(new Paragraph("Qualification (Degree/Course)"));
            table3.addCell(new Paragraph("Majored In"));
            table3.addCell(new Paragraph(""));
            document.add(table3);

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }

    }

    @GetMapping("/generateTmp2")
    public ResponseEntity<byte[]> generatePdfTmp2() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Table table1 = new Table(UnitValue.createPercentArray(new float[]{2, 3, 1.5f, 1.5f, 1}));
            table1.setWidth(UnitValue.createPercentValue(100));
            table1.setFixedLayout();
            table1.addCell(createCell05(""));
            table1.addCell(createCell05("PERIOD(DD-MM--YYYY"));
            table1.addCell(createCell05(""));
            table1.addCell(createCell05(""));
            table1.addCell(createCell05(""));
            document.add(table1);

            Table table2 = new Table(UnitValue.createPercentArray(new float[]{1, 1}));
            table2.setWidth(UnitValue.createPercentValue(100));
            table2.setFixedLayout();

            table2.addCell(new Cell(1, 2))
                    .addCell(new Paragraph(
                            "Name of theInstitute/University")
                            .setBorder(new SolidBorder(1))
                            .setPadding(7));
            table2.addCell(new Cell(1, 2))
                    .addCell(new Paragraph(
                            "Name of theInstitute/University")
                            .setBorder(new SolidBorder(1))
                            .setPadding(7));

            /*table2.addCell(createCell("Qualification "));
            table2.addCell(createCell(" Major-in"));

            table2.addCell(createCell("date of Graduation"));
            table2.addCell(createCell("Education Attainment"));

            table2.addCell(createCell("Couse type(regular"));
            table2.addCell(createEmptyCell());
            table2.addCell(new Cell(1,2)
                    .add(new Paragraph("Address of institute "))
                    .setBorder(new SolidBorder(1))
                    .setPadding(7));*/
            document.add(table2);


            /*Table table = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1, 1.5f, 1.5f, 1f}));

            table.setWidth(UnitValue.createPercentValue(100));

            table.setFixedLayout();

            table.addCell(createCell06("PAYROLL ORGANIZATION*"));
            table.addCell(createCell06("FROM"));
            table.addCell(createCell06("TO"));
            table.addCell(createCell06("DURATION (in months"));
            table.addCell(createCell06("DESIGNATION"));
            table.addCell(createCell06("REASON FOR SEPARATION"));
            table.addCell(createCell06("EXPERIENCE/RELIEVING LETTER AVAIABLE (Y/n"));*/

            //document.add(table);

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    public static Cell createCell06(String content) throws IOException {
        Cell cell = new Cell().setBorder(Border.NO_BORDER).setBold()
                .setBorderLeft(new SolidBorder(1)).setBorderBottom(new SolidBorder(1))
                .setBorderRight(new SolidBorder(1))
                .add(new Paragraph(content).setFontSize(9).setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                        .setTextAlignment(TextAlignment.CENTER));
        return cell;
    }

    public Cell createEmptyCell() {
        return new Cell()
                .add(new Paragraph(""))
                .setBorder(new SolidBorder(1))
                .setPadding(10);
    }

    public static Cell createCell05(String content) throws IOException {
        Cell cell = new Cell()/*.setBorder(Border.NO_BORDER).setBold()
                .setBorderLeft(new SolidBorder(1)).setBorderTop(new SolidBorder(1))
                .setBorderRight(new SolidBorder(1))*/
                .add(new Paragraph(content).setFontSize(9).setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                        .setTextAlignment(TextAlignment.CENTER));
        return cell;
    }

    public static Cell createCell(String content) throws IOException {
        Cell cell = new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph(content))
                .setBorder(new SolidBorder(1));
        cell.setPadding(5);
        return cell;
    }


    @GetMapping("/generateTmp1")
    public ResponseEntity<byte[]> generatePdfTmp1() {

        List<Person> people = Arrays.asList(
                new Person(1, "Alice", 30),
                new Person(2, "Bob", 28),
                new Person(3, "Charlie", 35)
        );
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("INFOSYS DATA SHEET")
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14));

            document.add(new Paragraph("EDUCATION DETAILS").setBold().setFontSize(12));

            /*float[] columnWidths = {200f, 200f};
            Table table = new Table(columnWidths);
            //table.setWidthPercent(100);

            table.addCell(createCell("Qualification (DEGREE/COURSE):"));
            table.addCell(createCell("Educational Attainme Highschool)"));
            table.addCell(createCell("Date of  (if undergraduate mm:yyyy):"));
            table.addCell(createCell(""));

            table.addCell(createCell("Course Type (REGULAR or DISTANCE):"));
            table.addCell(createCell(""));

            table.addCell(createCell("Address of Pin/Zip Code):", 2));

            document.add(table);*/

            // List of rows where each row is a list of cell values
            List<List<String>> data = Arrays.asList(
                    List.of("Name of Institute/College/University:"),
                    List.of("Qualification (Degree/Course)", "Majored In"),
                    List.of("Date of Graduation/Last year attended"),
                    List.of("Course Type") // single column row
            );

            for (List<String> row : data) {
                int columnCount = row.size();

                Table table = new Table(columnCount);
                table.setWidth(UnitValue.createPercentValue(100));

                for (String cellText : row) {
                    table.addCell(new Cell().add(new Paragraph(cellText)));
                }
                document.add(table);
            }

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/generatePdfRadioButton")
    public ResponseEntity<byte[]> generatePdfRadioButton() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            //PdfWriter writer = new PdfWriter(baos);
            //PdfDocument pdfDoc = new PdfDocument(writer);
            //Document document = new Document(pdfDoc);
            /*PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

            float pageWidth = PageSize.A4.getWidth();
            float leftMargin = 50;
            float rightMargin = 50;
            float usableWidth = pageWidth - leftMargin - rightMargin;

            int circleCount = 5;
            float radius = 10;
            //float y = 700;

            // Calculate spacing between circle centers
            float gapBetweenCenters = usableWidth / (circleCount - 1);

            for (int i = 0; i < circleCount; i++) {
                //float x = leftMargin + i * gapBetweenCenters;
                canvas.setLineWidth(2);
                canvas.setStrokeColor(ColorConstants.BLACK);
                //canvas.circle(radius);
                canvas.stroke();
            }*/
            /*
            PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdf);

            float x = 50;
            float y = 720;
            float radius = 7;

            document.showTextAligned("Source Of Application:", x, y + 30,
                    TextAlignment.LEFT, VerticalAlignment.TOP, 0);

            PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
            canvas.setLineWidth(1)
                    .setStrokeColor(ColorConstants.BLACK)
                    .circle(x + radius, y + radius, radius)
                    .stroke();

            document.showTextAligned("DIRECT", x + 2 * radius + 5, y + 2,
                    TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

            document.close();*/

            /*PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdf);
            PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());

            float baseX = 50;
            float y = 700;
            float radius = 7;
            float spacing = 80; // space between each option

            String[] options = {"DIRECT", "AGENT", "ONLINE", "WALK-IN", "OTHER"};
            document.showTextAligned("Source Of Application:", baseX, y + 30,
                    TextAlignment.LEFT, VerticalAlignment.TOP, 0);

            for (int i = 0; i < options.length; i++) {
                float x = baseX + i * spacing;

                canvas.setLineWidth(1)
                        .setStrokeColor(ColorConstants.BLACK)
                        .circle(x + radius, y + radius, radius)
                        .stroke();

                document.showTextAligned(options[i], x + 2 * radius + 4, y + 2,
                        TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
            }

            document.close();*/

            PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdf);
            PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());

            float baseX = 50;
            float y = 700;
            float radius = 7;
            float spacing = 80; // space between each option

            String[] options = {"DIRECT", "AGENT", "ONLINE", "WALK-IN", "OTHER"};
            document.showTextAligned("Source Of Application:", baseX, y + 30,
                    TextAlignment.LEFT, VerticalAlignment.TOP, 0);

            for (int i = 0; i < options.length; i++) {
                float x = baseX + i * spacing;
                canvas.setLineWidth(1)
                        .setStrokeColor(ColorConstants.BLACK)
                        .circle(x + radius, y + radius, radius)
                        .stroke();

                document.showTextAligned(options[i], x + 2 * radius + 4, y + 2,
                        TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
            }
            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/generateTmp")
    public ResponseEntity<byte[]> generatePdfTmp() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            /*document.add(new Paragraph("Source of Application:").setBold().setFontSize(12));

            // Options
            String[] options = {"Direct", "ERP", "Recruitment Partner", "Caravan", "Internet Portal"};
            float x = 50;
            float y = 750;
            float spacing = 100;

            for (int i = 0; i < options.length; i++) {
                // Add label next to radio button
                Paragraph label = new Paragraph(options[i])
                        .setFixedPosition(x + i * spacing + 20, y, 100)
                        .setFontSize(10);
                document.add(label);
            }*/

            // Add section heading (optional)
            /*document.add(new Paragraph("Sub Sources").setBold().setFontSize(14).setTextAlignment(TextAlignment.LEFT));
            // Get form object
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            // Create radio button group
            PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "subSourcesLeft", "");

            // Create individual radio buttons and assign to the group
            PdfFormField walkIn = PdfFormField.createRadioButton(pdfDoc, new Rectangle(100, 750, 15, 15), radioGroup, "Walk-in");
            PdfFormField facebook = PdfFormField.createRadioButton(pdfDoc, new Rectangle(100, 730, 15, 15), radioGroup, "Facebook");
            PdfFormField rehires = PdfFormField.createRadioButton(pdfDoc, new Rectangle(100, 710, 15, 15), radioGroup, "Rehires");
            PdfFormField careers = PdfFormField.createRadioButton(pdfDoc, new Rectangle(100, 690, 15, 15), radioGroup, "Careers");
            PdfFormField referral = PdfFormField.createRadioButton(pdfDoc, new Rectangle(100, 670, 15, 15), radioGroup, "Applicant referral");
            // Add radio group to the form
            form.addField(radioGroup);
            // Add labels next to each radio button
            document.showTextAligned("Walk-in", 120, 750, TextAlignment.LEFT);
            document.showTextAligned("Facebook", 120, 730, TextAlignment.LEFT);
            document.showTextAligned("Rehires", 120, 710, TextAlignment.LEFT);
            document.showTextAligned("Careers", 120, 690, TextAlignment.LEFT);
            document.showTextAligned("Applicant referral", 120, 670, TextAlignment.LEFT);*/

            // Create a circle using PdfCanvas
            /*PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            float x = 100;
            float y = 700;
            float radius = 10;
            canvas.setLineWidth(2);
            canvas.setStrokeColor(ColorConstants.BLACK);
            canvas.circle(x, y, radius);
            canvas.stroke();*/

            //vertical radio buttons
            /*PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

            float x = 100;
            float startY = 700;
            float radius = 10;
            float spacing = 40; // vertical space between circles

            // Draw 5 vertically spaced circles on one page
            for (int i = 0; i < 5; i++) {
                float y = startY - i * spacing;
                canvas.setLineWidth(2);
                canvas.setStrokeColor(ColorConstants.BLACK);
                canvas.circle(x, y, radius);
                canvas.stroke();
            }*/

            //horizontal radio buttons
            /*PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

            float startX = 100;
            float y = 700;
            float radius = 10;
            float spacing = 40; // horizontal spacing between circles

            // Draw 5 circles in a horizontal line (same row)
            for (int i = 0; i < 5; i++) {
                float x = startX + i * spacing;
                canvas.setLineWidth(2);
                canvas.setStrokeColor(ColorConstants.BLACK);
                canvas.circle(x, y, radius);
                canvas.stroke();
            }*/

            //horizontal radio buttons fix width
            PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

            float pageWidth = PageSize.A4.getWidth();
            float leftMargin = 50;
            float rightMargin = 50;
            float usableWidth = pageWidth - leftMargin - rightMargin;

            int circleCount = 5;
            float radius = 10;
            float y = 700;

            // Calculate spacing between circle centers
            float gapBetweenCenters = usableWidth / (circleCount - 1);

            for (int i = 0; i < circleCount; i++) {
                float x = leftMargin + i * gapBetweenCenters;
                canvas.setLineWidth(2);
                canvas.setStrokeColor(ColorConstants.BLACK);
                canvas.circle(x, y, radius);
                canvas.stroke();
            }

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=application_form.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf() throws Exception {
        byte[] pdfBytes = pdfService.generatePdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=radio_buttons.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
