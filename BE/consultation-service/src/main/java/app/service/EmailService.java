package app.service;

import app.dto.Response;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.PdfWriter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.itextpdf.text.BaseColor.BLACK;

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
        helper.addAttachment("Chitanța Consultație " + " - " + formattedDateTime + ".pdf", pdfAttachment);

        emailSender.send(message);

        log.info("Email sent to " + to);
        return new Response("success", "Email sent to " + to);
    }

    private static byte[] createPDF() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();

            // Initialize PdfWriter with ByteArrayOutputStream
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Load your image from resources and add it to the document
            InputStream imageStream = new ClassPathResource(CHITANTA_PATH).getInputStream();
            Image image = Image.getInstance(org.apache.commons.io.IOUtils.toByteArray(imageStream));
            image.scaleToFit(400, 400);
            document.add(image);

            // Write text to the document
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            String formattedDateTime = now.format(formatter);

            com.itextpdf.text.Font font = new com.itextpdf.text.Font();
            font.setSize(20);
            font.setColor(com.itextpdf.text.BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Chitanta Consultatie - " + formattedDateTime, font);
            document.add(paragraph);

            document.close();

            // Return the byte array of the PDF document
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}