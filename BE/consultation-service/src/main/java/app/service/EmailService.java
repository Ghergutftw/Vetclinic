package app.service;

import app.dto.ConsultationDTO;
import app.dto.Response;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EmailService {

    private static final String CHITANTA_PATH = "templates/logo.jpg";

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Value("${spring.mail.username}")
    private String from;

    public Response getReceipt(String to, ConsultationDTO consultation) throws MessagingException, IOException {
        // Create a PDF document
        byte[] pdfBytes = createPDF(consultation);

        // Create MimeMessage and helper
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDateTime = now.format(formatter);

        helper.setSubject("Chitanța Consultație " + " - " + formattedDateTime);
        helper.setText("Mulțumim pentru alegerea noastră! Atașat găsiți chitanța pentru consultația dumneavoastră. O zi buna!");

        ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
        helper.addAttachment("Chitanța Consultație " + " - " + formattedDateTime + ".pdf", pdfAttachment);

        emailSender.send(message);

        log.info("Email sent to " + to);
        return new Response("success", "Email sent to " + to);
    }

    private static byte[] createPDF(ConsultationDTO consultation) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add logo
            InputStream imageStream = new ClassPathResource(CHITANTA_PATH).getInputStream();
            Image image = Image.getInstance(org.apache.commons.io.IOUtils.toByteArray(imageStream));
            image.scaleToFit(200, 200);
            document.add(image);

            // Add title

            com.itextpdf.text.Font font = new com.itextpdf.text.Font();
            font.setSize(12);
            font.setColor(com.itextpdf.text.BaseColor.BLACK);
            document.add(new Phrase("Consultation Receipt", font));

            // Add current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            document.add(new Phrase("\n\nDate: " + formattedDateTime));

            // Create table
            PdfPTable table = new PdfPTable(8); // 8 columns
            table.setWidthPercentage(100);

            // Add table headers
            table.addCell("ID");
            table.addCell("Date");
            table.addCell("Doctor Last Name");
            table.addCell("Diagnostic");
            table.addCell("Treatment");
            table.addCell("Recommendations");
            table.addCell("Price");


            // Add data from consultations DTO
            addCell(table, String.valueOf(consultation.getId()));
            addCell(table, consultation.getDate().toString());
            addCell(table, consultation.getDoctorLastName());
            addCell(table, consultation.getDiagnostic());
            addCell(table, consultation.getTreatment());
            addCell(table, consultation.getRecommendations());
            addCell(table, String.valueOf(consultation.getPrice()));


            // Add table to document
            document.add(table);

            document.close();

            // Return the byte array of the PDF document
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    // Method to add cell to table with specified alignment
    private static void addCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}