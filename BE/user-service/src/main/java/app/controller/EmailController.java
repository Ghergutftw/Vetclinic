package app.controller;

import app.dto.EmailDTO;
import app.dto.Response;
import app.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    @GetMapping("/get-receipt")
    @ResponseStatus(HttpStatus.OK)
    public Response sendEmailWithAttachment(@RequestBody EmailDTO emailDTO , @RequestParam String email) throws MessagingException, IOException {
       return emailService.getReceiptWithAttachment(email, emailDTO.getSubject(), emailDTO.getText());
    }
}
