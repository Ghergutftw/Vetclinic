package app.service;

import app.dto.Response;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Value("${spring.mail.username}")
    private String from;

    public Response getReceipt(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);

        return new Response("success", "Email sent to " + to);
    }
    public Response getReceiptWithAttachment(
            String to, String subject, String text) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // Attach the PDF file
        Path path = Paths.get("user-service/src/main/resources/test.pdf");
        byte[] pdfBytes = Files.readAllBytes(path);
        ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
        helper.addAttachment("document.pdf", pdfAttachment);

        emailSender.send(message);

        return new Response("success", "Email sent to " + to);
    }



    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            // Attach the PDF file
            Path path = Paths.get(attachmentPath);
            byte[] pdfBytes = Files.readAllBytes(path);
            ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
            helper.addAttachment("document.pdf", pdfAttachment);

            emailSender.send(message);
            log.info("Email sent to " + to);
        } catch (MessagingException | IOException e) {
            e.printStackTrace(); // Handle any exceptions properly
        }
    }
}