package app.service;

import app.dto.Response;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Value("${spring.mail.username}")
    private String from;

    public Response getReceipt(String to) throws MessagingException, IOException {
        // Create a PDF document
        byte[] pdfBytes = createPDF();

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
        helper.addAttachment("Chitanța Consultație " + " - " + formattedDateTime + ".pdf" , pdfAttachment);

        emailSender.send(message);

        log.info("Email sent to " + to);
        return new Response("success", "Email sent to " + to);
    }

    private static byte[] createPDF() throws IOException {
        // Create a new PDF document
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            // Add a page to the document
            PDPage page = new PDPage();
            document.addPage(page);

            // Write text to the page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);

                // Adding current date and time to the title
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                String formattedDateTime = now.format(formatter);

                String title = "Chitanta Consultatie - " + formattedDateTime;
                contentStream.showText(title);
                contentStream.endText();
            }

            // Save the document to ByteArrayOutputStream
            document.save(outputStream);

            // Return the byte array of the PDF document
            return outputStream.toByteArray();
        }
    }

}