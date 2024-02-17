package app.service;

import app.dto.AccountInfo;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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

    public Response sendNewAccountInformations(String to, AccountInfo accountInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Welcome to the clinic");
        message.setText("Hello " + accountInfo.getUsername() + ",\n\n" +
                "Welcome to our clinic! Your account information is as follows:\n" +
                "Username: " + accountInfo.getUsername() + "\n" +
                "Password: " + accountInfo.getPassword() + "\n\n" +
                "This account credentials were generated\n" +
                "Thank you for choosing our clinic.\n\n" +
                "Best regards,\n" +
                "Clinic Team");
        emailSender.send(message);
        return new Response("status", "Information sent successfully");
    }

    public Response getReceipt(String to, ConsultationDTO consultation) throws MessagingException, IOException {
        // Create a PDF document
        byte[] pdfBytes = createPdf(consultation);

        // Create MimeMessage and helper
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        helper.setSubject("Chitanța Consultație " + " - " + formattedDateTime);
        helper.setText("Mulțumim pentru alegerea noastră! Atașat găsiți chitanța pentru consultația dumneavoastră. O zi buna!");

        ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
        helper.addAttachment("Chitanța Consultație " + " - " + formattedDateTime + ".pdf", pdfAttachment);

        emailSender.send(message);

        log.info("Email sent to " + to);
        return new Response("success", "Email sent to " + to);
    }

    public static byte[] createPdf(ConsultationDTO consultation) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            InputStream imageStream = new ClassPathResource(CHITANTA_PATH).getInputStream();
            Image image = Image.getInstance(org.apache.commons.io.IOUtils.toByteArray(imageStream));
            image.scaleToFit(200, 200);
            document.add(image);

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Paragraph title = new Paragraph("Consultation Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            Paragraph date = new Paragraph("\n\nDate: " + formattedDateTime);
            document.add(date);

            document.add(new Paragraph("\n"));

            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Create table
            PdfPTable table = new PdfPTable(6); // 6 columns
            table.setWidthPercentage(100);

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(createCellWithBorder("Date", contentFont)); // Use the modified method for header cells
            table.addCell(createCellWithBorder("Doctor Last Name", contentFont)); // Use the modified method for header cells
            table.addCell(createCellWithBorder("Diagnostic", contentFont)); // Use the modified method for header cells
            table.addCell(createCellWithBorder("Treatment", contentFont)); // Use the modified method for header cells
            table.addCell(createCellWithBorder("Recommendations", contentFont)); // Use the modified method for header cells
            table.addCell(createCellWithBorder("Price", contentFont)); // Use the modified method for header cells

            table.addCell(createCell(dateFormat.format(consultation.getDate()), contentFont)); // Format the date
            table.addCell(createCell(consultation.getDoctorLastName(), contentFont));
            table.addCell(createCell(consultation.getDiagnostic(), contentFont));
            table.addCell(createCell(consultation.getTreatment(), contentFont));
            table.addCell(createCell(consultation.getRecommendations(), contentFont));
            table.addCell(createCell(String.valueOf(consultation.getPrice()), contentFont));

            document.add(table);
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    private static PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static PdfPCell createCellWithBorder(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidth(2f); // Set thicker border width for header cells
        return cell;
    }
}
